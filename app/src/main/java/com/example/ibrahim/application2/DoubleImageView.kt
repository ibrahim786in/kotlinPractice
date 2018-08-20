package com.example.ibrahim.application2

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View

class DoubleImageView : View {
    private var mLeftDrawable: Drawable? = null
    private var mRightDrawable: Drawable? = null
    private var mText: CharSequence? = null
    private var mTextLayout: StaticLayout? = null
    private var mTextPaint: TextPaint? = null
    private var mTextOrigin: Point? = null
    private var mSpacing: Int = 0

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {

        mTextOrigin = Point(0, 0)

        val a: TypedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ActionBar, 0, defStyle)
        a.recycle()

        var d = a.getDrawable(R.styleable.ActionBar_contentInsetLeft)
        if (d != null) setLeftDrawable(d)
        d = a.getDrawable(R.styleable.ActionBar_contentInsetRight)
        if (d != null) setRightDrawable(d)

        val spacing = a.getDimensionPixelOffset(R.styleable.MenuView_preserveIconSpacing, 0)
        setSpacing(spacing)
        mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = a.getColor(R.styleable.DrawerArrowToggle_color, 0)
            textSize = a.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0).toFloat()
        }
        val text = a.getText(R.styleable.AppCompatTextView_android_textAppearance)
        setText(text)
        a.recycle()
    }

    private fun setLeftDrawableResource(resId: Int) {
        val d = resources.getDrawable(resId)
        setLeftDrawable(d)
    }

    private fun setLeftDrawable(left: Drawable) {
        mLeftDrawable = left
        updateContentBounds()
        invalidate()
    }

    private fun setRightDrawableResource(resId: Int) {
        val d: Drawable = resources.getDrawable(resId)
        setRightDrawable(d)
    }

    private fun setRightDrawable(right: Drawable) {
        mRightDrawable = right
        updateContentBounds()
        invalidate()
    }

    private fun setText(resId: Int) {
        val text: CharSequence = resources.getText(resId)
        setText(text)
    }

    private fun setText(text: CharSequence) {
        if (!TextUtils.equals(mText, text)) {
            mText = text
            updateContentBounds()
            invalidate()
        }
    }

    private fun setSpacing(spacing: Int) {
        mSpacing = spacing
        updateContentBounds()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize: Int = resolveSize(getDesiredWidth(), widthMeasureSpec)
        val heightSize: Int = resolveSize(getDesiredHeight(), widthMeasureSpec)
        setMeasuredDimension(widthSize, heightSize)
    }

    private fun getDesiredWidth(): Int {
        val leftWidth = if (mLeftDrawable == null) 0
        else mLeftDrawable!!.intrinsicWidth

        val rightWidth = if (mRightDrawable == null) 0
        else mRightDrawable!!.intrinsicWidth

        val textWidth = if (mTextLayout == null) 0
        else mTextLayout!!.getWidth();


        return ((leftWidth * 0.67f).toInt()) + ((rightWidth * 0.067f).toInt()) + mSpacing + textWidth
    }

    private fun getDesiredHeight(): Int {
        val leftHeight = if (mLeftDrawable == null) 0
        else mLeftDrawable!!.intrinsicWidth

        val rightHeight = if (mRightDrawable == null) 0
        else mRightDrawable!!.intrinsicWidth

        return ((leftHeight * 0.67f).toInt()) + ((rightHeight * 0.067f).toInt())
    }

    private fun updateContentBounds() {
        if (mText == null) mText = ""
        val textWidth = mTextPaint!!.measureText(mText, 0, mText!!.length)
        mTextLayout = StaticLayout(mText, mTextPaint, textWidth.toInt(), Layout.Alignment.ALIGN_CENTER, 1f, 0f, true)
        var left = (width - getDesiredHeight()) / 2
        var top = (height - getDesiredHeight()) / 2

        if (mLeftDrawable != null) {
            mLeftDrawable!!.setBounds(left, top, left + mRightDrawable!!.intrinsicWidth, top + mRightDrawable!!.intrinsicHeight)
            left += ((mLeftDrawable!!.intrinsicWidth * 0.33f)).toInt()
            top += ((mLeftDrawable!!.intrinsicWidth * 0.33f)).toInt()

        }
        if (mRightDrawable != null) {
            mRightDrawable!!.setBounds(left, top, left + mRightDrawable!!.intrinsicWidth, top + mRightDrawable!!.intrinsicHeight)
            left = mRightDrawable!!.bounds.right + mSpacing
        }
        if (mTextLayout != null) {
            top = (height - mTextLayout!!.height) / 2
            mTextOrigin!!.set(left, top)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (w != oldw || h != oldh) updateContentBounds()
    }

    override fun onDraw(canvas: Canvas) {
        if (mLeftDrawable != null)
            mLeftDrawable!!.draw(canvas)
        if (mTextLayout != null) {
            canvas.save()
            canvas.translate(mTextOrigin!!.x.toFloat(), mTextOrigin!!.y.toFloat())
            mTextLayout!!.draw(canvas)
            canvas.restore()
        }
        if (mRightDrawable != null) mRightDrawable!!.draw(canvas)
    }
}