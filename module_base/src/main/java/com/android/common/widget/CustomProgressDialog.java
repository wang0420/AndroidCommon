package com.android.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.common.R;



/**
 * 页面内弹框
 */

public class CustomProgressDialog extends Dialog {

    private Context mContext;
    private TextView titleText;


    public CustomProgressDialog(Context context) {
        this(context, R.style.customDialog);
        this.mContext = context;
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
        setDialogContentView();
    }


    /**
     * 设置dialog里面的view
     */
    private void setDialogContentView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_progress_dialog, null);   // 加载自己定义的布局
        titleText = view.findViewById(R.id.tv_loading_msg);
        this.setContentView(view);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);
        //获取当前Activity所在的窗体
        Window dialogWindow = getWindow();
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = -1;
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);

    }


    /**
     * setMessage 提示内容
     */
    public void setMessage(String msg) {
        if (titleText == null) {
            return;
        }
        if (!TextUtils.isEmpty(msg)) {
            titleText.setText(msg);
            titleText.setVisibility(View.VISIBLE);
        } else {
            titleText.setVisibility(View.GONE);
        }


    }


    @Override
    public void dismiss() {
        super.dismiss();
    }


}

