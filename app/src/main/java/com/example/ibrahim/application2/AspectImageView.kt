package com.example.ibrahim.application2

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView

class AspectImageView : ImageView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributes: AttributeSet) : super(context, attributes)
    constructor(context: Context, attributes: AttributeSet, defStyleAttr: Int) : super(context, attributes, defStyleAttr)

    override fun onMeasure(widthMEasureSpec: Int, heightMeasureSpec: Int) {
        val desireSize: Int
        val aspect: Float

        if (drawable == null) {
            desireSize = 0
            aspect = 1f
        } else {
            desireSize = drawable.intrinsicHeight
            aspect = drawable.intrinsicWidth.toFloat() / drawable.intrinsicHeight
        }
        var widthSize = View.resolveSize(desireSize, widthMEasureSpec)
        var heightSize = (widthSize / aspect).toInt()

        val specMode = MeasureSpec.getMode(heightMeasureSpec)
        val specSize = MeasureSpec.getSize(heightMeasureSpec)
        if (specMode == MeasureSpec.AT_MOST || specMode == MeasureSpec.EXACTLY) {
            if (heightSize > specSize) {
                heightSize = specSize
                widthSize = (heightSize * aspect).toInt()
            }
        }
        setMeasuredDimension(widthSize, heightSize)
    }

}