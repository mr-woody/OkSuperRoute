package com.okay.sampletamplate.sample.interceptors


import android.content.Intent
import android.net.Uri
import com.okay.sampletamplate.sample.manager.DataManager
import com.okay.router.interceptors.RouteInterceptor
import com.okay.sampletamplate.sample.activity.LoginActivity

/**
 * 默认拦截器。所有路由(除掉直接以浏览器方式打开的路由)均会触发此拦截器。
 *
 * 可以用作登录开关控制：为登录拦截添加动态登录控制。
 *
 */
class DefaultInterceptor : RouteInterceptor {

    override fun intercept(chain: RouteInterceptor.ActionChain) {
        val action = chain.action()
        // 当路由启动链接为需要进行登录检查时，且当前未登录，进行拦截
        if(checkRequestLogin(action.uri) && !DataManager.login){
            // 拦截后，将数据传递到登录页去。待登录完成后进行路由恢复
            val intent: Intent = Intent(action.context, LoginActivity::class.java)
            intent.putExtra("uri", action.uri)
            intent.putExtra("extras", action.extras)
            action.context?.startActivity(intent)
            // 阻断后续拦截器执行
            chain.onIntercept()
        }
        chain.proceed(action)
    }

    // 判断是否为要求进行登录检查的路由链接。
    private fun checkRequestLogin(uri: Uri?):Boolean {
        return uri?.getQueryParameter("requestLogin") == "1"
    }

}