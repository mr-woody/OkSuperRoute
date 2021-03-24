package com.woodys.sampletamplate.sample.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.woodys.router.annotation.Route
import com.woodys.sampletamplate.sample.R

@Route("intent/printer")
class IntentPrinterActivity: AppCompatActivity() {

    val mPrinter:TextView by lazy { findViewById<TextView>(R.id.printer_tv) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent_printer)

        var message = StringBuilder()
        if (intent == null
                || intent.extras == null
                || intent.extras.isEmpty) {
            message.append("当前页面的Intent中不含有数据")
        } else {
            val keys = intent.extras.keySet()
            message.append("当前页面的intent中含有一下${keys.size}条数据：\n")
            for (s in keys) {
                message.append("key = $s & value = ${intent.extras[s]}")
                message.append("\n")
            }
        }
        mPrinter.text = message
    }
}