package com.module.ui.widget.favor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

/**
 * 贝塞尔曲线实体（使用位图）
 *
 */

public abstract class BitmapBezierBean3 extends BezierBean3 {
    public static final int TIME_CYCLE = 20;
    public Bitmap mBitmap;
    protected Random mRandom;

    public BitmapBezierBean3(BezierController3 controller, Point start, Point end, Bitmap bitmap) {
        super(controller, start, end);

        mBitmap = bitmap;
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        Bitmap bitmap = mBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            Point point = evaluatePosition(TIME_CYCLE, mStart, mEnd);
            if (point == null) {
                return;
            }

            Matrix matrix = mMatrix;
            int x = point.x;
            int y = point.y;
            int w2 = bitmap.getWidth() / 2;
            int h2 = bitmap.getHeight() / 2;
            evaluateScale(TIME_CYCLE);
            matrix.setScale(mScaleX, mScaleY, w2, h2);
            matrix.postTranslate(x - w2, y - h2);
            evaluateRotate(TIME_CYCLE);
            matrix.postRotate(mRotate, x + w2, y + h2);

            paint.setAlpha(mAlpha);

            canvas.drawBitmap(bitmap, matrix, paint);
        }
    }

    protected Point mCenter;
    protected Point mResult;
    protected long mDuration = 3000L;
    protected float t = 0f;

    public Point evaluatePosition(int timeCycle, Point start, Point end) {
        t += (timeCycle + 0f) / mDuration;
//        Log.d("favor","t = "+t);
        if (t > 1.0f) {
            end();
            return null;
        }
        mAlpha = (int) ((1 - t) * 255);
        if (mResult == null) {
            mResult = new Point();
        }
        mResult.set(bezier(t, mCenter.x, start.x, end.x), bezier(t, mCenter.y, start.y, end.y));
        return mResult;
    }

    private int bezier(float t, int center, int start, int end) {
        float f = 1f - t;
        return (int) (f * f * start + 2 * f * t * center + t * t * end);
    }


    private void evaluateScale(int timeCycle) {
        float scaleEnd = 1.0f;
        if (mScaleX >= scaleEnd) {
            return;
        }
        long scaleDuration = 200L;
        float scaleStart = 0.7f;
        mScaleX = mScaleY = (scaleEnd - scaleStart) * (timeCycle + 0f) / scaleDuration + mScaleX;
    }


    private int rotateEnd = 0;

    private void evaluateRotate(int timeCycle) {
        if (rotateEnd == 0) {
            rotateEnd = mRandom.nextInt(2) == 0 ? mRandom.nextInt(45) : -mRandom.nextInt(45);
        }
        if (mRotate >= rotateEnd) {
            return;
        }

        long rotateDuration = 2500L;
        int rotateStart = 0;
        mRotate = mRotate + (rotateEnd - rotateStart) * Float.intBitsToFloat(timeCycle) / rotateDuration;
    }

    @Override
    protected void onEnd() {
        mBitmap = null;// 不recycle mBitmap，因为其它bean可能需要使用它
    }
}
