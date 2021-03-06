package com.woodys.sampletamplate.sample.action

import android.content.Context
import android.os.Bundle
import com.woodys.router.annotation.Route
import com.woodys.router.annotation.RouteExecutor
import com.woodys.sampletamplate.sample.executor.TestExecutor
import com.woodys.router.route.ActionSupport
import com.woodys.sampletamplate.utlis.EasyToast

@RouteExecutor(TestExecutor::class)
@Route("executor/switcher")
class ExecutorAction : ActionSupport(){
    override fun invoke(context: Context?, bundle: Bundle?) {
        context?.let { EasyToast.create(it).show("线程切换器测试动作路由被启动，启动线程为：${Thread.currentThread().name}" ) }
    }
}