package com.okay.sampletamplate.sample.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.okay.router.Router
import com.okay.router.callback.RouteCallback
import com.okay.router.configs.RouterConfiguration
import com.okay.sampletamplate.sample.interceptors.LoginInterceptor
import com.okay.router.exception.NotFoundException
import com.okay.router.launcher.Launcher
import com.okay.router.module.RouteRule
import com.okay.sampletamplate.ToolBarActivity
import com.okay.sampletamplate.sample.R

/**
 * @author :Created by yuetao
 * @date 2019-07-17 11:10
 * @email yuetao@okay.cn
 * 演示1 见配置
 * @see com.okay.sampletamplate.sample.SampleApplication.onCreate
 */
class Sample1Activity : ToolBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo1)

        findViewById<View>(R.id.open_browser).setOnClickListener {openBrowser()}
        findViewById<View>(R.id.toPrinterActivityWithRequestLogin).setOnClickListener {toPrinterActivityWithRequestLogin()}
        findViewById<View>(R.id.toPrinterActivityWithInterceptor).setOnClickListener {toPrinterActivityWithInterceptor()}
        findViewById<View>(R.id.toPrinterActivityWithoutInterceptor).setOnClickListener {toPrinterActivityWithoutInterceptor()}
        findViewById<View>(R.id.toUserActivity).setOnClickListener {toUserActivity()}
        findViewById<View>(R.id.toPrinterActivityWithExtras).setOnClickListener {toPrinterActivityWithExtras()}
        findViewById<View>(R.id.toResultActivity).setOnClickListener {toResultActivity()}
    }


    // =======http/https自动跳转浏览器========
    fun openBrowser() {
        Router.create("https://www.baidu.com").open(this)
    }

    // =======不同拦截器使用方式示例===========
    fun toPrinterActivityWithRequestLogin() {
        Router.create("okay://page/intent/printer?title=动态登录检查&requestLogin=1").open(this)
    }

    
    fun toPrinterActivityWithInterceptor() {
        Router.create("okay://page/intent/printer?title=使用指定拦截器")
                .addInterceptor(LoginInterceptor())// 指定此次跳转使用此拦截器
                .open(this)
    }

    
    fun toPrinterActivityWithoutInterceptor() {
        Router.create("okay://page/intent/printer?title=不使用拦截器进行跳转").open(this)
    }

   
    fun toUserActivity() {
        Router.create("okay://page/user-info?username=测试账号").open(this)
    }

   
    fun toPrinterActivityWithExtras() {
        val data = Bundle().apply {
            putString("用户名", "测试")
            putString("密码", "你猜")
        }

        Router.create("okay://page/intent/printer")
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)// 添加启动标记位：Intent.addFlag()
                .addExtras(data)// 添加额外数据。将放入Intent中进行传递:Intent.putExtras(data)
                .addInterceptor(LoginInterceptor())// 添加拦截器,若添加有多个拦截器，将被依次触发
                .setCallback(object: RouteCallback {// 添加路由回调
                override fun notFound(uri: Uri?, e: NotFoundException?) {
                    Toast.makeText(this@Sample1Activity, "没匹配到与此uri所匹配的路由目标", Toast.LENGTH_SHORT).show()
                }

                    override fun onOpenSuccess(uri: Uri?, rule: RouteRule<out RouteRule<*, *>, out Launcher>?) {
                        Toast.makeText(this@Sample1Activity, "打开路由成功", Toast.LENGTH_SHORT).show()
                    }

                    override fun onOpenFailed(uri: Uri?, e: Throwable?) {
                        Toast.makeText(this@Sample1Activity, "打开路由失败：${e?.message}", Toast.LENGTH_SHORT).show()
                    }
                })
                .setAnim(R.anim.anim_fade_in, R.anim.anim_fade_out)// 设置转场动画
                .open(this)

    }
    
    fun toResultActivity() {
        Router.create("okay://page/result")
                .requestCode(1001)// 指定请求码，使用startActivityForResult跳转
                // 指定返回数据回调
                .resultCallback { resultCode, data -> Toast.makeText(this, "返回码是$resultCode", Toast.LENGTH_SHORT).show() }
                .open(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        RouterConfiguration.get().dispatchActivityResult(this, requestCode, resultCode, data)
    }

}
