package com.okay.sampletamplate.configurtion

import android.app.Application
import com.okay.sample.library.lifecycle.SampleActivityLifecycleCallback
import com.okay.sampletamplate.FuncTemplate

/**
 * @author Created by cz
 * @date 2019-05-09 14:51
 * @email chenzhen@okay.cn
 * 配置入口对象,用于初始化类
 */
object TemplateConfiguration{
    fun init(application: Application, closure: TemplateItem.() -> Unit){
        //注册LifeCycleCallback,替换LaunchActivity内容
        application.registerActivityLifecycleCallbacks(SampleActivityLifecycleCallback(application))
        //应用配置
        TemplateItem(application.applicationContext).apply(closure)
        //进行分组
        FuncTemplate.groupItems += FuncTemplate.items.groupBy { it.pid }
        //配置文档信息
        val document = application::class.java.getAnnotation(Document::class.java)
        if(null!=document){
            FuncTemplate.document=document.value
        }
    }
}
