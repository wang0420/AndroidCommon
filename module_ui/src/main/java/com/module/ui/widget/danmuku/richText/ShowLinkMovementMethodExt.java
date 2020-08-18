package com.module.ui.widget.danmuku.richText;

import android.text.Spannable;


/**
 * 用于TextView中各个Span的点击监听，用于辅助Span点击
 */
public class ShowLinkMovementMethodExt extends BaseLinkMovementMethod {
    public ShowLinkMovementMethodExt(OnDanmakuClickListener onClickListener) {
        super(onClickListener);
    }

    @Override
    protected boolean onTouchUpEvent(Spannable spannable, int offset) {
        if (handleClickLinkSpan(spannable, offset)) return true;// 点击了LinkSpan
        if (handleClickNicknameSpan(spannable, offset)) return true;// 点击了NicknameSpan
        return false;
    }
}
