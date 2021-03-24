package com.woodys.sampletamplate.view

import android.content.Context
import android.support.v7.widget.AppCompatRadioButton
import android.util.AttributeSet
import android.view.View
import android.widget.RadioGroup
import com.woodys.sampletamplate.R


/**
 * Created by cz on 16/3/7.
 */
class RadioLayout : RadioGroup {
    private var listener: OnCheckedListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.RadioLayout)
        setItems(a.getTextArray(R.styleable.RadioLayout_rl_items))
        a.recycle()
    }

    /**
     * 设置复选 条目

     * @param items
     */
    fun setItems(items: Array<CharSequence>?) {
        if (null != items) {
            removeAllViews()
            val length = items.size
            for (i in 0 until length) {
                val item = items[i]
                val appCompatCheckBox = AppCompatRadioButton(context)
                appCompatCheckBox.id=i
                appCompatCheckBox.text = item
                appCompatCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (null != listener && isChecked) {
                        listener?.onChecked(buttonView, i, isChecked)
                    }
                }
                addView(appCompatCheckBox)
            }
        }
    }

    /**
     * 获得选中的位置
     */
    fun getCheckedPosotion():Int{
        return checkedRadioButtonId
    }

    fun setOnCheckedListener(listener: OnCheckedListener) {
        this.listener = listener
    }

    interface OnCheckedListener {
        fun onChecked(v: View, position: Int, isChecked: Boolean)
    }
}
