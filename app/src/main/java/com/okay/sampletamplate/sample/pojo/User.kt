package com.okay.sampletamplate.sample.pojo

import android.os.Bundle
import com.okay.router.annotation.Route
import com.okay.router.callback.ICreatorInjector

@Route("creator/user")
class User(): ICreatorInjector {
    var name:String? = null
    override fun inject(bundle: Bundle?) {
        name = bundle?.getString("name")
    }
    constructor(name:String?):this() {
        this.name = name
    }
    override fun toString(): String {
        return "User(name=$name)"
    }

}