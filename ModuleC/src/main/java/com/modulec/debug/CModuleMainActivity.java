package com.modulec.debug;

import android.support.v4.app.FragmentTransaction;

import com.basemodule.BaseActivity;
import com.modulec.CFragment;
import com.modulec.R;

/**
 * Created by wangwei on 2018/4/26.
 */

public class CModuleMainActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.c_module_main;
    }

    @Override
    protected void initView() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        CFragment fragment = new CFragment();
        transaction.add(R.id.id_content, fragment);
        transaction.commit();

    }
}
