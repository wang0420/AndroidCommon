package com.module.ui.widget.danmuku.richText

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan
import android.view.View
import androidx.core.content.ContextCompat
import com.android.common.BaseApplication
import com.android.newcommon.utils.DisplayUtil

import java.lang.ref.WeakReference

/**
 * 垂直居中的ImageSpan
 *
 * @date 2019/04/01
 */
class CenterImageSpan2 private constructor(d: Drawable) : ImageSpan(d) {
    companion object {
        @JvmStatic
        fun fromRes(context: Context, resId: Int): CenterImageSpan2 {
            val d = ContextCompat.getDrawable(context, resId)
                    ?: BitmapDrawable(context.resources, BitmapFactory.decodeResource(context.resources, resId))
            d.setBounds(0, 0, d.intrinsicWidth, d.intrinsicHeight)
            return CenterImageSpan2(d)
        }

        @JvmStatic
        fun fromUrl(url: String,
                    defaultWidthDp: Float,
                    defaultHeightDp: Float,
                    minSizeDp: Float): CenterImageSpan2 {
            val context = BaseApplication.getInstance()
            return fromUrlPx(url,
                    DisplayUtil.dpToPx(context, defaultWidthDp),
                    DisplayUtil.dpToPx(context, defaultHeightDp),
                    DisplayUtil.dpToPx(context, minSizeDp))
        }

        @JvmStatic
        fun fromUrlPx(url: String,
                    defaultWidth: Int,
                    defaultHeight: Int,
                    minSize: Int): CenterImageSpan2 {
            val placeholderDrawable = ColorDrawable(Color.TRANSPARENT)
            placeholderDrawable.setBounds(0, 0, defaultWidth, defaultHeight)
            return CenterImageSpan2(placeholderDrawable).apply {
                mUrl = url
                mDefaultWidth = defaultWidth
                mDefaultHeight = defaultHeight
                mMinSize = minSize
            }
        }
    }

    private var mUrl: String? = null
    private var mDefaultWidth: Int = 0
    private var mDefaultHeight: Int = 0
    private var mMinSize: Int = 0
    var msgType: Int = 0
    var data: Any? = null

    private var mLoadedDrawable: Drawable? = null
    private var mViewRef: WeakReference<View>? = null

    fun setView(view: View) {
        mViewRef = WeakReference(view)
    }

    override fun getDrawable(): Drawable {
        if (mUrl.isNullOrEmpty()) {
            return super.getDrawable()
        }

        val loaded = mLoadedDrawable
        if (loaded != null) {
            return loaded
        }
/*        ZAImageLoader.get()
                .with(BaseApplication.getContext())
                .load(mUrl)
                .override(mDefaultWidth, mDefaultHeight)
                .into(object : SimpleBitmapTarget() {
                    override fun onResourceReady(bitmap: Bitmap?) {
                        if (bitmap == null) return
                        val result: Bitmap?
                        if (bitmap.width > mDefaultWidth || bitmap.height >= mDefaultHeight) {
                            result = BitmapUtils.resize(bitmap, mDefaultWidth, mDefaultHeight)
                            BitmapUtils.recycle(bitmap)
                        } else {
                            result = bitmap
                        }

                        mLoadedDrawable = BitmapDrawable(BaseApplication.getContext().resources, result)
                        mLoadedDrawable?.setBounds(0, 0, mDefaultWidth, mDefaultHeight)

                        try {
                            // 反射更改mDrawable属性
                            val mDrawable = ImageSpan::class.java.getDeclaredField("mDrawable")
                            mDrawable.isAccessible = true
                            mDrawable.set(this@CenterImageSpan2, mLoadedDrawable)

                            // 反射更改mDrawableRef属性
                            val mDrawableRef = DynamicDrawableSpan::class.java.getDeclaredField(
                                    "mDrawableRef")
                            mDrawableRef.isAccessible = true
                            mDrawableRef.set(this@CenterImageSpan2, null)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        // 通知View重绘一下
                        mViewRef?.get()?.invalidate()
                    }
                })*/
        return super.getDrawable()
    }

    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int,
                         fmi: Paint.FontMetricsInt?): Int {
        return drawable.bounds.width()
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
        canvas.restore()
    }
}