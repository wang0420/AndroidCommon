package com.module.user.ativites;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.basemodule.ARouterManager;
import com.basemodule.base.BaseActivity;
import com.google.gson.Gson;
import com.module.user.R;
import com.module.user.bean.DataBean;
import com.module.user.iview.MainView;
import com.module.user.presenter.MainPresenter;

@Route(path = ARouterManager.ExampleActivity)
public class ExampleActivity extends BaseActivity<MainPresenter> implements MainView {
    private TextView text;
    private Button image_upload, button1, ui_btn;
    // https://www.wanandroid.com/article/list/0/json?cid=60


    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_main1;
    }

    @Override
    protected void initViews() {
        text = findViewById(R.id.text);
        button1 = findViewById(R.id.button1);
        image_upload = findViewById(R.id.upload);
        ui_btn = findViewById(R.id.ui_btn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //请求接口,统一处理返回的数据
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.getListData(60);
                    }
                }, 500);

            }
        });

        image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExampleActivity.this, MediaActivity.class));
            }
        });
        ui_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExampleActivity.this, UIActivity.class));
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    public void getDataSuccess(DataBean model) {
        dismissLoadingDialog();
        Toast.makeText(this, "請求成功", Toast.LENGTH_SHORT).show();
        //接口成功回调
        text.setText("---" + new Gson().toJson(model));
    }


}


