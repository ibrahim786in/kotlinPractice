package com.example.ibrahim.application2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

class BoxGridLayout : ViewGroup {
    private var mColumnCount: Int
    private var mMaxChildren: Int
    private var mGridPaint: Paint? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        val DEFAULT_COUNT = 3
        val a = context.obtainStyledAttributes(attrs, R.styleable.ActionBar, 0, defStyle)

        val strokeWidth = a.getDimensionPixelSize(R.styleable.AppCompatTheme_checkboxStyle, 0)
        val strokeColor = a.getColor(R.styleable.FontFamilyFont_android_ttcIndex, Color.WHITE)
        mColumnCount = a.getInteger(R.styleable.AppCompatTheme_checkboxStyle, DEFAULT_COUNT)
        mMaxChildren = mColumnCount * mColumnCount

        a.recycle()
        mGridPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            color = strokeColor
            this.strokeWidth = strokeWidth.toFloat()
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = View.getDefaultSize(0, widthMeasureSpec)
        val heightSize = View.getDefaultSize(0, heightMeasureSpec)
        val majorDimension = Math.min(widthSize, heightSize)
        val blockDimension = majorDimension / mColumnCount
        val blockSpec = MeasureSpec.makeMeasureSpec(blockDimension, MeasureSpec.EXACTLY)
        measureChildren(blockSpec, blockSpec)
        setMeasuredDimension(majorDimension, majorDimension)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val left = (i % mColumnCount) * child.measuredWidth
            val top = (i / mColumnCount) * child.measuredHeight
            child.layout(left, top, left + child.measuredWidth, top + child.measuredHeight)
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        for (i in 0..width step (width / mColumnCount)) canvas.drawLine(i.toFloat(), 0F, i.toFloat(), height.toFloat(), mGridPaint)
        for (i in 0..height step (height / mColumnCount)) canvas.drawLine(0F, i.toFloat(), width.toFloat(), i.toFloat(), mGridPaint)

    }

    override fun addView(child: View?) {
        if (childCount > mMaxChildren - 1) throw IllegalStateException("BoxGridLayout cannot have more than $mMaxChildren direct children")
        super.addView(child)
    }

    override fun addView(child: View?, index: Int) {
        if (childCount > mMaxChildren - 1) throw IllegalStateException("BoxGridLayout cannot have more than $mMaxChildren direct children")
        super.addView(child, index)
    }

    override fun addView(child: View?, index: Int, params: LayoutParams) {
        if (childCount > mMaxChildren - 1) throw IllegalStateException("BoxGridLayout cannot have more than $mMaxChildren direct children")
        super.addView(child, index, params)
    }

    override fun addView(child: View?,params:LayoutParams) {
        if (childCount > mMaxChildren - 1) throw IllegalStateException("BoxGridLayout cannot have more than $mMaxChildren direct children")
        super.addView(child,params)
    }

    override fun addView(child: View?,width:Int,height:Int) {
        if (childCount > mMaxChildren - 1) throw IllegalStateException("BoxGridLayout cannot have more than $mMaxChildren direct children")
        super.addView(child,width,height)
    }

}



