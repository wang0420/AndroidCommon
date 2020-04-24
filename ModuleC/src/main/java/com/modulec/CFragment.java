package com.modulec;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.basemodule.ARouterManager;
import com.basemodule.BaseFragment;

/**
 * Created by wangwei on 2018/4/26.
 */
@Route(path = ARouterManager.CFragment)
public class CFragment extends BaseFragment {

    private TextView tvModule;
    private Button btnButton;

    @Override
    protected int initLayout() {
        return R.layout.c_fragment;
    }

    @Override
    protected void initView(View parent) {
        tvModule = parent.findViewById(R.id.tv_module);
        btnButton = parent.findViewById(R.id.btn_jump);
        tvModule.setText("我\nCModule");
        btnButton.setText("点击跳转到AModule的一个Activity");

        btnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build(ARouterManager.AModuleActivity).navigation();
            }
        });

    }

    @Override
    protected void initData() {

    }

}
