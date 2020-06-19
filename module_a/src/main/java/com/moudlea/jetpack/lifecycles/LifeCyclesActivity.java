package com.moudlea.jetpack.lifecycles;

import android.view.View;
import android.widget.TextView;

import com.android.common.base.BaseActivity;
import com.android.common.base.BasePresenter;
import com.moudlea.R;
import com.moudlea.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wangwei on 2020/4/29.
 */

public class LifeCyclesActivity extends BaseActivity {
    @BindView(R2.id.life_text)
    TextView life_text;


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_lifecycles;
    }

    @Override
    protected void initViews() {
        //谁观察生命  就注册谁  两个角色定义好后，需要让他们之间建立联系
        //获取Lifecycle
        getLifecycle().addObserver(new LocationListener());

    }

    @OnClick({R2.id.life_text})
    void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.life_text) {

        }
    }


    @Override
    protected void initData() {


    }

}
