package com.okay.sampletamplate.data

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.okay.sampletamplate.R

/**
 * @author :Created by cz
 * @date 2019-05-27 17:49
 * @email chenzhen@okay.cn
 * 演示ViewPager数据适配器对象
 */
class SimpleImagePagerAdapter(context:Context, private val items:List<String>) : PagerAdapter() {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val views= SparseArray<View>()

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var view = views.get(position)
        if(null==view){
            view=layoutInflater.inflate(R.layout.sample_pager_item, container, false)
            views.put(position,view)
        }
        view.findViewById<TextView>(R.id.textView).text=items[position]
        view.setOnClickListener { Toast.makeText(view.context,"点击:$position",Toast.LENGTH_SHORT).show() }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, item: Any) {
        if(item is View){
            container.removeView(item)
        }
    }

    override fun isViewFromObject(p0: View, p1: Any)=p0==p1

    override fun getCount(): Int =items.size
}
