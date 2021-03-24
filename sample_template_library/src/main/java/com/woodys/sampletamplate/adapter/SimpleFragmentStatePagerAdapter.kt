package com.woodys.sampletamplate.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup

import java.util.*

/**
 * @author :Created by cz
 * @date 2019/4/2 上午10:27
 * @email chenzhen@woodys.cn
 *
 * 可编辑fragment条目的viewpager的adapter
 */
class SimpleFragmentStatePagerAdapter constructor(fm: FragmentManager, fragments: List<Fragment>, titles: List<CharSequence>? = null) : FragmentStatePagerAdapter(fm) {
    private val fragments: MutableList<Fragment>
    private val titles: MutableList<CharSequence>
    init {
        this.fragments = ArrayList()
        this.fragments.addAll(fragments.toList())
        this.titles = ArrayList()
        if (null != titles) {
            this.titles.addAll(titles)
        }
        if(!this.titles.isEmpty()&&this.titles.size!=this.fragments.size){
            throw IllegalArgumentException("title.size:${this.titles.size} != fragments.size:${this.fragments.size}")
        }
    }
    override fun getPageTitle(position: Int): CharSequence =titles[position]

    override fun getItem(position: Int): Fragment =fragments[position]

    override fun getCount(): Int =fragments.size

    override fun setPrimaryItem(container: ViewGroup, position: Int, fragment: Any) {
        super.setPrimaryItem(container, position, fragment)
    }

    override fun finishUpdate(container: ViewGroup) {
        try {
            super.finishUpdate(container)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun swapPageTitles(titles: List<CharSequence>?){
        titles?.let{
            this.titles.clear()
            this.titles.addAll(it)
        }
    }

    /**
     * 替换数据源
     * @param fragments
     */
    fun swapFragments(fragments: List<Fragment>) {
        fragments.let {
            this.fragments.clear()
            this.fragments.addAll(it)
            notifyDataSetChanged()
        }
    }

}

