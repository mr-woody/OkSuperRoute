package com.okay.sampletamplate.adapter

import android.app.Activity
import android.app.Dialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.okay.sampletamplate.FuncTemplate
import com.okay.sampletamplate.R
import com.okay.sampletamplate.item.SampleItem

/**
 * @author :Created by cz
 * @date 2019-05-09 14:21
 * @email chenzhen@okay.cn
 * 演示示例模板Adapter对象
 */
class TemplateAdapter(val activity: FragmentActivity, private val items:List<SampleItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val layoutInflater= LayoutInflater.from(activity)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object:RecyclerView.ViewHolder(layoutInflater.inflate(R.layout.sample_item,parent,false)){}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item=items[position]
        holder.itemView.findViewById<TextView>(android.R.id.text1).text=item.title
        holder.itemView.findViewById<TextView>(android.R.id.text2).text=item.desc
        holder.itemView.setOnClickListener {
            val context=it.context
            val clazz=item.clazz
            if(item.id in FuncTemplate){
                it.context.startActivity(Intent().apply {
                    //设置启动activity信息
                    component = getLaunchActivityComponent(context)
                    putExtra("id",item.id)
                    putExtra("title",item.title)
                    putExtra("desc",item.desc)
                })//子分组
            } else if(null==clazz){
                val callback = item.callback
                if(null==callback){
                    Toast.makeText(it.context,R.string.invalid_class,Toast.LENGTH_SHORT).show()
                } else {
                    //采用动态构建对象展示,当前仅用于Dialog,未来看其他需要可扩展到其他类型
                    val any = callback.invoke(context)
                    when (any) {
                        is Dialog -> any.show()
                        is DialogFragment -> any.show(activity.supportFragmentManager,null)
                        is android.app.DialogFragment -> any.show(activity.fragmentManager,null)
                        else -> Toast.makeText(it.context,R.string.invalid_class,Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                when {
                    Activity::class.java.isAssignableFrom(clazz) -> {
                        it.context.startActivity(Intent(context,item.clazz).apply { putExtra("title",item.title) })//子条目
                    }
                    Dialog::class.java.isAssignableFrom(clazz) -> {
                        if(null!=context){
                            try {
                                val constructor = clazz.getConstructor(Context::class.java)
                                val dialog = constructor.newInstance(context) as Dialog
                                dialog.show()
                            } catch (e:Exception){
                                Toast.makeText(context,R.string.open_dialog_failed,Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    DialogFragment::class.java.isAssignableFrom(clazz) -> {
                        if(null!=context){
                            try {
                                val dialog = clazz.newInstance() as DialogFragment
                                dialog.show(activity.supportFragmentManager,null)
                            } catch (e:Exception){
                                Toast.makeText(context,R.string.open_dialog_failed,Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    android.app.DialogFragment::class.java.isAssignableFrom(clazz) -> {
                        if(null!=context){
                            try {
                                val dialog = clazz.newInstance() as android.app.DialogFragment
                                dialog.show(activity.fragmentManager,null)
                            } catch (e:Exception){
                                Toast.makeText(context,R.string.open_dialog_failed,Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int=items.size

    /**
     * 获得启动activity信息
     */
    private fun getLaunchActivityComponent(context: Context): ComponentName {
        val packageManager = context.packageManager
        val launchIntent = packageManager.getLaunchIntentForPackage(context.packageName)
        return launchIntent.component
    }
}