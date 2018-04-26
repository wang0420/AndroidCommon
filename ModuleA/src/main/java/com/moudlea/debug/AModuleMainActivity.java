package com.moudlea.debug;

import android.support.v4.app.FragmentTransaction;

import com.basemodule.BaseActivity;
import com.moudlea.AFragment;
import com.moudlea.R;

/**
 * Created by wangwei on 2018/4/26.
 */

public class AModuleMainActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.a_module_main;
    }

    @Override
    protected void initView() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        AFragment fragment = new AFragment();
        transaction.add(R.id.id_content, fragment);
        transaction.commit();

    }
}
