package com.android.newcommon.dialog;

import android.content.Context;
import android.widget.TextView;

import com.android.common.R;

import androidx.annotation.NonNull;

/**
 * @author wangwei
 * @date 2020/8/14.
 */
public class ZACommonDialog extends QMUIBaseDialog {

    private TextView tv_loading_msg;

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
        tv_loading_msg.setText("www");
    }


}
