package com.moudlea.jetpackStudy;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.basemodule.base.BaseActivity;
import com.basemodule.base.BasePresenter;
import com.moudlea.R;
import com.moudlea.R2;
import com.moudlea.jetpackStudy.lifecycles.LifeCyclesActivity;
import com.moudlea.jetpackStudy.navigation.NaviActivity;
import com.moudlea.jetpackStudy.viewmode.ViewModuleActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wangwei on 2020/4/29.
 */

public class JetPackStudyActivity extends BaseActivity {
    @BindView(R2.id.btn1)
    Button btn1;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_jetpack;
    }


    @OnClick({R2.id.btn1, R2.id.btn2, R2.id.btn3})
    void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.btn1) {
            startActivity(new Intent(this, ViewModuleActivity.class));
        } else if (id == R.id.btn2) {
            startActivity(new Intent(this, LifeCyclesActivity.class));
        }else if (id == R.id.btn3) {
            startActivity(new Intent(this, NaviActivity.class));
        }
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }
}
