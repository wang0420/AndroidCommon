package com.android.newcommon.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

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
    private Dialog mDialog;


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

        setListener();

    }

    private void setListener() {
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
    }

    ;


    protected void initView() {
        mContentView = getLayoutInflater().inflate(getLayoutId(), null, false);
        this.setContentView(mContentView);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);
    }

    /**
     * 默认显示在屏幕中间
     */
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

    /**
     * 显示在底部
     */
    public void showBottom() {
        Window window = getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.CommonDialog_Fullscreen_Transparent_BottomDialog); //设置窗口弹出动画
        }
        show();
    }
}

