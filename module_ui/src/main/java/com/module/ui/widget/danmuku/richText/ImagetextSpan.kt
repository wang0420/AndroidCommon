package com.module.ui.widget.danmuku.richText

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan
import com.android.newcommon.utils.DisplayUtil
import com.module.ui.R
/**
 * 自定义背景的图文Span
 * */
class ImagetextSpan(context: Context, private var msg: String, private var mWidth: Float)
    : ImageSpan(ColorDrawable(Color.TRANSPARENT)) {

    private var textPaint: Paint = Paint()
    private val mHeight: Float
    private var mLoadedDrawable: Drawable
    private val mTextStartX: Float
    private val mTextStartY: Float
    private val mMinIconSizeDp: Int


    init {
        textPaint.color = 0xffffff
        textPaint.alpha = 255
        textPaint.isAntiAlias = true
        textPaint.textSize = DisplayUtil.dpToPx(context, 11f).toFloat()
        textPaint.style = Paint.Style.FILL

        mWidth = DisplayUtil.dpToPx(context, mWidth).toFloat()
        mHeight = DisplayUtil.dpToPx(context, 19f).toFloat()
        mTextStartX = DisplayUtil.dpToPx(context, 6f).toFloat()
        mTextStartY = DisplayUtil.dpToPx(context, 13f).toFloat()
        mMinIconSizeDp = DisplayUtil.dpToPx(context, 14f)
        mLoadedDrawable = context.resources.getDrawable(R.drawable.shape_round_red)
        mLoadedDrawable.setBounds(0, 0, mWidth.toInt(), mHeight.toInt())
    }

    override fun getDrawable(): Drawable {
        return mLoadedDrawable
    }

    /**
     * 覆盖此方法，使文本基线对齐时对齐到图片中央
     */
    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int,
                         fmi: FontMetricsInt?): Int {
        val drawable = drawable
        val rect = drawable.bounds
        if (fmi != null) {
            val fmPaint = paint.fontMetricsInt
            val fontHeight = fmPaint.bottom - fmPaint.top
            // 以整行TextView的内容中最小的图标尺寸为准
            val drHeight = mMinIconSizeDp.coerceAtMost(rect.bottom - rect.top)
            val top = drHeight / 2 - fontHeight / 4
            val bottom = drHeight / 2 + fontHeight / 4
            fmi.ascent = -bottom
            fmi.top = -bottom
            fmi.bottom = top
            fmi.descent = top
        }
        return rect.right
    }

    override fun draw(canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val drawable = drawable
        canvas.save()

        val fmi = paint.fontMetricsInt
        val ascentToTop = y - top - Math.abs(fmi.ascent)
        val centerY = ascentToTop + (fmi.descent - fmi.ascent) * 0.5f
        val transY = top.toFloat() + centerY - drawable.bounds.height() * 0.5f
        canvas.translate(x, transY)
        drawable.draw(canvas)

        canvas.drawText(msg, mTextStartX, mTextStartY, textPaint)
        canvas.restore()

    }

}