package com.example.ibrahim.application2

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RelativeLayout

class EntryFormView : RelativeLayout, View.OnClickListener {
    interface OnEntrySubmittedListener {

        fun onEntrySubmitted(name: CharSequence, email: CharSequence)
    }

    private val mNameText: EditText? = null
    private val mEmailText: EditText? = null
    private var mListener: EntryFormView.OnEntrySubmittedListener? = null


    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        LayoutInflater.from(context).inflate(R.layout.notification_action, this)
        setBackgroundResource(R.drawable.ic_launcher_background)

    }


    override fun onClick(v: View?) {
        val imm: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mNameText!!.windowToken, 0)
        if (mListener != null) mListener!!.onEntrySubmitted(mNameText.text, mEmailText!!.text)

        mNameText.text = null
        mEmailText!!.text = null
    }

    fun setOnEntrySubmittedListener(listener: OnEntrySubmittedListener) {
        mListener = listener
    }
}