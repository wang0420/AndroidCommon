package com.module.ui.widget.favor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import com.android.newcommon.utils.BitmapUtils;
import com.module.ui.R;

import java.lang.ref.WeakReference;
import java.util.Random;

/**
 * 飘星星控件
 */
public class FavorWidget2 extends SurfaceView implements View.OnClickListener {
    private static final int MAX_FAVOR_COUNT = 24;

    private BezierController3 mBezierController;
    private Bitmap[] mBitmaps;
    //    private boolean mUsingRemoteIcons;// 是否已经使用后台配置的icon
    private Random mRandom;
    private FavorSender mFavorSender;
    private long mLastClickTime;// 上一次点击时间
    private int mSendingCount = 0;// 单个发送点赞时间窗口内，点击的次数
    private boolean mIsPaused;

    private FavorHandler mFavorHandler;
    private Handler mHandler;

    public FavorWidget2(Context context) {
        this(context, null, 0);
    }

    public FavorWidget2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FavorWidget2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setZOrderOnTop(true);
        setOnClickListener(this);

        mFavorHandler = new FavorHandler(this);

        mBezierController = new BezierController3(getHolder(), new Paint(Paint.ANTI_ALIAS_FLAG));
        mBezierController.setSleepDuration(5);
        mRandom = new Random();
        //直播间点亮逻辑及UI改动,每秒钟自动喷射14个点亮（具体频率需产品体验时可能有所调整）
        mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.w("TAG", "----run--");
                displayRandomFavorPassively(14);
                mHandler.postDelayed(this, 10000);
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (mBezierController != null) {
            mBezierController.stop();
        }

        if (mFavorHandler != null) {
            mFavorHandler.stop();
            mFavorHandler = null;
        }
        if (mBitmaps != null) {
            for (Bitmap bitmap : mBitmaps) {
                BitmapUtils.recycle(bitmap);
            }
        }
        mBitmaps = null;

        mRandom = null;
        mFavorSender = null;
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    public void setBitmaps(Bitmap[] bitmaps, boolean isRemoteIcons) {
        mBitmaps = bitmaps;
//        mUsingRemoteIcons = isRemoteIcons;
    }

    /**
     * addFavor方法，均是主动点击进行点赞
     */
    private void addRandomFavor() {
        if (mBitmaps == null || mBitmaps.length == 0) {
            return;
        }
        if (mBitmaps.length == 1) {
            addFavor(mBitmaps[0]);
            return;
        }
        if (mRandom == null) {
            mRandom = new Random();
        }
        Bitmap bitmap = null;
        while (bitmap == null) {
            bitmap = mBitmaps[mRandom.nextInt(mBitmaps.length)];
        }
        addFavor(bitmap);
    }

    private void addFavor(Bitmap bitmap) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        width = width <= 0 ? 40 : width;
        int startX = width / 2;
        int startY = height - bitmap.getHeight() / 2;
        int endX = mRandom.nextInt(width);
        int endY = 0;
        FavorBean favor = new FavorBean(mBezierController,
                new Point(startX, startY), new Point(endX, endY), bitmap, mRandom);
        addFavor(favor);
    }

    private void addFavor(FavorBean favor) {
        if (mBezierController.getBeanCount() < MAX_FAVOR_COUNT) {
            mBezierController.addBean(favor);
        }
    }

    /**
     * 被动点赞，断续抛出爱心
     *
     * @param count
     */
    private int totalCount = 0;

    public void displayRandomFavorPassively(int count) {
        if (mIsPaused) {
            return;
        }

        totalCount += count;
        if (totalCount > 64) {
            totalCount = 64;
        }
        if (mFavorHandler != null) {
            mFavorHandler.removeMessages(0);
            mFavorHandler.sendEmptyMessage(0);
        }
    }

    @Override
    public void onClick(View v) {
        // 本地可以连续点赞，但每隔250ms才给远端点赞
        addRandomFavor();
        long now = System.currentTimeMillis();
        mSendingCount++;
        if (now - mLastClickTime >= 2000L) {
            mLastClickTime = now;
            if (mFavorSender != null) {
                mFavorSender.onSendFavor(mSendingCount);
            }
            mSendingCount = 0;
        }
    }

    public void pause() {
        mIsPaused = true;
        if (mBezierController != null) {
            mBezierController.pause(true);
        }
    }

    public void resume() {
        mIsPaused = false;
    }

    public void setFavorSender(FavorSender sender) {
        mFavorSender = sender;
    }

    public interface FavorSender {
        void onSendFavor(int count);
    }

    public static class FavorBean extends BitmapBezierBean3 {

        FavorBean(BezierController3 controller, Point start, Point end, Bitmap bitmap,
                  Random random) {
            super(controller, start, end, bitmap);
            mRandom = random;
            start();
        }

        @Override
        protected void onStart() {
            Random random = mRandom;
            if (random == null) {
                return;
            }

            // 平移
            int centerX = random.nextInt(mStart.x * 2);
            int centerY = Math.abs(mEnd.y - mStart.y) / 150;
            mCenter = new Point(centerX, centerY);
        }
    }

    private static class FavorHandler extends Handler {
        private WeakReference<FavorWidget2> mFavorWidget;

        public FavorHandler(FavorWidget2 widget) {
            mFavorWidget = new WeakReference<>(widget);
        }

        public void stop() {
            mFavorWidget = null;
        }

        @Override
        public void handleMessage(Message msg) {
            FavorWidget2 widget;
            if (mFavorWidget != null && (widget = mFavorWidget.get()) != null) {
               // Bitmap[] remoteIcons = GiftResourceManager.getLoadedFavorIcons();
                Bitmap bitmap2 = BitmapFactory.decodeResource(widget.getResources(), R.drawable.icon_live_voice_favor_4, null);
                Bitmap bitmap4 = BitmapFactory.decodeResource(widget.getResources(), R.drawable.icon_live_voice_favor_2, null);
                Bitmap bitmap5 = BitmapFactory.decodeResource(widget.getResources(), R.drawable.icon_live_voice_favor_5, null);
                Bitmap bitmap6 = BitmapFactory.decodeResource(widget.getResources(), R.drawable.icon_live_voice_favor_1, null);
                Bitmap bitmap7 = BitmapFactory.decodeResource(widget.getResources(), R.drawable.icon_live_voice_favor_3, null);
                Bitmap bitmap8 = BitmapFactory.decodeResource(widget.getResources(), R.drawable.icon_live_voice_favor_2, null);

                Bitmap[] remoteIcons = { bitmap2, bitmap4, bitmap5, bitmap6, bitmap7, bitmap8};

                if (remoteIcons != null) {
                    widget.setBitmaps(remoteIcons, true);
                }

                if (!widget.mIsPaused && widget.totalCount > 0) {
                    widget.addRandomFavor();

                    sendEmptyMessageDelayed(0, 50L);

                    --widget.totalCount;
                }
            }
        }
    }
}