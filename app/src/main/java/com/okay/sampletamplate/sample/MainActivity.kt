package com.okay.sampletamplate.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * @author :Created by cz
 * @date 2019-05-09 15:44
 * @email chenzhen@okay.cn
 * 注册此类只是作为演示入口,不论写不写contentView,都不会影响演示列表
 * @see com.okay.sample.library.lifecycle.SampleActivityLifecycleCallback
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
