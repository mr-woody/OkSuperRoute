package com.woodys.sampletamplate

import android.app.Activity
import com.woodys.sampletamplate.item.SampleItem

/**
 * Created by cz on 2017/6/8.
 */
object FuncTemplate {
    /**
     * 文档信息
     */
    internal var document:String?=null
    internal val items = mutableListOf<SampleItem>()
    internal val groupItems = mutableMapOf<Int, List<SampleItem>>()

    operator fun get(id: Int?) = groupItems[id]

    operator fun contains(id: Int?) = groupItems.any { it.key == id }
}