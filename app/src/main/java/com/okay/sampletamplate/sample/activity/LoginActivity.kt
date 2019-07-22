package com.okay.sampletamplate.sample.activity

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.okay.router.Router
import com.okay.sampletamplate.sample.manager.DataManager
import com.okay.router.extras.RouteBundleExtras
import com.okay.sampletamplate.sample.R

class LoginActivity:AppCompatActivity() {

    var uri:Uri? = null
    var extras:RouteBundleExtras? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        uri = intent?.getParcelableExtra("uri")
        extras = intent?.getParcelableExtra("extras")

        findViewById<View>(R.id.login).setOnClickListener {
            DataManager.login = true
            // 登录完成后恢复路由启动。
            Router.resume(uri, extras).open(this)
            finish()
        }
    }
}

