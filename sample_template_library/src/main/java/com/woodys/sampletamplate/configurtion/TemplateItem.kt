package com.woodys.sampletamplate.configurtion

import android.content.Context
import com.woodys.sampletamplate.FuncTemplate
import com.woodys.sampletamplate.item.SampleItem
import java.lang.IllegalArgumentException

/**
 * @author Created by cz
 * @date 2019-05-09 14:23
 * @email chenzhen@woodys.cn
 * 模板配置对象
 */
class TemplateItem(private val applicationContext:Context){

    fun item(closure: SampleItem.() -> Unit) {
        val sampleItem = SampleItem().apply(closure)
        val launchActivityName = getLaunchActivityName(applicationContext)
        // 增加了主activity配置判断
        if(sampleItem.clazz?.name==launchActivityName){
            throw IllegalArgumentException("Illegal Activity class:$launchActivityName sample item,This Activity is use for Sample list! Don't use it for a sample!")
        }
        FuncTemplate.items.add(sampleItem)
    }

    private fun getLaunchActivityName(context: Context): String {
        val packageManager = context.packageManager
        val launchIntent = packageManager.getLaunchIntentForPackage(context.packageName)
        return launchIntent.component.className
    }
}