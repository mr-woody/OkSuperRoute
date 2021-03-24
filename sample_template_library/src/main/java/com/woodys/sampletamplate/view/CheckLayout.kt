package com.woodys.sampletamplate.view


import android.content.Context
import android.support.v7.widget.AppCompatCheckBox
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.woodys.sampletamplate.R
import java.util.*


/**
 * Created by cz on 16/3/7.
 */
class CheckLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {
    private val checkItems: ArrayList<Int> = ArrayList()
    private var listener: OnCheckedListener? = null

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CheckLayout)
        setItems(a.getTextArray(R.styleable.CheckLayout_cl_items))
        a.recycle()
    }

    /**
     * 设置复选 条目
     * @param items
     */
    private fun setItems(items: Array<CharSequence>?) {
        if (null != items) {
            removeAllViews()
            checkItems.clear()
            val length = items.size
            for (i in 0 until length) {
                val item = items[i]
                val appCompatCheckBox = AppCompatCheckBox(context)
                appCompatCheckBox.text = item
                appCompatCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        checkItems.add(i)
                    } else {
                        checkItems.remove(Integer.valueOf(i))
                    }
                    if (null != listener) {
                        listener!!.onChecked(buttonView, checkItems, i)
                    }
                }
                addView(appCompatCheckBox)
            }
        }
    }

    /**
     * 获得选中的位置
     */
    fun getCheckedPosotions():List<Int>{
        return checkItems
    }

    fun setOnCheckedListener(listener: OnCheckedListener) {
        this.listener = listener
    }

    interface OnCheckedListener {
        fun onChecked(v: View, items: ArrayList<Int>, position: Int)
    }
}
