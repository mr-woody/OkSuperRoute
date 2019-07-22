package com.okay.sampletamplate.sample.activity

import android.os.Bundle
import android.view.View
import com.okay.router.Router
import com.okay.sampletamplate.ToolBarActivity
import com.okay.sampletamplate.sample.R
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * @author :Created by yuetao
 * @date 2019-07-17 11:21
 * @email yuetao@okay.cn
 * 演示2 见配置
 * @see com.okay.sampletamplate.sample.SampleApplication.onCreate
 */
class Sample2Activity : ToolBarActivity() {

    val pool: ExecutorService = Executors.newSingleThreadExecutor { runnable ->
        val thread:Thread = Thread(runnable)
        thread.name = "action_executor"
        return@newSingleThreadExecutor thread
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo2)

        findViewById<View>(R.id.launchActionRoute).setOnClickListener {launchActionRoute()}
        findViewById<View>(R.id.launchActionRouteWithExecutorAnnotation).setOnClickListener {launchActionRouteWithExecutorAnnotation()}
        findViewById<View>(R.id.launchActionRouteWithExecutorConfig).setOnClickListener {launchActionRouteWithExecutorConfig()}
    }

    fun launchActionRoute() {
        Router.create("okay://page/say/hello").open(this)
    }

    fun launchActionRouteWithExecutorAnnotation() {
        Router.create("okay://page/executor/switcher").open(this)
    }


    fun launchActionRouteWithExecutorConfig() {
        Router.create("okay://page/executor/switcher")
                .setExecutor(pool)
                .open(this)
    }


}
