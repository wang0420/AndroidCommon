package com.moudlea;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.basemodule.ARouterManager;
import com.basemodule.BaseFragment;
import com.basemodule.bean.EmployeeBean;
import com.basemodule.bean.TestObj;
import com.basemodule.bean.TestParcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by wangwei on 2018/4/26.
 */
@Route(path = ARouterManager.AFragment)
public class AFragment extends BaseFragment {

    private TextView tvModule;
    private Button btnButton;
    private Button btn2, btn3;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_a;
    }

    @Override
    protected View initView(View parent) {
        tvModule = parent.findViewById(R.id.tv_module);
        btnButton = parent.findViewById(R.id.btn_jump);
        btn2 = parent.findViewById(R.id.btn2);
        btn3 = parent.findViewById(R.id.btn3);
        tvModule.setText("首页\nAModule\n\n\n点击跳转到BModule的一个Activity");
        setListener();
        return parent;
    }

    private void setListener() {
        btnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build(ARouterManager.AModuleActivity, "customGroup").navigation(getActivity(), new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {
                        String group = postcard.getGroup();
                        Log.e("zhao", "分组是: " + group);
                    }
                });
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 2. 跳转并携带参数  对象
                List<TestObj> list = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    TestObj testObj = new TestObj("Rose" + i, 777);
                    list.add(testObj);
                }
                ARouter.getInstance().build(ARouterManager.BModuleActivity)
                        .withString("name", "老王")
                        .withInt("age", 18)
                        .withString("url", "https://a.b.c")
                        .withSerializable("pac", (Serializable) list)
                        .navigation();

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build(ARouterManager.BModuleActivity).navigation(getActivity(), 100);

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getActivity(), "onActivityResult", Toast.LENGTH_LONG).show();
        Log.w("TAG", "-------onActivityResult");

    }
}
