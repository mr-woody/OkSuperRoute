package com.okay.sampletamplate.sample.activity

import android.os.Bundle
import android.view.View
import com.okay.router.Router
import com.okay.sampletamplate.sample.pojo.User
import com.okay.sampletamplate.ToolBarActivity
import com.okay.sampletamplate.sample.R
import com.okay.sampletamplate.utlis.EasyToast


/**
 * @author :Created by yuetao
 * @date 2019-07-17 11:30
 * @email yuetao@okay.cn
 * 演示3 见配置
 * @see com.okay.sampletamplate.sample.SampleApplication.onCreate
 */
class Sample3Activity: ToolBarActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo3)
        findViewById<View>(R.id.createInstanceForJavaBean).setOnClickListener {
            createInstanceForJavaBean()
        }
    }

    fun createInstanceForJavaBean() {
        val user = Router.createInstanceRouter("okay://page/creator/user?name=CreatorRouter").createInstance<User>()
        EasyToast.create(Sample3Activity@this).show("获取到对象：$user")
    }
}