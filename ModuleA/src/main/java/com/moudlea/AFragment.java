package com.moudlea;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.basemodule.ARouterManager;
import com.basemodule.BaseFragment;
import com.basemodule.bean.Author;


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
        tvModule.setText("首页AModule");
        setListener();
        return parent;
    }

    private void setListener() {
        btnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EventBus.getDefault().post(new MemberEvent(""));//刷新会员
                ARouter.getInstance().build(ARouterManager.ExampleActivity).navigation();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Author author = new Author();
                author.setName("Margaret Mitchell");
                author.setCounty("USA");
                ARouter.getInstance().build(ARouterManager.BModuleActivity)
                        .withString("name", "老王")
                        .withInt("age", 18)
                        .withString("url", "https://a.b.c")
                        .withSerializable("author", author)
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
