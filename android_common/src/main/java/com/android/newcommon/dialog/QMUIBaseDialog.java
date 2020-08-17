package com.android.newcommon.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.common.R;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

/**
 * @author wangwei
 * @date 2020/8/14.
 */
public abstract class QMUIBaseDialog extends Dialog {
    private View mContentView;
    protected Context mContext;

    private DialogListener dialogListener;

    public abstract int getLayoutId();

    public abstract void initViewData();


    public QMUIBaseDialog(@NonNull Context context) {
        this(context, R.style.CommonDialog_Fullscreen);
    }

    public QMUIBaseDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext = context;
        initView();
        initViewData();
    }


    protected void initView() {
        mContentView = getLayoutInflater().inflate(getLayoutId(), null, false);
        this.setContentView(mContentView);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);
        //获取当前Activity所在的窗体
        Window window = this.getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);
            WindowManager.LayoutParams lp = window.getAttributes();//获得窗体的属性
            lp.width = overrideWidth(); //设置宽度
            lp.height = overrideHeight(); // 高度
            window.setAttributes(lp);     //将属性设置给窗体}
        }
    }


    protected int overrideWidth() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    protected int overrideHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }


    @Override
    public void show() {
        if (mContext != null && mContext instanceof Activity) {
            Activity ac = (Activity) mContext;
            if (ac.isFinishing()) {
                return;
            }
        }
        if (dialogListener != null) {
            dialogListener.onDialogShow();
        }
        super.show();
    }

    protected Activity getActivity() {
        return (Activity) mContext;
    }


    @Override
    public void dismiss() {
        super.dismiss();
        if (dialogListener != null) {
            dialogListener.onDialogDismiss();
        }
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T find(int id) {
        return (T) mContentView.findViewById(id);
    }

    public View getContentView() {
        return mContentView;
    }


    public QMUIBaseDialog setDialogListener(DialogListener listener) {
        this.dialogListener = listener;
        return this;
    }

    public interface DialogListener {
        void onDialogDismiss();

        void onDialogShow();
    }


}

