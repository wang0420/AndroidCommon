package com.android.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.android.common.R;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;


/**
 * Dialog 基类
 */

public abstract class BaseBottomDialog extends Dialog {


    private BottomDialogListener bottomDialogListener;
    protected Context mContext;

    public BaseBottomDialog(@NonNull Context context) {
        super(context, R.style.CommonDialog_Fullscreen_Transparent);
        mContext = context;
    }

    public BaseBottomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (bottomDialogListener != null) {
            bottomDialogListener.onBottomDialogDismiss();
        }
    }

    public BaseBottomDialog setBottomDialogListener(BottomDialogListener bottomDialogListener) {
        this.bottomDialogListener = bottomDialogListener;
        return BaseBottomDialog.this;
    }

    @Override
    public void show() {
        super.show();
        if (bottomDialogListener != null) {
            bottomDialogListener.onBottomDialogShow();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        init();
        findViews();
        initViewData();
        bindListener();
        Window window = getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setWindowAnimations(R.style.CommonDialog_Fullscreen_Transparent_BottomDialog); //设置窗口弹出动画
        }

        if (isResponseClickOutside()) {
            setOutSideEventResponse();
        } else {
            setCanceledOnTouchOutside(true);
        }
    }

    private void setOutSideEventResponse() {
        Window window = getWindow();
        if (window != null) {
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
    }

    public abstract void init();

    public abstract void findViews();

    public abstract void initViewData();

    public abstract void bindListener();

    public abstract int getLayoutResId();

    protected <T extends View> T find(int id) {
        return (T) findViewById(id);
    }


    /**
     * 当用户点击dialog外部的控件时，是否响应事件
     *
     * @return
     */
    protected boolean isResponseClickOutside() {
        return false;
    }

    public interface BottomDialogListener {
        void onBottomDialogDismiss();

        void onBottomDialogShow();
    }

}
