package com.basemodule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initToolbar();
        initView();
    }

    protected abstract int getLayoutId();

    protected abstract void initView();


    protected void initToolbar() {



    }

    //点击事件
    private void onLeftClick() {
        finish();

    }

}
