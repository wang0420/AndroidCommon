package com.moduleb;

import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.basemodule.BaseActivity;

/**
 * Created by wangwei on 2018/4/17.
 */
@Route(path = "/b/ModuleBActivity")
public class ModuleBActivity extends BaseActivity {
    private TextView button;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_b;
    }

    @Override
    protected void initView() {
        button = findViewById(R.id.button);
        button.setText("这又是一个新的页面");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
