package com.android.newcommon.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

/**
 * 可监听尺寸变化的相对布局，可监听键盘弹起
 *
 * @author yintaibing
 * @date 2017/6/19
 */

public class SizeObservableFrameLayout extends FrameLayout {
    private OnSizeChangedListener mOnSizeChangedListener;

    public SizeObservableFrameLayout(Context context) {
        this(context, null, 0);
    }

    public SizeObservableFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SizeObservableFrameLayout(Context context, AttributeSet attributeSet, int style) {
        super(context, attributeSet, style);
    }
    private boolean mKeyboardShowing = false;// 键盘状态
    private int mOriginHeight  = 0;// 刚刚inflate出来，还未被键盘压缩时的高度

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        if (mOnSizeChangedListener != null) {
            mOnSizeChangedListener.onSizeChanged(w, h, oldW, oldH);
        }

        Log.w("TAG", "w--" + w);
        Log.w("TAG", "h--" + h);
        Log.w("TAG", "oldW--" + oldW);
        Log.w("TAG", "oldH--" + oldH);
        if (oldH == 0) {
            // 键盘还未弹起时，最大高度
            mOriginHeight = h;
            return;
        }
        if (h == mOriginHeight && mKeyboardShowing) {
            // 键盘收起
            Log.w("TAG", "键盘收起--");

            mKeyboardShowing = false;
           // mOnSizeChangedListener.onKeyboardChanged(mKeyboardShowing);
            return;
        }
        if (mKeyboardShowing) {
            // 键盘变化，但不是收起
            Log.w("TAG", "键盘变化--");

        } else {
            // 键盘弹起
            Log.w("TAG", "键盘弹起--");

            mKeyboardShowing = true;
           // mOnSizeChangedListener.onKeyboardChanged(mKeyboardShowing);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mOnSizeChangedListener = null;
    }

    public void setOnSizeChangedListener(OnSizeChangedListener listener) {
        mOnSizeChangedListener = listener;
    }

    public interface OnSizeChangedListener {
        void onSizeChanged(int w, int h, int oldW, int oldH);
        void onKeyboardChanged(boolean mKeyboardShowing);

    }
}
