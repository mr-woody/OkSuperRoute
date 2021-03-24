package com.woodys.sampletamplate.sample.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.woodys.router.annotation.Route
import com.woodys.router.annotation.RouteInterceptors
import com.woodys.sampletamplate.sample.interceptors.LoginInterceptor
import com.woodys.sampletamplate.sample.R

/**
 * 用户信息展示页
 */
@RouteInterceptors(LoginInterceptor::class)// 指定所有往此页面跳转的路由，均要进行登录检查
@Route("user-info")
class UserActivity : AppCompatActivity(){

    var username:String? = null

    val userTv:TextView by lazy { findViewById<TextView>(R.id.username) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        username = intent?.getStringExtra("username")
        userTv.text = "用户名为【$username】"
    }
}