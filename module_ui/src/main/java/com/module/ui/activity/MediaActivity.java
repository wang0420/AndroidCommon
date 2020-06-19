package com.module.ui.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.common.utils.FileUtils;
import com.module.ui.R;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by wangwei on 2019/4/28.
 */

public class MediaActivity extends AppCompatActivity {
    private Button start, stop;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_media);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initMedia();
//                FileUtils.getInstance().creatFile("haha");
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });

    }

    /**
     * 在使用start()播放流媒体之前，需要装载流媒体资源。这里最好使用prepareAsync()
     * 用异步的方式装载流媒体资源。因为流媒体资源的装载是会消耗系统资源的，在一些硬件不理想的设备上，
     * 如果使用prepare()同步的方式装载资源，可能会造成UI界面的卡顿，这是非常影响用于体验的。
     * 因为推荐使用异步装载的方式，为了避免还没有装载完成就调用start()而报错的问题，
     * 需要绑定MediaPlayer.setOnPreparedListener()事件，它将在异步装载完成之后回调。异
     * 步装载还有一个好处就是避免装载超时引发ANR（(Application Not Responding）错误。
     */
    private void initMedia() {
        String path = FileUtils.getInstance().getImageFile() + "/music.mp3";
        String path1 = "http://96.30.199.133/trade.m4a";
        Log.w("TAG", "--  -" + path);
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 通过异步的方式装载媒体资源
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // 装载完毕回调
                mediaPlayer.start();
            }
        });
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                Log.w("TAG", "缓冲中---" + percent);

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.w("TAG", "播放到---" + mediaPlayer.getCurrentPosition());
                Log.w("TAG", "total---" + mediaPlayer.getDuration());

            }
        }).start();
        // 设置循环播放
//                mediaPlayer.setLooping(true);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // 在播放完毕被回调
                Log.w("TAG", "播放结束了");
            }
        });
    }


    public void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


}
