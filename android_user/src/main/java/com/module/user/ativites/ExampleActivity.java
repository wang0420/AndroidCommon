package com.module.user.ativites;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.basemodule.ARouterManager;
import com.basemodule.base.BaseActivity;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.VideoView;
import com.google.gson.Gson;
import com.module.ui.activity.UIActivity;
import com.module.ui.util.DisplayUtil;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayUtil.getDisplay(this);
        setContentView(R.layout.activity_main1);
        viplay();
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
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void getDataSuccess(DataBean model) {
        dismissLoadingDialog();
        Toast.makeText(this, "請求成功", Toast.LENGTH_SHORT).show();
        //接口成功回调
        text.setText("---" + new Gson().toJson(model));
    }


    private void viplay() {
        //startActivity(new Intent(ExampleActivity.this,WelcomeActivity.class));
        VideoView videoView = (VideoView) findViewById(R.id.player);
        String URL_VOD = "http://www.jmzsjy.com/UploadFile/微课/地方风味小吃——宫廷香酥牛肉饼.mp4";

        videoView.setUrl(URL_VOD); //设置视频地址
        StandardVideoController controller = new StandardVideoController(this);
        controller.addDefaultControlComponent("标题", false);
        controller.setVisibility(View.GONE);
        videoView.setVideoController(controller); //设置控制器
       videoView.start(); //开始播放，不调用则不自动播放


    }

}


