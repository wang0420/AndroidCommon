package com.module.ui.widget.danmuku.richText;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.CharacterStyle;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

import com.android.common.BaseApplication;


/**
 * 用于TextView中各个Span的点击监听，用于辅助GiftImageSpan点击
 *
 * @date 2017/5/16
 */
public abstract class BaseLinkMovementMethod extends LinkMovementMethod {
    private int mTouchSlop;
    protected OnDanmakuClickListener mOnDanmakuClickListener;

    public BaseLinkMovementMethod(OnDanmakuClickListener onClickListener) {
        mOnDanmakuClickListener = onClickListener;
        mTouchSlop = ViewConfiguration.get(BaseApplication.getInstance()).getScaledTouchSlop();
    }

    public void destroy() {
        mOnDanmakuClickListener = null;
    }

    private int mDownX, mDownY;
    private long mLastClickTime = 0L;

    protected boolean checkRepeatClick() {
        long now = System.currentTimeMillis();
        if (now - mLastClickTime >= 1000L) {
            mLastClickTime = now;
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(TextView widget, Spannable spannable, MotionEvent event) {
        if (mOnDanmakuClickListener == null) {
            return super.onTouchEvent(widget, spannable, event);
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) event.getX();
                mDownY = (int) event.getY();
                break;

            case MotionEvent.ACTION_UP:
                int mUpX = (int) event.getX();
                int mUpY = (int) event.getY();
                if (Math.abs(mUpX - mDownX) <= mTouchSlop && Math.abs(mUpY - mDownY) <= mTouchSlop) {
                    mUpX -= widget.getTotalPaddingLeft();
                    mUpY -= widget.getTotalPaddingTop();
                    mUpX += widget.getScrollX();
                    mUpY += widget.getScrollY();

                    // 获取点击区域内的所有span
                    Layout layout = widget.getLayout();
                    int line = layout.getLineForVertical(mUpY);
                    int offset = layout.getOffsetForHorizontal(line, mUpX);

                    if (onTouchUpEvent(spannable, offset)) {
                        return true;
                    }
                }

            default:
        }
        return super.onTouchEvent(widget, spannable, event);
    }

    // ACTION_UP事件对span的点击处理
    protected abstract boolean onTouchUpEvent(Spannable spannable, int offset);

    // 点击了LinkSpan
    protected boolean handleClickLinkSpan(Spannable spannable, int offset) {
        CharacterStyle span = getClickedSpan(spannable, offset, LinkSpan.class);
        if (span != null && checkRepeatClick()) {
            LinkSpan linkSpan = (LinkSpan) span;
            mOnDanmakuClickListener.onLinkClick(
                    linkSpan.getPage(),
                    linkSpan.getRoomActionType(),
                    linkSpan.getUrl(),
                    linkSpan.getTitle(),
                    linkSpan.getMsgType());
            return true;
        }
        return false;
    }

    // 点击了NicknameSpan
    protected boolean handleClickNicknameSpan(Spannable spannable, int offset) {
        CharacterStyle span = getClickedSpan(spannable, offset, NicknameSpan.class);
        if (span != null && checkRepeatClick()) {
            NicknameSpan nicknameSpan = (NicknameSpan) span;
            mOnDanmakuClickListener.onNicknameClick(
                    nicknameSpan.getType(),
                    nicknameSpan.getUserId(),
                    nicknameSpan.getGender());
            return true;
        }
        return false;
    }

    // 包含CustomClickSpan
    protected boolean handleContainsCustomSpanClick(Spannable spannable, int offset) {
        CharacterStyle span = containsSpan(spannable, CustomClickSpan.class, false);
        if (span != null && checkRepeatClick()) {
            CustomClickSpan clickSpan = (CustomClickSpan) span;
            mOnDanmakuClickListener.onCustomClick(
                    clickSpan.getType(),
                    clickSpan.getData());
            return true;
        }
        return false;
    }

    /**
     * 获取点击区域内指定类型的span，如果点击了多个，返回第0个
     *
     * @param spannable spannable对象
     * @param offset    点击区域偏移
     * @param spanClass span类型
     * @return 被点击的指定类型的span对象
     */
    protected CharacterStyle getClickedSpan(Spannable spannable, int offset,
                                            Class<? extends CharacterStyle> spanClass) {
        CharacterStyle[] spans = spannable.getSpans(offset, offset, spanClass);
        if (spans != null && spans.length > 0) {
            CharacterStyle first = spans[0];
            Selection.setSelection(spannable, spannable.getSpanStart(first),
                    spannable.getSpanEnd(first));
            return first;
        }
        return null;
    }

    /**
     * 判断整个spannable是否包含指定类型的span，如果有，返回第0个
     *
     * @param spannable         spannable对象
     * @param spanClass         span类型
     * @param subclassInclusive 该span类型的子类是否也算在内
     * @return 被点击的指定类型的span对象
     */
    protected CharacterStyle containsSpan(Spannable spannable,
                                          Class<? extends CharacterStyle> spanClass,
                                          boolean subclassInclusive) {
        CharacterStyle[] spans = spannable.getSpans(0, spannable.length(), spanClass);
        if (spans != null && spans.length > 0) {
            if (subclassInclusive) {
                return spans[0];
            } else {
                for (CharacterStyle span : spans) {
                    if (span.getClass() == spanClass) {
                        return span;
                    }
                }
            }
        }
        return null;
    }
}
