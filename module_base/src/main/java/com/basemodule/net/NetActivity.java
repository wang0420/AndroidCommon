package com.basemodule.net;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.basemodule.R;
import com.basemodule.base.BaseActivity;
import com.basemodule.bean.DataBean;
import com.basemodule.iview.MainView;
import com.basemodule.service.MainPresenter;
import com.google.gson.Gson;


public class NetActivity extends BaseActivity<MainPresenter> implements MainView {
    private TextView text;
    private Button button1;
    // https://www.wanandroid.com/article/list/0/json?cid=60


    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_example;
    }

    @Override
    protected void initViews() {
        text = findViewById(R.id.text);
        button1 = findViewById(R.id.button1);
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


