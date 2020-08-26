package com.module.ui.widget.danmuku.richText

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.CharacterStyle
import android.view.View
import java.util.*

/**
 * 富文本
 *
 * @date 2019/04/01
 */
class SpannedText(private val template: String) {
    private var mCursorPosition: Int = 0// 当前光标位置
    private var mImageSpans: LinkedList<CenterImageSpan2>? = null// 图片span

    private val mBuilder by lazy {
        SpannableStringBuilder(template)
    }

    fun getCursor(): Int {
        return mCursorPosition
    }

    /**
     * 设置光标位置
     */
    fun setCursor(position: Int): SpannedText {
        mCursorPosition = when {
            position < 0 -> 0
            position > mBuilder.length -> mBuilder.length
            else -> position
        }
        return this
    }

    /**
     * 移动光标位置
     *
     * @author shift 光标移动的字符数，负数向左，正数向右
     */
    fun moveCursor(shift: Int): SpannedText {
        return setCursor(mCursorPosition + shift)
    }

    fun indexOf(what: String): Int {
        return mBuilder.indexOf(what, mCursorPosition)
    }

    /**
     * 使光标前进到下一个非空格字符，如果没有空格字符，则相当于没移动
     */
    fun forwardToNotBlank(): SpannedText {
        var i = mCursorPosition + 1
        while (i < mBuilder.length) {
            if (mBuilder[i] != ' ') {
                return setCursor(i)
            }
            ++i
        }
        return this
    }

    /**
     * 给从当前光标开始、长度为length的字符设置一个span
     */
    fun setSpan(length: Int, span: CharacterStyle): SpannedText {
        val where = mCursorPosition
        val end = if (Int.MAX_VALUE - mCursorPosition < length) {
            mBuilder.length
        } else {
            Math.min(mBuilder.length, where + length)
        }
        setSpanInternal(span, where, end)
        mCursorPosition = end
        return this
    }

    /**
     * 从当前光标开始往后寻找，给placeholder字符设置一个span
     */
    fun setSpan(placeholder: String,
                span: CharacterStyle): SpannedText {
        val where = mBuilder.indexOf(placeholder, mCursorPosition)
        if (where >= 0) {
            setCursor(where)
            return setSpan(placeholder.length, span)
        }
        return this
    }

    /**
     * 从当前光标开始往后寻找，把placeholder替换为replacement，并设置一个span
     */
    fun replace(placeholder: String,
                replacement: String?,
                span: CharacterStyle?): SpannedText {
        val where = mBuilder.indexOf(placeholder, mCursorPosition)
        if (where >= 0) {
            val rep = replacement ?: ""
            var end = where + placeholder.length
            mBuilder.replace(where, end, rep)
            end = where + rep.length
            if (span != null) {
                setSpanInternal(span, where, end)
            }
            mCursorPosition = end
        }
        return this
    }

    /**
     * 从当前光标，插入一段字符，并给该段字符设置一个span
     */
    fun insert(insert: String,
               span: CharacterStyle?): SpannedText {
        val where = mCursorPosition
        val end = where + insert.length
        mBuilder.insert(where, insert)
        if (span != null) {
            setSpanInternal(span, where, end)
        }
        mCursorPosition = end
        return this
    }

    /**
     * 返回SpannableStringBuilder对象
     */
    fun get(): SpannableStringBuilder {
        return mBuilder
    }

    fun setView(view: View) {
        mImageSpans?.forEach {
            it.setView(view)
        }
    }

    private fun setSpanInternal(span: CharacterStyle, start: Int, end: Int) {
        mBuilder.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        if (span is CenterImageSpan2) {
            if (mImageSpans == null) {
                mImageSpans = LinkedList()
            }
            mImageSpans?.add(span)
        }
    }
}