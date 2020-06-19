package com.moudlea.debug;

import androidx.fragment.app.FragmentTransaction;

import com.android.common.base.BaseActivity;
import com.android.common.base.BasePresenter;
import com.moudlea.AFragment;
import com.moudlea.R;

/**
 * Created by wangwei on 2018/4/26.
 */

public class AModuleMainActivity extends BaseActivity {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int initLayout() {
        return R.layout.a_module_main;
    }

    @Override
    protected void initViews() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        AFragment fragment = new AFragment();
        transaction.add(R.id.id_content, fragment);
        transaction.commit();
    }

    @Override
    protected void initData() {

    }
}
