package com.modulec;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.basemodule.ARouterManager;
import com.basemodule.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wangwei on 2018/4/26.
 */
@Route(path = ARouterManager.CFragment)
public class CFragment extends BaseFragment {

    @BindView(R2.id.tv_module)
    TextView tv_module;
    @BindView(R2.id.btn_jump)
    Button btn_jump;
    @BindView(R2.id.tab_layout)
    Button tab_layout;

    @Override
    protected int initLayout() {
        return R.layout.c_fragment;
    }

    @Override
    protected void initView(View parent) {

    }


    @OnClick({R2.id.btn_jump, R2.id.tab_layout,R2.id.btn3})
    void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.btn_jump) {
            ARouter.getInstance().build(ARouterManager.AModuleActivity).navigation();
        } else if (id == R.id.tab_layout) {
            ARouter.getInstance().build(ARouterManager.MainLayoutActivity).navigation();
        } else if (id == R.id.btn3) {
            ARouter.getInstance().build(ARouterManager.ZhuJieActivity).navigation();
        }
    }

    @Override
    protected void initData() {

    }

}
