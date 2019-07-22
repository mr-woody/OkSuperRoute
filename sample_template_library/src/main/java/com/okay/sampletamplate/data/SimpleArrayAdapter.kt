package com.okay.widgets.common.sample.data

import android.content.Context
import android.support.annotation.ArrayRes
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import java.util.*

/**
 * @author Created by cz
 * @date 2019-05-27 17:10
 * @email chenzhen@okay.cn
 * 简单的RecyclerView展示适配器对象
 */
class SimpleArrayAdapter<E>(context: Context, @param:LayoutRes private val layout: Int, items: List<E>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val layoutInflater=LayoutInflater.from(context)
    private val items= mutableListOf<E>()
    companion object {
        fun createFromResource(context: Context, @ArrayRes res: Int): SimpleArrayAdapter<*> {
            return SimpleArrayAdapter(context, context.resources.getStringArray(res))
        }
    }

    constructor(context: Context, items: Array<E>) : this(context, android.R.layout.simple_list_item_1, Arrays.asList(*items))

    constructor(context: Context, @LayoutRes layout: Int, items: Array<E>) : this(context, layout, Arrays.asList(*items))

    constructor(context: Context, items: List<E>) : this(context, android.R.layout.simple_list_item_1, items)

    init {
        this.items.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object:RecyclerView.ViewHolder(layoutInflater.inflate(layout,parent, false)){}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val textView = holder.itemView as TextView
        val item = getItem(position)
        if (null != item) {
            textView.text = item.toString()
        }
    }

    override fun getItemCount(): Int=items.size

    private fun getItem(position: Int): E =items[position]

}
