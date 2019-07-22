package com.okay.sampletamplate.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import com.okay.sampletamplate.R


/**
 * Created by cz on 16/3/7.
 */
class SeekLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {
    private var listener: OnSeekProgressChangeListener? = null

    init {
        orientation = LinearLayout.VERTICAL
        View.inflate(context, R.layout.seek_layout, this)
        val infoView = findViewById<TextView>(R.id.info)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)

        val a = context.obtainStyledAttributes(attrs, R.styleable.SeekLayout)
        seekBar.max = a.getInteger(R.styleable.SeekLayout_sl_max, 100)
        val info = a.getString(R.styleable.SeekLayout_sl_info)
        infoView.text = info
        a.recycle()
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                infoView.text = "$info:$progress"
                listener?.onProgressChanged(seekBar, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })
    }

    override fun setOrientation(orientation: Int) {
        super.setOrientation(LinearLayout.VERTICAL)
    }

    fun setOnSeekProgressChangeListener(listener: OnSeekProgressChangeListener) {
        this.listener = listener
    }

    interface OnSeekProgressChangeListener {
        fun onProgressChanged(seekBar: SeekBar, progress: Int)
    }
}
