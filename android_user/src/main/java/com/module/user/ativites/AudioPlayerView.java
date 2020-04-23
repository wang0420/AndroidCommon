package com.module.user.ativites;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


import com.module.user.R;

import java.io.IOException;

/**
 * Created by wangwei on 2019/5/5.
 */

public class AudioPlayerView extends AppCompatTextView {

    String mUrl;

    public boolean isHasprepared() {
        return hasprepared;
    }

    /**
     * 在非初始化状态下调用setDataSource  会抛出IllegalStateException异常
     */
    boolean hasprepared = false;

    private MediaPlayer mediaPlayer;

    private Handler handler;
    private Runnable runnable;
    private int i;

    private int[] drawLefts = new int[]{R.mipmap.voice1, R.mipmap.voice2, R.mipmap.voice3};

    public AudioPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initMediaPlayer();
    }

    private void initMediaPlayer() {

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        } catch (Exception e) {
            Log.e("mediaPlayer", " init error", e);
        }

        if (mediaPlayer != null) {
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {

                    hasprepared = true;
                    setText(getDuration());
                }
            });
        }
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mediaPlayer.reset();
                return false;
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                stopAnim();
            }
        });
        setClick();

    }

    public void setUrl(String url) {

        mUrl = url;
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("mediaPlayer", " set dataSource error", e);
        } catch (IllegalStateException e) {
            Log.e("mediaPlayer", " set dataSource error", e);
        }
    }

    /**
     * 用于需要设置不同的dataSource
     * 二次setdataSource的时候需要reset 将MediaPlayer恢复到Initialized状态
     *
     * @param url
     */
    public void resetUrl(String url) {

        if (TextUtils.isEmpty(mUrl) || hasprepared) {
            mediaPlayer.reset();
        }
        setUrl(url);
    }

    public String getDuration() {

        int duration = mediaPlayer.getDuration();

        if (duration == -1) {
            return "";
        } else {

            int sec = duration / 1000;

            int m = sec / 60;
            int s = sec % 60;
            return m + ":" + s;
        }

    }

    private void startAnim() {

        i = 0;
        if (handler == null) {
            handler = new Handler();
        }
        if (runnable == null) {
            runnable = new Runnable() {
                @Override
                public void run() {

                    handler.postDelayed(this, 500);
                    setDrawableLeft(drawLefts[i % 3]);
                    i++;
                }
            };
        }

        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 500);
    }

    private void stopAnim() {

        setDrawableLeft(drawLefts[2]);
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }

    /**
     * 设置 drawableLeft
     *
     * @param
     * @param id
     */
    private void setDrawableLeft(@DrawableRes int id) {
        Drawable leftDrawable = getResources().getDrawable(id);
        leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
        setCompoundDrawables(leftDrawable, null, null, null);

    }

    private void setClick() {

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

//                第一次调用start播放正常. 然后调用stop().停止播放.然后再调start 放不了

//
                //方案一： 先stop，再Reset ，stop换成pause也行
//                if (mediaPlayer.isPlaying()){
//                    mediaPlayer.stop();
//                    stopAnim();
//                }else {
//                    mediaPlayer.reset();
//                    setUrl(mUrl);
//                    startAnim();
//                    mediaPlayer.start();
//
//                }

                // 采取方案二  pause  代替 stop
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    stopAnim();
                } else {
                    mediaPlayer.seekTo(0);
                    startAnim();
                    mediaPlayer.start();
                }

            }

        });
    }

    /**
     * 释放资源
     */
    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }

}

