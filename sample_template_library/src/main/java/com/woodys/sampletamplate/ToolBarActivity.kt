package com.woodys.sampletamplate

import android.graphics.Color
import android.os.Build
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.woodys.sampletamplate.R

/**
 * Created by cz on 2017/6/26.
 */
open class ToolBarActivity :AppCompatActivity(){
    private lateinit var toolBar: Toolbar
    private lateinit var indeterminate: ProgressBar

    override fun setContentView(@LayoutRes layoutResID: Int) {
        val layout = RelativeLayout(this)
        //初始化content
        val contentView = layoutInflater.inflate(layoutResID, layout, false)
        toolBar = Toolbar(ContextThemeWrapper(this, R.style.AppTheme_AppBarOverlay))
        obtainStyledAttributes(intArrayOf(R.attr.colorPrimary)).apply {
            var colorPrimary:Int = Color.BLUE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                colorPrimary = getColor(0, Color.BLUE)
            }
            //设置toolBar背景颜色
            toolBar.setBackgroundColor(colorPrimary)
            recycle()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolBar.elevation= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,4f,resources.displayMetrics)
        }
        toolBar.id=R.id.toolBar
        //初始化indeterminate
        indeterminate = ProgressBar(this, null, android.R.attr.progressBarStyleSmall)
        indeterminate.visibility = View.GONE
        var layoutParams = Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.RIGHT or Gravity.CENTER_VERTICAL
        toolBar.addView(indeterminate, layoutParams)

        layout.addView(toolBar,RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT)
        val layoutParams2 = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        layoutParams2.addRule(RelativeLayout.BELOW, R.id.toolBar)
        layout.addView(contentView, layoutParams2)

        setSupportActionBar(toolBar)
        //初始化title
        supportActionBar?.title = intent.getStringExtra("title")
        supportActionBar?.subtitle = intent.getStringExtra("desc")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolBar.setNavigationOnClickListener { finish() }
        setContentView(layout)
    }

    /**
     * 是否显示加载旋转框

     * @param showIndeterminate
     */
    fun showIndeterminate(showIndeterminate: Boolean) {
        indeterminate.visibility = if (showIndeterminate) View.VISIBLE else View.GONE
    }
}