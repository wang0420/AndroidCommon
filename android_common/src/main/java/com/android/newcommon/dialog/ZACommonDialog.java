package com.android.newcommon.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import com.android.common.R;

import androidx.annotation.NonNull;

/**
 * @author wangwei
 * @date 2020/8/14.
 */
public class ZACommonDialog extends QMUIBaseDialog implements View.OnClickListener {

    private TextView tv_loading_msg;
    private DialogInterface.OnClickListener mLeftBtnClickListener;

    public ZACommonDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    public int getLayoutId() {
        return R.layout.w_dialog;
    }

    @Override
    public void initViewData() {
        tv_loading_msg = find(R.id.tv_loading_msg);
        tv_loading_msg.setOnClickListener(this);
        tv_loading_msg.setText("www");
    }

    public ZACommonDialog setLeftBtnClickListener(DialogInterface.OnClickListener listener) {
        mLeftBtnClickListener = listener;
        return this;
    }

    @Override
    public void onClick(View v) {

    }
}
