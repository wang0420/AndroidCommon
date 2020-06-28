package com.moduleb;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.common.ARouterManager;
import com.android.common.bean.MemberEvent;
import com.android.newcommon.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@Route(path = ARouterManager.BFragment)
public class BFragment extends BaseFragment {

    private TextView tvModule;
    private Button btnButton;

    @Override
    protected int layoutResID() {
        return R.layout.fragment_b;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MemberEvent Event) {
        Log.w("TAG", "onMoonEvent");
    }

    @Override
    protected void initView(View parent) {
        tvModule = parent.findViewById(R.id.tv_module);
        tvModule.setText("会员\nBModule");
        EventBus.getDefault().register(this);    //注册事件

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册事件
    }
}
