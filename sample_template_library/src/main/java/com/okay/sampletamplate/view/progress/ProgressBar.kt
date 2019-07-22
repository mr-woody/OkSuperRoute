package com.okay.sampletamplate.view.progress

import android.animation.*
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.okay.sampletamplate.R

/**
 * Created by cz on 2017/7/20.
 * 此处说明,此控件默认配置为配置webview加载效果,webview,加载时,进度波动很大,让人感觉并不好,此控件主要为仿ios短信发送
 * 短信发送,ios的做法是一路狂奔至80%左右的进度.这时候如果还没有发送成功,进度就开始变得缓慢起来,中间如果随时发送成功了.则立即从当前进度快速加载到100%
 * 否则后面就变得异常缓存,此机制从头到尾给人一种,比较快的错觉.
 */
class ProgressBar(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : View(context, attrs, defStyleAttr){
    private var animator:Animator?=null
    private var progressDrawable:Drawable?=null
    private var normalDrawable:Drawable?=null
    private var drawableSize:Float=0f
    private var firstProgress=0
    private var firstDuration:Int=0
    private var firstMinDuration=0
    private var secondDuration:Int=0
    private var secondProgress:Int=0
    private var thirdDuration:Int=0
    private var progress:Float=0f
    //动画启动时间
    private var startAnimatorTime=0L
    private var max:Int=0
    private var min:Int=0
    private var pass=false
    constructor(context: Context):this(context,null, R.attr.progressBar)
    constructor(context: Context, attrs: AttributeSet?):this(context,attrs,R.attr.progressBar)

    init {
        context.obtainStyledAttributes(attrs, R.styleable.ProgressBar,defStyleAttr,R.style.ProgressBar).apply {
            setProgressDrawable(getDrawable(R.styleable.ProgressBar_pb_progressDrawable))
            setNormalDrawable(getDrawable(R.styleable.ProgressBar_pb_normalDrawable))
            setDrawableSize(getDimension(R.styleable.ProgressBar_pb_drawableSize,0f))
            setFirstDuration(getInteger(R.styleable.ProgressBar_pb_firstDuration,0))
            setFirstMinDuration(getInteger(R.styleable.ProgressBar_pb_firstMinDuration,0))
            setSecondDuration(getInteger(R.styleable.ProgressBar_pb_secondDuration,0))
            setFirstProgress(getInteger(R.styleable.ProgressBar_pb_firstProgress,0))
            setSecondProgress(getInteger(R.styleable.ProgressBar_pb_secondProgress,0))
            setThirdDuration(getInteger(R.styleable.ProgressBar_pb_thirdDuration,0))
            setProgress(getFloat(R.styleable.ProgressBar_pb_progress,0f))
            setMax(getInteger(R.styleable.ProgressBar_pb_max,0))
            setMin(getInteger(R.styleable.ProgressBar_pb_min,0))
            recycle()
        }
    }

    private fun setThirdDuration(duration: Int) {
        this.thirdDuration=duration;
    }

    fun setProgressDrawable(drawable: Drawable?) {
        this.progressDrawable=drawable
        invalidate()
    }

    fun setNormalDrawable(drawable: Drawable?) {
        this.normalDrawable=drawable
        invalidate()
    }

    fun setFirstDuration(duration: Int) {
        this.firstDuration=duration
        invalidate()
    }

    fun setFirstMinDuration(duration: Int) {
        this.firstMinDuration=duration
    }

    fun setFirstProgress(progress: Int) {
        this.firstProgress=progress
    }

    fun setSecondProgress(progress: Int) {
        if(this.firstProgress>=progress){
            throw IllegalArgumentException("first progress >= second progress!!")
        }
        this.secondProgress=progress
    }

    fun setSecondDuration(duration: Int) {
        this.secondDuration=duration
        invalidate()
    }

    fun setDrawableSize(size: Float) {
        this.drawableSize=size
        requestLayout()
    }

    fun setMax(max:Int){
        if(this.min>=max){
            throw IllegalArgumentException("min >= max!!")
        }
        this.max=max
        invalidate()
    }

    fun setMin(min:Int){
        if(min>=this.max){
            throw IllegalArgumentException("min >= max!!")
        }
        this.min=min
        invalidate()
    }

    fun setProgress(progress:Float){
        this.progress=progress
        invalidate()
    }

    fun getProgress()=this.progress

    fun getFirstProgress()=this.firstProgress


    /**
     * 启动进度动画
     */
    fun startProgressAnim(){
        animator?.cancel()
        startAnimatorTime=SystemClock.uptimeMillis()
        val totalDuration=firstDuration+secondDuration+thirdDuration
        val frame1=Keyframe.ofFloat(0f,0f)
        val frame2=Keyframe.ofFloat(firstDuration*1f/totalDuration,firstProgress.toFloat())
        val frame3=Keyframe.ofFloat((firstDuration+secondDuration)*1f/totalDuration,secondProgress.toFloat())
        val frame4=Keyframe.ofFloat(totalDuration*1f/totalDuration,max.toFloat())
        animator = ObjectAnimator.ofPropertyValuesHolder(this,PropertyValuesHolder.ofKeyframe("progress",frame1,frame2,frame3,frame4)).apply {
            duration = totalDuration.toLong()
            interpolator= DecelerateInterpolator()
            start()
        }
    }

    /**
     * 立即通过进度动画
     */
    fun passProgressAnim(closure:(()->Unit)?=null){
        val intervalTime=SystemClock.uptimeMillis()-startAnimatorTime
        if(intervalTime>firstMinDuration){
            startPassAnimatorInner(closure)
        } else {
            postDelayed({startPassAnimatorInner(closure)},firstMinDuration-intervalTime)
        }
    }

    private fun startPassAnimatorInner(closure: (() -> Unit)?) {
        startAnimatorTime=SystemClock.uptimeMillis()
        animator?.cancel()
        if (!pass) {
            val valueAnimator = ValueAnimator.ofFloat(progress, max.toFloat())
            valueAnimator.interpolator = DecelerateInterpolator()
            valueAnimator.addUpdateListener {
                progress = it.animatedValue as Float
                invalidate()
            }
            valueAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    closure?.invoke()
                }
            })
            valueAnimator.start()
        }
    }

    /**
     * 重置进度动画
     */
    fun resetProgressAnim(){
        pass=false
        progress=0f
        animator?.cancel()
        invalidate()
    }

    /**
     * 取消进度动画
     */
    fun cancelProgressAnim(){
        animator?.cancel()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, (paddingTop+drawableSize+paddingBottom).toInt())
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val itemWidth=(width-paddingLeft-paddingRight)*1f/max
        normalDrawable?.run {
            setBounds(paddingLeft,paddingTop,width-paddingRight,height-paddingBottom)
            draw(canvas)
        }
        progressDrawable?.run {
            setBounds(paddingLeft,paddingTop, (paddingLeft+itemWidth*progress).toInt(),height-paddingBottom)
            draw(canvas)
        }
    }

    override fun onDetachedFromWindow() {
        animator?.cancel()
        super.onDetachedFromWindow()
    }


}