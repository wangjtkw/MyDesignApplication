package com.example.mydesignapplication.myview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.pow

class PickerView : View {

    private val TAG = "PickerView"

    //内部显示数据项
    private lateinit var mDataList: List<String>

    private val MARGIN_ALPHA = 2.8f

    //当前数据索引
    private var mCurrentSelect = 0

    //触摸滑动距离
    private var mMoveLen = 0f

    private var mLastDownY = 0f

    private lateinit var mPaint: Paint

    private var mViewWidth = 0
    private var mViewHeight = 0

    private var mMaxTextSize = 50f
    private var mMinTextSize = 25f

    private var isInit: Boolean = false

    private var onSelectedCallback: (String) -> Unit = {
        Log.d(TAG, it)
    }

    //起止透明度
    private var mMaxTextAlpha = 255
    private var mMinTextAlpha = 100

    //显示文字颜色
    private var mColorText = 0x333333

    private var centerHeight = 0f

    private var myTimerTask: MyTimerTask? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        mDataList = ArrayList()
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.style = Paint.Style.FILL
        mPaint.textAlign = Paint.Align.CENTER
        mPaint.color = mColorText

    }

    fun setData(list: List<String>): PickerView {
        this.mDataList = list
        invalidate()
        return this
    }

    fun setOnSelectedCallBack(callback: (String) -> Unit): PickerView {
        this.onSelectedCallback = callback
        return this
    }

    fun setTextColor(color: String): PickerView {
        val mColor = Color.parseColor(color)
        mColorText = mColor
        return this
    }

    fun setTextAlpha(maxTextAlpha: Int, minTextAlpha: Int): PickerView {
        mMaxTextAlpha = maxTextAlpha
        mMinTextAlpha = minTextAlpha
        return this
    }

    fun setSelectIndex(index: Int): PickerView {
        if (index < 0 || index > mDataList.size) {
            throw IllegalArgumentException("the index is illegal")
        }
        mCurrentSelect = index
        invalidate()
        return this
    }

    fun getCurrentIndex(): Int {
        return mCurrentSelect
    }

    private fun performSelect() {
        onSelectedCallback(mDataList[mCurrentSelect])
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mViewHeight = measuredHeight
        mViewWidth = measuredWidth
//        mMaxTextSize = mViewHeight / 8.0f
//        mMinTextSize = mMaxTextSize / 2.0f
        centerHeight =
            measuredHeight / 2 - (mPaint.fontMetricsInt.top / 2f + mPaint.fontMetricsInt.bottom / 2f)
        invalidate()
        isInit = true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return
        if (isInit) drawData(canvas)
    }

    private fun drawData(canvas: Canvas) {
        Log.d(TAG, "mMoveLen $mMoveLen")
        // 先绘制选中的text再往上往下绘制其余的text
        val scale: Float = parabola(mViewHeight / 4.0f, mMoveLen)
        val size = (mMaxTextSize - mMinTextSize) * scale + mMinTextSize
        mPaint.textSize = size
        mPaint.alpha = ((mMaxTextAlpha - mMinTextAlpha) * scale + mMinTextAlpha).toInt()
        // text居中绘制，注意baseline的计算才能达到居中，y值是text中心坐标
        val x = (mViewWidth / 2.0).toFloat()
        val y = (mViewHeight / 2.0 + mMoveLen).toFloat()
        val fmi = mPaint.fontMetricsInt
        val baseline = (y - (fmi.bottom / 2.0 + fmi.top / 2.0)).toFloat()
        Log.d(TAG, "fmi ${fmi.top} ${fmi.bottom}")
        val index: Int = mCurrentSelect
        val textData: String = mDataList[index]
        canvas.drawText(textData, x, baseline, mPaint)
        Log.d(TAG, "centerHeight $centerHeight  baseLine $baseline y $y")
        //centerHeight 856.5  baseLine 935.5 y 852.0
        // 绘制上方data
        // 绘制上方data

        var i = 1
        while (mCurrentSelect - i >= 0) {
            drawOtherText(canvas, i, -1)
            i++
        }

        // 绘制下方data
        // 绘制下方data

        i = 1
        while (mCurrentSelect + i < mDataList.size) {
            drawOtherText(canvas, i, 1)
            i++
        }
    }

    private fun drawOtherText(canvas: Canvas, position: Int, type: Int) {
        val d = (MARGIN_ALPHA * mMinTextSize * position + type * mMoveLen)
        val scale = parabola(mViewHeight / 4.0f, d)
        val size = (mMaxTextSize - mMinTextSize) * scale + mMinTextSize
        mPaint.textSize = size
        mPaint.alpha = ((mMaxTextAlpha - mMinTextAlpha) * scale + mMinTextAlpha).toInt()
        val y = (mViewHeight / 2.0 + type * d).toFloat()
        val fmi = mPaint.fontMetricsInt
        val baseline = (y - (fmi.bottom / 2.0 + fmi.top / 2.0)).toFloat()
        val index: Int = mCurrentSelect + type * position
        val textData: String = mDataList[index]
        canvas.drawText(textData, (mViewWidth / 2.0).toFloat(), baseline, mPaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) return false
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> doDown(event)
            MotionEvent.ACTION_MOVE -> doMove(event)
            MotionEvent.ACTION_UP -> doUp(event)
        }
        return true
    }

    private fun doDown(event: MotionEvent) {
        mLastDownY = event.y
    }

    private fun doMove(event: MotionEvent) {
        //上滑-，下滑+
        val moveLen = event.y - mLastDownY
        if (mCurrentSelect == 0 && moveLen > 0) return
        if (mCurrentSelect == mDataList.size - 1 && moveLen < 0) return

        mMoveLen += moveLen
        Log.d(TAG, "$mMoveLen")
        if (mMoveLen >= MARGIN_ALPHA * mMinTextSize / 2) {
            // 往下滑超过离开距离
//            moveTailToHead();
            if (mCurrentSelect > 0) {
                mCurrentSelect--
                mMoveLen -= MARGIN_ALPHA * mMinTextSize;
            }

        } else if (mMoveLen <= -MARGIN_ALPHA * mMinTextSize / 2) {
            // 往上滑超过离开距离
//            moveHeadToTail();
            if (mCurrentSelect < mDataList.size - 1) {
                mCurrentSelect++
                mMoveLen += MARGIN_ALPHA * mMinTextSize;
            }

        }
        if (mCurrentSelect >= 0 && mCurrentSelect <= mDataList.size - 1) {
            mLastDownY = event.y
        }
        invalidate()
    }

    private fun doUp(event: MotionEvent) {
        if (Math.abs(mMoveLen) < 0.0001) {
            mMoveLen = 0f
            return
        }
        performSelect()
        mMoveLen = 0F
        invalidate()
    }

    inner class MyTimerTask : TimerTask() {
        override fun run() {
            mMoveLen -= 20f
            if (mMoveLen < 20f) {
                mMoveLen = 0f
            }
            invalidate()
        }

    }

    private fun parabola(zero: Float, x: Float): Float {
        val f = (1 - (x / zero).toDouble().pow(2.0)).toFloat()
        return if (f < 0f) 0f else f
    }
}