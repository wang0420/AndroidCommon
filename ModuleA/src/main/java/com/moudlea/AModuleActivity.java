package com.moudlea;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.basemodule.ARouterManager;
import com.basemodule.base.BaseActivity;
import com.basemodule.base.BasePresenter;

/**
 * Created by wangwei on 2018/4/17.
 */

@Route(path = ARouterManager.AModuleActivity, group = "customGroup")
public class AModuleActivity extends BaseActivity {
    TextView txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.le_layout);
        initView();
    }


    protected void initView() {
        txt = findViewById(R.id.txt);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
