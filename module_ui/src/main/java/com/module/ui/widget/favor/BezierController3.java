package com.module.ui.widget.favor;

import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.view.SurfaceHolder;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 贝塞尔控制器
 *

 */

public class BezierController3 implements SurfaceHolder.Callback {
    private CopyOnWriteArrayList<BezierBean3> mBeans;
    private BezierDrawThread3 mDrawThread;
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;
    private long mSleepDuration;

    public BezierController3(SurfaceHolder surfaceHolder, Paint paint) {
        mSurfaceHolder = surfaceHolder;
        mPaint = paint;

        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        mSurfaceHolder.addCallback(this);

        mBeans = new CopyOnWriteArrayList<>();
        mDrawThread = BezierDrawThread3(mSurfaceHolder, mPaint);
    }

    public void setSleepDuration(long sleepDuration) {
        mSleepDuration = sleepDuration;
        if (mDrawThread != null) {
            mDrawThread.setSleepDuration(sleepDuration);
        }
    }

    public void addBean(BezierBean3 bean) {
        mBeans.add(bean);
        startDrawThread();
    }

    public void endBean(BezierBean3 bean) {
        if (!bean.isEnded()) {
            bean.end();
        }
        mBeans.remove(bean);
    }

    public int getBeanCount() {
        return mBeans.size();
    }

    public void pause(boolean clearBeans) {
//        if (mDrawThread != null) {
//            mDrawThread.stopSelf();
//            mDrawThread = null;
//        }
        if (clearBeans) {
            for (BezierBean3 bean : mBeans) {
                if (bean != null && !bean.isEnded()) {
                    bean.end();
                }
            }
            mBeans.clear();
        }
    }

    public void stop() {
        pause(true);
        if (mSurfaceHolder != null) {
            mSurfaceHolder.removeCallback(this);
        }
        mPaint = null;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
//        startDrawThread();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mDrawThread != null) {
            mDrawThread.stopSelf();
            notifyDrawThread();
            mDrawThread = null;
        }
    }

    private void startDrawThread() {
        if (mBeans == null || mBeans.isEmpty()) {
            return;
        }
        if (mDrawThread == null || mDrawThread.isDestroyed()) {
            mDrawThread = BezierDrawThread3(mSurfaceHolder, mPaint);
        }
        if (mDrawThread.getState() == Thread.State.NEW) {
            mDrawThread.start();
        } else {
            notifyDrawThread();
        }
    }

    private void notifyDrawThread() {
        if (mDrawThread != null && mDrawThread.isBlocked) {
            synchronized (mDrawThread.mLock) {
                mDrawThread.mLock.notifyAll();
            }
        }
    }

    private BezierDrawThread3 BezierDrawThread3(SurfaceHolder surfaceHolder, Paint paint) {
        BezierDrawThread3 thread = new BezierDrawThread3(surfaceHolder, paint);
        thread.setBeans(mBeans);
        thread.setSleepDuration(mSleepDuration);
        return thread;
    }
}
