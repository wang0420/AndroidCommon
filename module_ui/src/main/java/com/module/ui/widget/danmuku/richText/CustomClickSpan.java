package com.module.ui.widget.danmuku.richText;

import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * 点击Span
 * @date 2018/01/18
 */

public class CustomClickSpan extends ClickableSpan {
    private int type;
    private Object data;

    private int mTextColor;
    private boolean mUnderline;
    private Typeface mTypeface;

    public static final int TYPE_NICKNAME = 1;// 昵称，点击打开三资面板
    public static final int TYPE_LINK = 2;// 超链接（如红娘小助手），点击跳BaseHtmlActivity
    public static final int TYPE_RED_ENVELOPE = 3;// 红包，点击打开红包
    public static final int TYPE_INTERACTIVE = 4;// 互动游戏，点击打开游戏面板
    public static final int TYPE_AT_NICKNAME = 5;// @功能昵称，点击打开三资面板
    public static final int TYPE_RANK_CHECK= 6;// 日榜变化，点击打开日榜

    public CustomClickSpan(int type, Object data, int textColor, boolean underline) {
        this.type = type;
        this.data = data;
        this.mTextColor = textColor;
        this.mUnderline = underline;
    }

    public int getType() {
        return type;
    }

    public Object getData() {
        return data;
    }

    public void setTypeface(Typeface typeface) {
        this.mTypeface = typeface;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(mTextColor);
        ds.setUnderlineText(mUnderline);// 不需要下划线
        if (mTypeface != null) {
            ds.setTypeface(mTypeface);
        }
    }

    @Override
    public void onClick(View widget) {

    }
}
