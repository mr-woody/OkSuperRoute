package com.woodys.sampletamplate.sample.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.woodys.router.annotation.Route
import com.woodys.sampletamplate.sample.R

/**
 * 提供resultCode的页面。
 */
@Route("result")
class ResultActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        findViewById<View>(R.id.backWithoutIntent).setOnClickListener { finish()}
        findViewById<View>(R.id.backWithIntent).setOnClickListener {
            val intent = Intent()
            intent.putExtra("value", "返回数据")
            setResult(1001, intent)
            finish()
        }
    }
}