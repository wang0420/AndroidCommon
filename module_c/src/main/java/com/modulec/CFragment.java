package com.modulec;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.android.common.router.ActivityPath;
import com.android.newcommon.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wangwei on 2018/4/26.
 */
@Route(path = ActivityPath.CFragment)
public class CFragment extends BaseFragment {

    @BindView(R2.id.tv_module)
    TextView tv_module;
    @BindView(R2.id.btn_jump)
    Button btn_jump;
    @BindView(R2.id.tab_layout)
    Button tab_layout;

    @Override
    protected int layoutResID() {
        return R.layout.c_fragment;
    }

    @Override
    protected void initView(View parent) {

    }


    @OnClick({R2.id.btn_jump, R2.id.tab_layout, R2.id.btn3, R2.id.btn4, R2.id.btn5, R2.id.recyleview_list})
    void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.btn_jump) {
            ARouter.getInstance().build(ActivityPath.AModuleActivity).navigation();
        } else if (id == R.id.tab_layout) {
            ARouter.getInstance().build(ActivityPath.MainLayoutActivity).navigation();
        } else if (id == R.id.btn3) {
            ARouter.getInstance().build(ActivityPath.ZhuJieActivity).navigation();
        } else if (id == R.id.btn4) {
            ARouter.getInstance().build(ActivityPath.KotlinActivity).navigation();
        } else if (id == R.id.btn5) {
            ARouter.getInstance().build(ActivityPath.CModuleActivity).navigation();
        } else if (id == R.id.recyleview_list) {
            ARouter.getInstance().build(ActivityPath.RecyclerViewHomeActivity).navigation();
        }
    }

    @Override
    protected void initData() {

    }

}
