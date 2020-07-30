package com.okay.sampletamplate.sample.interceptors

import android.content.Intent
import com.okay.sampletamplate.sample.manager.DataManager
import com.okay.router.interceptors.RouteInterceptor
import com.okay.sampletamplate.sample.activity.LoginActivity

/**
 * 登录拦截器。
 */
class LoginInterceptor : RouteInterceptor {

    override fun intercept(chain: RouteInterceptor.ActionChain) {
        val action = chain.action()
        // 判断是否已登录。已登录：不拦截、登录：拦截
        if(!DataManager.login){
            // 拦截后，将数据传递到登录页去。待登录完成后进行路由恢复
            val intent:Intent = Intent(action.context, LoginActivity::class.java)
            intent.putExtra("uri", action.uri)
            intent.putExtra("extras", action.extras)
            action.context?.startActivity(intent)

            chain.onIntercept()
        }
        chain.proceed(action)
    }


}