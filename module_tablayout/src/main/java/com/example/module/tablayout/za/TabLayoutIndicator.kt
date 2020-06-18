package com.example.module.tablayout.za


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.example.module.tablayout.R

/**
 * 自定义tabLayout指示器
 *
 */
class TabLayoutIndicator @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    /**
     * 指示器的宽度和颜色
     */
    private var mIndicatorWidth = 0f
    private var mIndicatorColor: Int = 0x9f6def

    private val mPaint = Paint()
    private var mPosition = 0
    private var mPositionOffset = 0f
    private var mTotalTabCount = 1
    private var mItemWith = 0
    private var mMarginLeft = 0f
    /**
     * 是否变换长度
     */
    private var mCanScanAnim = false
    /**
     * 是否和标题一样的长度
     */
    private var mMatchTitleWidth = false
    /**
     * 设置了drawable就回执drawable，否则就绘制线条
     */
    private var mIndicatorDrawable: Drawable? = null
    private var mBitmapPaint: Paint? = null
    private var mIndicatorBitmap: Bitmap? = null
    /**
     * 用集合将各个title的宽度搜集起来，不用每次去计算,节约性能
     */
    private var mItemWithArray: MutableList<Float>? = null
    /**
     * 自定义根据文字变化的宽度缩放系数
     */
    private var mMyScale = 0f

    var mViewPager: ViewPager? = null
        set(value) {
            field = value
            mTotalTabCount = field!!.adapter?.count ?: 1
            field!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    mPosition = position
                    mPositionOffset = positionOffset
                    invalidate()
                }

                override fun onPageSelected(position: Int) {

                }
            })
        }

    init {
        if (attrs != null) {
            val attrsArray = getContext().obtainStyledAttributes(attrs, R.styleable.TabLayoutIndicator)
            mIndicatorWidth = attrsArray.getDimension(R.styleable.TabLayoutIndicator_indicator_width, 20f)
            mIndicatorColor = attrsArray.getColor(R.styleable.TabLayoutIndicator_indicator_color, 0x9f6def)
            mIndicatorDrawable = attrsArray.getDrawable(R.styleable.TabLayoutIndicator_indicator_drawable)
            mCanScanAnim = attrsArray.getBoolean(R.styleable.TabLayoutIndicator_indicator_can_anim, false)
            mMatchTitleWidth = attrsArray.getBoolean(R.styleable.TabLayoutIndicator_indicator_match_title_width, false)
            mMyScale = attrsArray.getFloat(R.styleable.TabLayoutIndicator_indicator_scale, 0f)
            attrsArray.recycle()
        }

        mPaint.run {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            color = mIndicatorColor
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        mItemWith = width / mTotalTabCount
        mMarginLeft = mItemWith / 2 - mIndicatorWidth / 2
        mPaint.strokeWidth = height.toFloat()
        if (mIndicatorDrawable != null) {
            mIndicatorBitmap = Util.drawableToBitmap(mIndicatorDrawable!!, mIndicatorWidth.toInt(), measuredHeight)
            mBitmapPaint = Paint().apply {
                style = Paint.Style.FILL
                isAntiAlias = true
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        if (mMatchTitleWidth && mViewPager != null && mViewPager!!.adapter != null && mItemWithArray == null) {
            mItemWithArray = MutableList(mViewPager!!.adapter!!.count) {
                getTitleWidth(it)
            }
        }

        if (mCanScanAnim) {
            //indicator长度动态变换
            val startX: Float
            val endX: Float
            if (mPositionOffset <= 0.5) {
                if (mMatchTitleWidth) {
                    startX = mItemWith * mPosition + mItemWith / 2 - getItemTitleWidth(mPosition) / 2
                    //动态计算结束点
                    endX = startX + getItemTitleWidth(mPosition) +
                            (2 * mItemWith - getItemTitleWidth(mPosition) + getItemTitleWidth(mPosition + 1)) *
                            mPositionOffset
//                            (mItemWith / 2 - getTitltWidth(mPosition) / 2 + mItemWith /2 +getTitltWidth(mPosition + 1)/2) * 2 * mPositionOffset
                } else {
                    startX = mItemWith * mPosition + mMarginLeft
                    endX = startX + mIndicatorWidth + mItemWith * 2 * mPositionOffset
                }
            } else {
                if (mMatchTitleWidth) {
                    endX = mItemWith * (mPosition + 1) + mItemWith / 2 + getItemTitleWidth(mPosition + 1) / 2
                    //动态计算开始点
                    startX = mItemWith * mPosition + mItemWith / 2 - getItemTitleWidth(mPosition) / 2 +
                            (2 * mItemWith + getItemTitleWidth(mPosition) - getItemTitleWidth(mPosition + 1)) *
                            (mPositionOffset - 0.5f)
                } else {
                    endX = mItemWith * (mPosition + 1) + mMarginLeft + mIndicatorWidth
                    startX = mMarginLeft + mItemWith * mPosition + (mPositionOffset - 0.5f) * 2 * mItemWith
                }
            }
            if (mIndicatorBitmap != null) {
                mIndicatorBitmap = Util.drawableToBitmap(mIndicatorDrawable!!, (endX - startX).toInt(), measuredHeight)
                canvas?.drawBitmap(mIndicatorBitmap!!, startX, 0f, mBitmapPaint)
            } else {
                canvas?.drawLine(startX, height / 2.toFloat(), endX, height / 2.toFloat(), mPaint)
            }
        } else {
            //indicator长度固定
            val startX = mItemWith * mPosition + mMarginLeft + mItemWith * mPositionOffset
            if (mIndicatorBitmap != null) {
                canvas?.drawBitmap(mIndicatorBitmap!!, startX, 0f, mBitmapPaint)
            } else {
                canvas?.drawLine(startX, height / 2.toFloat(), startX + mIndicatorWidth, height / 2.toFloat(), mPaint)
            }
        }
    }

    /**
     * 获取每个title的宽度
     */
    private fun getItemTitleWidth(position: Int) =
            if (mItemWithArray != null && position < mItemWithArray!!.size) mItemWithArray!![position] else mIndicatorWidth


    /**
     * 获取相应位子title的长度
     */
    private fun getTitleWidth(position: Int): Float {
        var width = 0f
        if (position < mViewPager?.adapter?.count ?: 0 && mViewPager?.adapter?.getPageTitle(position) != null) {
            val string = mViewPager?.adapter?.getPageTitle(position).toString()
            val rect = Rect()
            mPaint.getTextBounds(string, 0, string.length, rect)
            width = rect.width().toFloat()
        }
        return if (mMyScale != 0f) mMyScale * width else dp2px(context, width)
    }

    /**
     * dp转换成px
     */
    private fun dp2px(context: Context, dpValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return dpValue * scale + 0.5f
    }

}


