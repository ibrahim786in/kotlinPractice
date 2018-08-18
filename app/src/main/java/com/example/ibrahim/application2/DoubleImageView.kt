package com.example.ibrahim.application2

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Paint
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.graphics.drawable.Drawable
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

class DoubleImageView : View {
    private var mLeftDrawable: Drawable? = null
    private val mText: CharSequence? = null
    private val mTextLayout: StaticLayout? = null
    private var mTextPaint: TextPaint? = null
    private var mTextOrigin: Point? = null
    private val mSpacing: Int = 0

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        mTextOrigin = Point(0, 0)

        val a: TypedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ActionBar, 0, defStyle)
        a.recycle()
        var d: Drawable = a.getDrawable(R.styleable.ActionBar_contentInsetLeft)
        if (d != null) setLeftDrawable(d)
        d = a.getDrawable(R.styleable.ActionBar_contentInsetRight)
        if (d != null) setRightDrawable(d)

        val spacing: Int = a.getDimensionPixelOffset(R.styleable.MenuView_preserveIconSpacing, 0)
        setSpacing(spacing)
        val color = a.getColor(R.styleable.DrawerArrowToggle_color, 0)
        mTextPaint!!.color = color
        val rawSize: Int = a.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0)
        mTextPaint!!.textSize = rawSize.toFloat()
        val text: CharSequence = a.getText(R.styleable.AppCompatTextView_android_textAppearance)
        setText(text)
        a.recycle()
    }

    public fun setLeftDrawableResource(resId: Int) {
        val d: Drawable = resources.getDrawable(resId)
        setLeftDrawable(d)
    }

    private fun setLeftDrawable(left: Drawable) {
        mLeftDrawable = left
        setRightDrawableResource()
        invalidate()
    }


}