package com.moudlea.jetpack;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.android.common.base.BaseActivity;
import com.android.common.base.BasePresenter;
import com.moudlea.R;
import com.moudlea.R2;
import com.moudlea.jetpack.lifecycles.LifeCyclesActivity;
import com.moudlea.jetpack.mvvm.MVVMActivity;
import com.moudlea.jetpack.navigation.NaviActivity;
import com.moudlea.jetpack.room.RoomActivity;
import com.moudlea.jetpack.viewmode.ViewModuleActivity;
import com.moudlea.jetpack.work.WorkActivity;

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


    @OnClick({R2.id.btn1, R2.id.btn2, R2.id.btn3, R2.id.btn4, R2.id.btn5, R2.id.btn6})
    void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.btn1) {
            startActivity(new Intent(this, ViewModuleActivity.class));
        } else if (id == R.id.btn2) {
            startActivity(new Intent(this, LifeCyclesActivity.class));
        } else if (id == R.id.btn3) {
            startActivity(new Intent(this, NaviActivity.class));
        } else if (id == R.id.btn4) {
            startActivity(new Intent(this, RoomActivity.class));
        }else if (id == R.id.btn5) {
            startActivity(new Intent(this, WorkActivity.class));
        }else if (id == R.id.btn6) {
            startActivity(new Intent(this, MVVMActivity.class));
        }
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }
}
