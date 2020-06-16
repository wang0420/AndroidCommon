package com.module.ui.widget.favor;

/**
 * @author chenzijin
 * @version 1.0.0
 * @date 2019/6/24
 */

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * 贝塞尔变换实体
 *

 */

public abstract class BezierBean3 {
    protected Point mStart;
    protected Point mEnd;
    protected Point mCoordinate;
    protected int mAlpha;
    protected float mScaleX, mScaleY;
    protected float mRotate;
    protected Matrix mMatrix;

    private boolean mEnded;
    private ValueAnimator mTranslateAnimator;
    private ValueAnimator mScaleAnimator;
    private ValueAnimator mRotateAnimator;
    private ValueAnimator mAlphaAnimator;

    private BezierController3 mController;

    public BezierBean3(BezierController3 controller, Point start, Point end) {
        mController = controller;
        mStart = start;
        mEnd = end;
        mCoordinate = mStart;

        mAlpha = 255;
        mScaleX = 0.7f;
        mScaleY = 0.7f;
        mRotate = 0;
        mMatrix = new Matrix();
    }

    public boolean isEnded() {
        return mEnded;
    }

    public final void start() {
        onStart();

        if (mTranslateAnimator != null) {
            mTranslateAnimator.start();
        }
        if (mScaleAnimator != null) {
            mScaleAnimator.start();
        }
        if (mRotateAnimator != null) {
            mRotateAnimator.start();
        }
        if (mAlphaAnimator != null) {
            mAlphaAnimator.start();
        }
    }

    protected abstract void onStart();

    protected abstract void onDraw(Canvas canvas, Paint paint);

    protected abstract void onEnd();

    protected final void translate(Point center, long duration, TimeInterpolator interpolator,
                                   ValueAnimator.AnimatorUpdateListener listener) {
        mTranslateAnimator = ValueAnimator.ofObject(new BezierEvaluator3(center), mStart, mEnd);
        setAnimator(mTranslateAnimator, duration, interpolator, listener);
    }

    protected final void scale(float from, float to, long duration, TimeInterpolator interpolator,
                               ValueAnimator.AnimatorUpdateListener listener) {
        mScaleAnimator = ValueAnimator.ofFloat(from, to);
        setAnimator(mScaleAnimator, duration, interpolator, listener);
    }

    protected final void rotate(float from, float to, long duration, TimeInterpolator interpolator,
                                ValueAnimator.AnimatorUpdateListener listener) {
        mRotateAnimator = ValueAnimator.ofFloat(from, to);
        setAnimator(mRotateAnimator, duration, interpolator, listener);
    }

    protected final void alpha(float from, float to, long duration, TimeInterpolator interpolator,
                               ValueAnimator.AnimatorUpdateListener listener) {
        mAlphaAnimator = ValueAnimator.ofFloat(from, to);
        setAnimator(mAlphaAnimator, duration, interpolator, listener);
    }

    protected final void end() {
        mEnded = true;
        if (mTranslateAnimator != null) {
            mTranslateAnimator.cancel();
            mTranslateAnimator = null;
        }
        if (mScaleAnimator != null) {
            mScaleAnimator.cancel();
            mScaleAnimator = null;
        }
        if (mRotateAnimator != null) {
            mRotateAnimator.cancel();
            mRotateAnimator = null;
        }
        if (mAlphaAnimator != null) {
            mAlphaAnimator.cancel();
            mAlphaAnimator = null;
        }
        onEnd();
        if (mController != null) {
            mController.endBean(this);
            mController = null;
        }
    }

    private void setAnimator(ValueAnimator animator, long duration, TimeInterpolator interpolator,
                             ValueAnimator.AnimatorUpdateListener listener) {
        animator.setDuration(duration);
        if (interpolator != null) {
            animator.setInterpolator(interpolator);
        }
        if (listener != null) {
            animator.addUpdateListener(listener);
        }
    }
}
