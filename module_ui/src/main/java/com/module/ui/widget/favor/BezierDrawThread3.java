package com.module.ui.widget.favor;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.SystemClock;
import android.view.SurfaceHolder;

import java.util.List;

/**
 * 贝塞尔绘制线程
 *
 */

class BezierDrawThread3 extends Thread {
    public final Object mLock = new Object();
    public boolean isBlocked;
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;
    private List<BezierBean3> mBeans;
    private long mSleepDuration;

    private boolean mRun;
    private boolean mDestroyed;

    BezierDrawThread3(SurfaceHolder surfaceHolder, Paint paint) {
        mSurfaceHolder = surfaceHolder;
        mPaint = paint;
        mRun = true;
        mDestroyed = false;
    }

    public void setBeans(List<BezierBean3> beans) {
        mBeans = beans;
    }

    public void setSleepDuration(long sleepDuration) {
        mSleepDuration = sleepDuration;
    }

    public boolean isDestroyed() {
        return !mRun || mDestroyed;
    }

    @Override
    public void run() {
        mRun = checkValid();

        Canvas canvas;
        Paint paint = mPaint;
        boolean allEnded;
        long sleep;
        long drawTime;
        while (mRun) {
            synchronized (mLock) {
                allEnded = true;
                try {
                    long startTime = SystemClock.uptimeMillis();
                    canvas = mSurfaceHolder.lockCanvas();

                    // 先清除画面
                    if (canvas != null) {
                        clearCanvas(canvas);

                        // 绘制bean
                        List<BezierBean3> beans = mBeans;
                        for (BezierBean3 bean : beans) {
                            if (!bean.isEnded()) {
                                allEnded = false;
                                bean.onDraw(canvas, paint);
                            }
                        }

                        if (allEnded) {
                            clearCanvas(canvas);
                        }
                    }

                    mSurfaceHolder.unlockCanvasAndPost(canvas);
                    drawTime = SystemClock.uptimeMillis() - startTime;
                    // 检查是否结束线程
                    if (allEnded && mRun) {
                        isBlocked = true;
                        mLock.wait();
                    }
                    isBlocked = false;

                    // 线程睡眠，控制绘制频率
                    sleep = mSleepDuration - drawTime;
                    if (sleep > 0L) {
                        Thread.sleep(sleep);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mRun = false;
                }
            }
        }

        // 在线程结束时释放资源
        mDestroyed = true;
        mSurfaceHolder = null;
        mPaint = null;
        mBeans = null;
    }

    public void stopSelf() {
        mRun = false;
    }

    private boolean checkValid() {
        return !isDestroyed() && mSurfaceHolder != null && mBeans != null;
    }

    private void clearCanvas(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }
}
