package com.okay.sampletamplate.lifecycle

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.okay.sampletamplate.MainFragment
import com.okay.sampletamplate.R
import java.lang.IllegalArgumentException

/**
 * @author Created by cz
 * @date 2019/4/22 下午3:30
 * @email chenzhen@okay.cn
 *
 */
internal class SampleActivityLifecycleCallback(private val application: Application):Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        val activity=activity?:return
        val languageActivityName=getLaunchActivityName(application.applicationContext)
        if(languageActivityName==activity::class.java.name){
            //进入主activity
            if(activity !is AppCompatActivity){
                throw IllegalArgumentException("The launch activity should use AppcompatActivity! not activity!")
            } else {
                if(null==savedInstanceState){
                    //替换布局
                    activity.supportFragmentManager.beginTransaction().add(android.R.id.content, MainFragment()).commit()
                    //这里不能解除注册,因为如果application不重启,则下次重新进来,不会自动添加fragment对象
                    //application.unregisterActivityLifecycleCallbacks(this)
                }
            }
        }
    }

    /**
     * 检测标题样式
     * 暂时不加入
     */
    private fun checkActivityStyle(context:Context){
        val a = context.obtainStyledAttributes(android.support.v7.appcompat.R.styleable.AppCompatTheme)
        if (!a.hasValue(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowActionBar)) {
            a.recycle()
            throw IllegalStateException(
                "You need to use a Theme.AppCompat theme (or descendant) with this activity."
            )
        } else {
            val windowActionBar=a.getBoolean(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowActionBar,true)
            val windowNoTitle=a.getBoolean(android.support.v7.appcompat.R.styleable.AppCompatTheme_windowNoTitle,false)
            if(windowActionBar||!windowNoTitle){
                throw IllegalStateException(
                    "You need to use a Theme.AppCompat theme and set windowActionBar=\"false\" windowNoTitle=\"true\"!"
                )
            }
            a.recycle()
        }
    }



    override fun onActivityStarted(activity: Activity?) {
        val activity=activity?:return
        val languageActivityName=getLaunchActivityName(application.applicationContext)
        if(languageActivityName==activity::class.java.name){
            val contentLayout=activity.findViewById<ViewGroup>(android.R.id.content)
            val sampleFragmentContainer=contentLayout.findViewById<View>(R.id.sampleFragmentContainer)
            if(null==sampleFragmentContainer){
                contentLayout.removeAllViews()
            } else {
                //移除其他控件无用控件,只保存替换的MainFragment内的布局体
                (0 until contentLayout.childCount).
                    map { contentLayout.getChildAt(it) }.
                    filter { it!=sampleFragmentContainer }.
                    forEach { contentLayout.removeView(it) }

            }
        }
    }

    override fun onActivityPaused(activity: Activity?) {
    }

    override fun onActivityResumed(activity: Activity?) {
    }


    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
    }

    private fun getLaunchActivityName(context: Context): String {
        val packageManager = context.packageManager
        val launchIntent = packageManager.getLaunchIntentForPackage(context.packageName)
        return launchIntent.component.className
    }

}