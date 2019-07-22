package com.okay.sampletamplate.sample.action

import android.content.Context
import android.os.Bundle
import com.okay.router.annotation.Route
import com.okay.router.route.ActionSupport
import com.okay.sampletamplate.utlis.EasyToast

@Route("say/hello")
class SayHelloAction:ActionSupport() {
    override fun invoke(context: Context?, bundle: Bundle?) {
        context?.let { EasyToast.create(it).show("Hello! this is an action route!") }
    }
}