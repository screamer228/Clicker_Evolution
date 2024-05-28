package com.example.clickerevolution.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import com.example.clickerevolution.R


class TextViewOutline @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
    AppCompatTextView(context!!, attrs) {
    private var mOutlineSize = 0
    private var mOutlineColor = 0
    private var mTextColor = 0
    private var mShadowRadius = 0f
    private var mShadowDx = 0f
    private var mShadowDy = 0f
    private var mShadowColor = 0

    init {
        setAttributes(attrs)
    }


    private fun setAttributes(attrs: AttributeSet?) {
        // set defaults
        mOutlineSize = DEFAULT_OUTLINE_SIZE
        mOutlineColor = DEFAULT_OUTLINE_COLOR
        // text color
        mTextColor = currentTextColor
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.TextViewOutline)
            // outline size
            if (a.hasValue(R.styleable.TextViewOutline_outlineSize)) {
                mOutlineSize = a.getDimension(
                    R.styleable.TextViewOutline_outlineSize,
                    DEFAULT_OUTLINE_SIZE.toFloat()
                ).toInt()
            }
            // outline color
            if (a.hasValue(R.styleable.TextViewOutline_outlineColor)) {
                mOutlineColor =
                    a.getColor(R.styleable.TextViewOutline_outlineColor, DEFAULT_OUTLINE_COLOR)
            }
            // shadow (the reason we take shadow from attributes is because we use API level 15 and only from 16 we have the get methods for the shadow attributes)
            if (a.hasValue(R.styleable.TextViewOutline_android_shadowRadius)
                || a.hasValue(R.styleable.TextViewOutline_android_shadowDx)
                || a.hasValue(R.styleable.TextViewOutline_android_shadowDy)
                || a.hasValue(R.styleable.TextViewOutline_android_shadowColor)
            ) {
                mShadowRadius = a.getFloat(R.styleable.TextViewOutline_android_shadowRadius, 0f)
                mShadowDx = a.getFloat(R.styleable.TextViewOutline_android_shadowDx, 0f)
                mShadowDy = a.getFloat(R.styleable.TextViewOutline_android_shadowDy, 0f)
                mShadowColor =
                    a.getColor(R.styleable.TextViewOutline_android_shadowColor, Color.TRANSPARENT)
            }

            a.recycle()
        }

        Log.d(TAG, "mOutlineSize = $mOutlineSize")
        Log.d(TAG, "mOutlineColor = $mOutlineColor")
    }

    private fun setPaintToOutline() {
        val paint: Paint = paint
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = mOutlineSize.toFloat()
        super.setTextColor(mOutlineColor)
        super.setShadowLayer(mShadowRadius, mShadowDx, mShadowDy, mShadowColor)
    }

    private fun setPaintToRegular() {
        val paint: Paint = paint
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 0f
        super.setTextColor(mTextColor)
        super.setShadowLayer(0f, 0f, 0f, Color.TRANSPARENT)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setPaintToOutline()
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun setTextColor(color: Int) {
        super.setTextColor(color)
        mTextColor = color
    }

    override fun setShadowLayer(radius: Float, dx: Float, dy: Float, color: Int) {
        super.setShadowLayer(radius, dx, dy, color)
        mShadowRadius = radius
        mShadowDx = dx
        mShadowDy = dy
        mShadowColor = color
    }



    fun setOutlineSize(size: Int) {
        mOutlineSize = size
    }

    fun setOutlineColor(color: Int) {
        mOutlineColor = color
    }

    override fun onDraw(canvas: Canvas) {
        setPaintToOutline()
        super.onDraw(canvas)
        setPaintToRegular()
        super.onDraw(canvas)
    }

    companion object {
        /*
    Add to attrs.xml:
        <declare-styleable name="TextViewOutline">
        <attr name="outlineSize" format="dimension"/>
        <attr name="outlineColor" format="color|reference"/>
        <attr name="android:shadowRadius"/>
        <attr name="android:shadowDx"/>
        <attr name="android:shadowDy"/>
        <attr name="android:shadowColor"/>
        </declare-styleable>
    Usage:
    textView.setOutlineSize(10);
    textView.setOutlineColor(Color.WHITE);
     */
        private val TAG: String = TextViewOutline::class.java.simpleName
        private const val DEFAULT_OUTLINE_SIZE = 0
        private const val DEFAULT_OUTLINE_COLOR = Color.TRANSPARENT
    }
}