package com.woodys.sampletamplate.sample.action

import android.content.Context
import android.os.Bundle
import com.woodys.router.annotation.Route
import com.woodys.router.route.ActionSupport
import com.woodys.sampletamplate.utlis.EasyToast

@Route("say/hello")
class SayHelloAction:ActionSupport() {
    override fun invoke(context: Context?, bundle: Bundle?) {
        context?.let { EasyToast.create(it).show("Hello! this is an action route!") }
    }
}