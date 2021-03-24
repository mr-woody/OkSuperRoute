package com.woodys.sampletamplate.sample

import android.app.Application
import com.woodys.router.Router
import com.woodys.router.annotation.RouteConfig
import com.woodys.router.configs.RouterConfiguration
import com.woodys.sampletamplate.sample.interceptors.DefaultInterceptor
import com.woodys.sample.RouterRuleCreator
import com.woodys.sampletamplate.configurtion.Document
import com.woodys.sampletamplate.configurtion.TemplateConfiguration
import com.woodys.sampletamplate.sample.activity.Sample1Activity
import com.woodys.sampletamplate.sample.activity.Sample2Activity
import com.woodys.sampletamplate.sample.activity.Sample3Activity

/**
 * @author Created by yuetao
 * @date 2019-07-17 10:45
 * @email yuetao@woodys.cn
 * 演示程序入口
 */
@Document("readme.md")
@RouteConfig(baseUrl = "woodys://page/", pack = "com.woodys.sample")
class SampleApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        //初始化演示模板
        initTemplate()

        // 注册通过apt生成的路由表
        RouterConfiguration.get().addRouteCreator(RouterRuleCreator())
        // 设置默认路由拦截器：所有路由跳转均会被触发(除了需要直接打开浏览器的链接)
        RouterConfiguration.get().interceptor = DefaultInterceptor()

        // 开启Router日志打印
        Router.DEBUG = true
    }

    /**
     * 初始化演示模板
     */
    private fun initTemplate() {
        TemplateConfiguration.init(this) {
            item {
                id = 1
                title = "页面路由"
                desc = "通过指定路由链接，从而启动某个页面"
                clazz = Sample1Activity::class.java
            }
            item {
                id = 2
                title = "动作路由"
                desc = "使用路由启动的，并不是需要启动某个页面，而是需要执行一些特殊的操作。"
                clazz = Sample2Activity::class.java
            }
            item {
                id = 3
                title = "对象路由"
                desc = "通过指定路由链接，创建出具体的对象实例提供使用"
                clazz = Sample3Activity::class.java
            }
        }
    }
}