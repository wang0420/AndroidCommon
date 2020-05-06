package com.moudlea;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.basemodule.ARouterManager;
import com.basemodule.BaseFragment;
import com.basemodule.bean.Author;
import com.basemodule.service.IUserModuleService;
import com.moudlea.jetpack.JetPackStudyActivity;
import com.moudlea.rxjava.RxActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by wangwei on 2018/4/26.
 */
@Route(path = ARouterManager.AFragment)
public class AFragment extends BaseFragment {

    @BindView(R2.id.btn1)
    Button btn1;
    @BindView(R2.id.btn2)
    Button btn2;
    @BindView(R2.id.btn3)
    Button btn3;
    @BindView(R2.id.btn4)
    Button btn4;
    @BindView(R2.id.btn5)
    Button btn5;
    @BindView(R2.id.btn6)
    Button btn6;


    @Override
    protected int initLayout() {
        return R.layout.fragment_a;
    }

    @Override
    protected void initView(View view) {
        setListener();
    }

    @OnClick({R2.id.btn4, R2.id.btn5, R2.id.btn6})
    void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.btn4) {
            btn4.setText(getUserAddress());
        } else if (id == R.id.btn5) {
            startActivity(new Intent(mActivity, JetPackStudyActivity.class));
        } else if (id == R.id.btn6) {
            startActivity(new Intent(mActivity, RxActivity.class));
        }
    }

    public String getUserAddress() {
        IUserModuleService userModuleService = ARouter.getInstance().navigation(IUserModuleService.class);
        if (userModuleService != null) {
            return userModuleService.getUserInfo();
        }
        return "";
    }

    @Override
    protected void initData() {

    }

    private void setListener() {
        btn1.setOnClickListener(new View.OnClickListener() {
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
