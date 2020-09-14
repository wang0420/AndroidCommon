package com.android.newcommon.utils.activity;

import android.view.View;
import android.widget.Button;

import com.android.common.R;
import com.android.newcommon.base.BaseTitleActivity;

/**
 * @author wangwei
 * @date 2020/9/14.
 */
public class ZaActivity extends BaseTitleActivity {
    Button btn_go3;

    @Override
    public void initView() {
        btn_go3 = findViewById(R.id.btn_ac);
        btn_go3.setText("ZaActivity");
        btn_go3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(20000);
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public int layoutResID() {
        return R.layout.ac_test;
    }
}
