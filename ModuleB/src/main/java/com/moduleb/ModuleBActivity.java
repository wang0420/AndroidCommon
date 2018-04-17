package com.moduleb;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.basemodule.BaseActivity;
import com.basemodule.MyARouter;

/**
 * Created by wangwei on 2018/4/17.
 */

public class ModuleBActivity extends BaseActivity {
    private TextView button;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_b;
    }

    @Override
    protected void initView() {
        button = findViewById(R.id.button);
        button.setText("ModuleB");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
