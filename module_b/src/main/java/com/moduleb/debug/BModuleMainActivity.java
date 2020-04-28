package com.moduleb.debug;

import androidx.fragment.app.FragmentTransaction;

import com.moduleb.BFragment;
import com.moduleb.R;

/**
 * Created by wangwei on 2018/4/26.
 */

public class BModuleMainActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.b_module_main;
    }

    @Override
    protected void initView() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        BFragment fragment = new BFragment();
        transaction.add(R.id.id_content, fragment);
        transaction.commit();

    }
}
