package com.example.module.tablayout.za


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.example.module.tablayout.R

/**
 * 自定义tab导航title
 *
 * @date 2019/01/10
 */
class TabLayoutTitle @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        View(context, attrs, defStyleAttr) {

    companion object {
        const val ANIM_MODE_START = 0
        const val ANIM_MODE_MIDDLE = 1

        const val DEFAULT_COLOR = 0x9f6def

        const val DEFAULT_NORMAL_SIZE = 14
        const val DEFAULT_SELECT_SIZE = 20
        /**
         * 手指点击的默认位置为第一个
         */
        const val DEFAULT_TOUCH_POSITION = 0
        /**
         * 颜色改变的阈值
         */
        const val COLOR_CHANGE_THRESHOLD = 0.5f

    }

    /**
     * title的宽度、普通颜色、选中后的颜色
     */
    private var mTextNormalColor: Int = DEFAULT_COLOR
    private var mTextSelectColor: Int = DEFAULT_COLOR

    private val mPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private var mPosition = 0
    private var mPositionOffset = 0f
    private var mTotalTabCount = 1
    private var mItemWith = 0
    private var mPointWith = 0
    /**
     * 文字普通大小（px）
     * 文字选中大小（px）
     */
    private var mTextNormalSize = DEFAULT_NORMAL_SIZE
    private var mTextSelectSize = DEFAULT_SELECT_SIZE
    /**
     * 选中和普通文字大小的差值
     */
    private var mDiffSize = 0
    /**
     * 动画模式
     */
    private var mAnimMode = ANIM_MODE_START
    /**
     * 是否为粗体
     */
    private var mIsBoldText = false
    /**
     * 是否只有选中的为粗体
     */
    private var mOnlySelectBoldText = false
    /**
     * 标题
     */
    private val mTitles: MutableList<String> = ArrayList()
    /**
     * 手指点击之前的位置
     */
    private var mTouchBeforePosition = DEFAULT_TOUCH_POSITION
    /**
     * 手指点击的位置
     */
    private var mTouchPosition = DEFAULT_TOUCH_POSITION
    /**
     * 是否手动点击title
     */
    private var mIsTouched = false

    /**
     * 指示器的宽度和颜色
     */
    private var mIndicatorWidth = 0f
    private var mIndicatorHeight = 2f
    private var mIndicatorColor: Int = 0x9f6def
    private val mIndicatorPaint = Paint()
    private var mIndicatorMarginLeft = 0f
    /**
     * 设置了drawable就回执drawable，否则就绘制线条
     */
    private var mIndicatorDrawable: Drawable? = null
    private var mBitmapPaint: Paint? = null
    private var mIndicatorBitmap: Bitmap? = null
    /**
     * title变化的方式
     */
    var changeResourceListener: OnChangeResourceListener? = null

    var mViewPager: ViewPager? = null
        set(value) {
            field = value
            mTotalTabCount = field!!.adapter?.count ?: 1
            for (i in 0 until mTotalTabCount) {
                mTitles.add(field!!.adapter?.getPageTitle(i).toString())
            }

            field!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                    if (state == ViewPager.SCROLL_STATE_IDLE) {
                        //ViewPager停止了滑动
                        if (mIsTouched) {
                            changeResourceListener?.clickResource(OnChangeResourceListener.CHANGE_CLICK, mViewPager?.currentItem ?: DEFAULT_TOUCH_POSITION)
                        } else {
                            changeResourceListener?.clickResource(OnChangeResourceListener.CHANGE_VIEW_PAGER, mViewPager?.currentItem ?: DEFAULT_TOUCH_POSITION)
                        }
                        mIsTouched = false
                    }
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//                    Log.e("-------", "-------position=$position-----positionOffset=$positionOffset--------")
                    mPosition = position
                    mPositionOffset = when {
                        positionOffset > 0.99 -> 1f
                        positionOffset < 0.01 -> 0f
                        else -> positionOffset
                    }
                    invalidate()
                }

