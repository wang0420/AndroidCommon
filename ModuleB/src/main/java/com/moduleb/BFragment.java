package com.moduleb;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.basemodule.ARouterManager;
import com.basemodule.BaseFragment;
import com.basemodule.bean.MemberEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@Route(path = ARouterManager.BFragment)
public class BFragment extends BaseFragment {

    private TextView tvModule;
    private Button btnButton;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_b;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MemberEvent Event) {
        Log.w("TAG", "onMoonEvent");
    }

    @Override
    protected View initView(View parent) {
        tvModule = parent.findViewById(R.id.tv_module);
        btnButton = parent.findViewById(R.id.btn_jump);
        tvModule.setText("会员\nBModule");
        btnButton.setText("点击跳转到CModule的一个Activity");
        btnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build(ARouterManager.CModuleActivity).navigation();

            }
        });
        EventBus.getDefault().register(this);    //注册事件

        return parent;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册事件
    }
}
