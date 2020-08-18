package com.module.ui.widget.danmuku.richText

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan
import com.android.common.BaseApplication
import com.android.newcommon.utils.DisplayUtil
import com.module.ui.R


class TreasureImageSpan(context: Context, private var treasureValue: Int)
    : ImageSpan(ColorDrawable(Color.TRANSPARENT)) {

    private var textPaint: Paint = Paint()
    private val mWidth: Float
    private val mHeight: Float
    private var mLoadedDrawable: Drawable
    private val mTextStartX: Float
    private val mTextStartY: Float
    private val mMinIconSizeDp: Int


    init {
        if (treasureValue < 0) {
            treasureValue = 0
        } else if (treasureValue > 99) {
            treasureValue = 99
        }
        textPaint.color = 0xffffff
        textPaint.alpha = 255
        textPaint.isAntiAlias = true
        textPaint.textSize = DisplayUtil.dpToPx(BaseApplication.getInstance(), 11f).toFloat()
        textPaint.style = Paint.Style.FILL

        mWidth = DisplayUtil.dpToPx(BaseApplication.getInstance(), 46f).toFloat()
        mHeight = DisplayUtil.dpToPx(BaseApplication.getInstance(), 24f).toFloat()
        mTextStartX = DisplayUtil.dpToPx(BaseApplication.getInstance(), 26f).toFloat()
        mTextStartY = DisplayUtil.dpToPx(BaseApplication.getInstance(), 16f).toFloat()
        mMinIconSizeDp = DisplayUtil.dpToPx(BaseApplication.getInstance(), 14f)
        mLoadedDrawable = context.resources.getDrawable(getTreasureIconResource(treasureValue))
        mLoadedDrawable.setBounds(0, 0, mWidth.toInt(), mHeight.toInt())
    }

    override fun getDrawable(): Drawable {
        return mLoadedDrawable
    }

    fun getTreasureIconResource(treasureValue: Int): Int {
        return when (treasureValue) {
            in 0..19 -> R.drawable.icon_treasure_level_1
            in 20..39 -> R.drawable.icon_treasure_level_2
            in 40..59 -> R.drawable.icon_treasure_level_3
            in 60..79 -> R.drawable.icon_treasure_level_4
            else -> R.drawable.icon_treasure_level_5
        }
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
//        Log.e("CenterImageSpan", "x=$x,y=$y,top=$top,bottom=$bottom,ascentToTop=$ascentToTop,centerY=$centerY,transY=$transY")
        canvas.translate(x, transY)
        drawable.draw(canvas)

        canvas.drawText(treasureValue.toString(), mTextStartX, mTextStartY, textPaint)
        canvas.restore()
    }

}