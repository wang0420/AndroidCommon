package com.module.user.ativites;

import android.view.View;

import com.basemodule.base.BaseActivity;
import com.basemodule.base.BasePresenter;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.VideoView;
import com.module.user.R;

/**
 * Created by wangwei on 2020/4/23.
 */

public class VideoPlayActivity extends BaseActivity {
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_play_video;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

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