                override fun onPageSelected(position: Int) {
                }
            })
        }

    init {
        if (attrs != null) {
            val attrsArray = getContext().obtainStyledAttributes(attrs, R.styleable.TabLayoutTitle)
            //-----------------title--------------
            mTextNormalColor = attrsArray.getColor(R.styleable.TabLayoutTitle_title_normal_color, DEFAULT_COLOR)
            mTextSelectColor = attrsArray.getColor(R.styleable.TabLayoutTitle_title_select_color, DEFAULT_COLOR)
            mTextNormalSize = attrsArray.getInt(R.styleable.TabLayoutTitle_title_text_normal_size, DEFAULT_NORMAL_SIZE)
            mTextSelectSize = attrsArray.getInt(R.styleable.TabLayoutTitle_title_text_select_size, DEFAULT_NORMAL_SIZE)
            mIsBoldText = attrsArray.getBoolean(R.styleable.TabLayoutTitle_title_is_Bold, false)
            mOnlySelectBoldText = attrsArray.getBoolean(R.styleable.TabLayoutTitle_title_only_select_Bold, false)
            mAnimMode = attrsArray.getInt(R.styleable.TabLayoutTitle_title_anim_mode, ANIM_MODE_MIDDLE)
            //-----------------indicator-------------
            mIndicatorWidth = attrsArray.getDimension(R.styleable.TabLayoutTitle_title_indicator_width, 20f)
            mIndicatorHeight = attrsArray.getDimension(R.styleable.TabLayoutTitle_title_indicator_height, 2f)
            mIndicatorColor = attrsArray.getColor(R.styleable.TabLayoutTitle_title_indicator_color, 0x9f6def)
            mIndicatorDrawable = attrsArray.getDrawable(R.styleable.TabLayoutTitle_title_indicator_drawable)
            attrsArray.recycle()
        }

        //设置文字paint
        mPaint.run {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = mTextNormalColor
            textSize = dp2px(mTextNormalSize)
            isFakeBoldText = mIsBoldText
            //居中对齐
            textAlign = Paint.Align.CENTER
        }


        mTextNormalSize = dp2px(mTextNormalSize).toInt()
        mTextSelectSize = dp2px(mTextSelectSize).toInt()
        mDiffSize = mTextSelectSize - mTextNormalSize

        //---------indicator-----------------
        mIndicatorPaint.run {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeWidth = mIndicatorHeight
            color = mIndicatorColor
        }

    }

    private fun initItemWidth() {
        if (mItemWith == 0 && mTitles.isNullOrEmpty().not()) {
            //说明还没有进行初始化
            mItemWith = width / mTitles.size
            mPointWith = width / (mTitles.size + 1)

            mIndicatorMarginLeft = mPointWith - mIndicatorWidth / 2

            if (mIndicatorDrawable != null) {
                mIndicatorBitmap =
                        Util.drawableToBitmap(mIndicatorDrawable!!, mIndicatorWidth.toInt(), mIndicatorHeight.toInt())
                mBitmapPaint = Paint().apply {
                    style = Paint.Style.FILL
                    isAntiAlias = true
                }
            }

        }
    }

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        initItemWidth()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.run {
            if (mIsTouched) {
                //手动点击
                if (mAnimMode == ANIM_MODE_START) {

                    var startX: Float
                    //波浪起伏模式  ANIM_MODE_START
                    mTitles.forEachIndexed { index, title ->
                        mPaint.run {
                            textSize = when (index) {
                                mPosition -> {
                                    startX = (2 * mPointWith - mPointWith * mPositionOffset) / 2 + mPointWith *
                                            mPosition
                                    color =
                                            if (mPositionOffset < COLOR_CHANGE_THRESHOLD) mTextSelectColor else mTextNormalColor
                                    if (mOnlySelectBoldText) {
                                        isFakeBoldText = mPositionOffset < COLOR_CHANGE_THRESHOLD
                                    }
                                    mTextSelectSize - mDiffSize * mPositionOffset
                                }
                                mPosition + 1 -> {
                                    startX = mPointWith * 5 / 2 - mPointWith / 2 * mPositionOffset + mPointWith *
                                            mPosition
                                    color =
                                            if (mPositionOffset < COLOR_CHANGE_THRESHOLD) mTextNormalColor else mTextSelectColor
                                    if (mOnlySelectBoldText) {
                                        isFakeBoldText = mPositionOffset > COLOR_CHANGE_THRESHOLD
                                    }
                                    mTextNormalSize + mDiffSize * mPositionOffset
                                }
                                else -> {
                                    val index1 = if (index < mPosition) index else index + 1
                                    startX = mPointWith / 2.toFloat() + mPointWith * index1
                                    color = mTextNormalColor
                                    if (mOnlySelectBoldText) {
                                        isFakeBoldText = false
                                    }
                                    mTextNormalSize.toFloat()
                                }
                            }
                        }
                        drawText(
                                title,
                                startX,
                                height / 2 + mPaint.fontMetrics.bottom,
                                mPaint
                        )
                    }


                    //---------------draw Indicator-------------
                    val indicatorStartX = mPointWith * mPosition + mIndicatorMarginLeft + mPointWith * mPositionOffset
                    if (mIndicatorBitmap != null) {
                        drawBitmap(mIndicatorBitmap!!, indicatorStartX, height - mIndicatorHeight, mBitmapPaint)
                    } else {
                        drawLine(
                                indicatorStartX,
                                height - mIndicatorHeight / 2,
                                indicatorStartX + mIndicatorWidth,
                                height - mIndicatorHeight / 2,
                                mIndicatorPaint
                        )
                    }
                } else {
                    mTitles.forEachIndexed { index, title ->
                        mPaint.run {
                            textSize = when (index) {
                                mTouchBeforePosition -> {
                                    color = mTextNormalColor
                                    if (mOnlySelectBoldText) {
                                        isFakeBoldText = false
                                    }
                                    when {
                                        Math.abs(mPosition - mTouchPosition) > 1 -> mTextSelectSize.toFloat()
                                        mPosition == mTouchPosition -> mTextNormalSize.toFloat()
                                        else -> {
                                            if (mTouchPosition > mTouchBeforePosition) {
                                                //向右
                                                mTextSelectSize - mDiffSize * mPositionOffset
                                            } else {
                                                //向左
                                                mTextNormalSize + mDiffSize * mPositionOffset
                                            }
                                        }
                                    }
                                }
                                mTouchPosition -> {
                                    color = mTextSelectColor
                                    if (mOnlySelectBoldText) {
                                        isFakeBoldText = true
                                    }
                                    when {
                                        Math.abs(mPosition - mTouchPosition) > 1 -> mTextNormalSize.toFloat()
                                        mPosition == mTouchPosition -> mTextSelectSize.toFloat()
                                        else -> {
                                            if (mTouchPosition > mTouchBeforePosition) {
                                                //向右
                                                mTextNormalSize + mDiffSize * mPositionOffset
                                            } else {
                                                //向左
                                                mTextSelectSize - mDiffSize * mPositionOffset
                                            }
                                        }
                                    }
                                }
                                else -> {
                                    color = mTextNormalColor
                                    if (mOnlySelectBoldText) {
                                        isFakeBoldText = false
                                    }
                                    mTextNormalSize.toFloat()
                                }
                            }
                        }
                        drawText(
                                title,
                                mItemWith * index + mItemWith / 2.toFloat(),
                                height / 2 + mPaint.fontMetrics.bottom,
                                mPaint
                        )
                    }
                }
            } else {
                //自动滑动
                if (mAnimMode == ANIM_MODE_START) {
                    var startX: Float
                    //波浪起伏模式  ANIM_MODE_START
                    mTitles.forEachIndexed { index, title ->
                        mPaint.run {
                            textSize = when (index) {
                                mPosition -> {
                                    startX = (2 * mPointWith - mPointWith * mPositionOffset) / 2 + mPointWith *
                                            mPosition
                                    color =
                                            if (mPositionOffset < COLOR_CHANGE_THRESHOLD) mTextSelectColor else mTextNormalColor
                                    if (mOnlySelectBoldText) {
                                        isFakeBoldText = mPositionOffset < COLOR_CHANGE_THRESHOLD
                                    }
                                    mTextSelectSize - mDiffSize * mPositionOffset
                                }
                                mPosition + 1 -> {
                                    startX = mPointWith * 5 / 2 - mPointWith / 2 * mPositionOffset + mPointWith *
                                            mPosition
                                    color =
                                            if (mPositionOffset < COLOR_CHANGE_THRESHOLD) mTextNormalColor else mTextSelectColor
                                    if (mOnlySelectBoldText) {
                                        isFakeBoldText = mPositionOffset > COLOR_CHANGE_THRESHOLD
                                    }
                                    mTextNormalSize + mDiffSize * mPositionOffset
                                }
                                else -> {
                                    val index1 = if (index < mPosition) index else index + 1
                                    startX = mPointWith / 2.toFloat() + mPointWith * index1
                                    color = mTextNormalColor
                                    if (mOnlySelectBoldText) {
                                        isFakeBoldText = false
                                    }
                                    mTextNormalSize.toFloat()
                                }
                            }
                        }
                        drawText(
                                title,
                                startX,
                                height / 2 + mPaint.fontMetrics.bottom,
                                mPaint
                        )
                    }

                    //---------------draw Indicator-------------
                    val indicatorStartX = mPointWith * mPosition + mIndicatorMarginLeft + mPointWith * mPositionOffset
                    if (mIndicatorBitmap != null) {
                        drawBitmap(mIndicatorBitmap!!, indicatorStartX, height - mIndicatorHeight, mBitmapPaint)
                    } else {
                        drawLine(
                                indicatorStartX,
                                height - mIndicatorHeight / 2,
                                indicatorStartX + mIndicatorWidth,
                                height - mIndicatorHeight / 2,
                                mIndicatorPaint
                        )
                    }

                } else {
                    //居中放大模式  ANIM_MODE_MIDDLE
                    mTitles.forEachIndexed { index, title ->
                        mPaint.run {
                            textSize = when (index) {
                                mPosition -> {
                                    color =
                                            if (mPositionOffset < COLOR_CHANGE_THRESHOLD) mTextSelectColor else mTextNormalColor
                                    if (mOnlySelectBoldText) {
                                        isFakeBoldText = mPositionOffset < COLOR_CHANGE_THRESHOLD
                                    }
                                    mTextSelectSize - mDiffSize * mPositionOffset
                                }
                                mPosition + 1 -> {
                                    color =
                                            if (mPositionOffset < COLOR_CHANGE_THRESHOLD) mTextNormalColor else mTextSelectColor
                                    if (mOnlySelectBoldText) {
                                        isFakeBoldText = mPositionOffset > COLOR_CHANGE_THRESHOLD
                                    }
                                    mTextNormalSize + mDiffSize * mPositionOffset
                                }
                                else -> {
                                    color = mTextNormalColor
                                    if (mOnlySelectBoldText) {
                                        isFakeBoldText = false
                                    }
                                    mTextNormalSize.toFloat()
                                }
                            }
                        }
                        drawText(
                                title,
                                mItemWith * index + mItemWith / 2.toFloat(),
                                height / 2 + mPaint.fontMetrics.bottom,
                                mPaint
                        )
                    }
                }
            }
        }
    }

    /**
     * dp转换成px
     */
    private fun dp2px(dpValue: Int): Float {
        val scale = context.resources.displayMetrics.density
        return dpValue * scale + 0.5f
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mTouchBeforePosition = mViewPager?.currentItem ?: DEFAULT_TOUCH_POSITION
                // 根据手指点击的范围，确定当前点击的第几个title
                mTouchPosition = if (mAnimMode == ANIM_MODE_START) {
                    when {
                        (mTouchBeforePosition + 2) * mPointWith < event.x -> (event.x / mPointWith).toInt() - 1
                        (mTouchBeforePosition + 2) * mPointWith > event.x -> (event.x / mPointWith).toInt()
                        else -> mTouchBeforePosition
                    }
                } else {
                    (event.x / mItemWith).toInt()
                }

                mIsTouched = true
                mViewPager?.currentItem = mTouchPosition
            }
            else -> {
            }
        }
        return true
    }

}


