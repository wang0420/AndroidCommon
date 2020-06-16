package com.module.ui.widget.favor;

import android.animation.TypeEvaluator;
import android.graphics.Point;

/**
 * 贝塞尔曲线
 *
 */

class BezierEvaluator3 implements TypeEvaluator<Point> {
    private Point mCenter;
    private Point mResult;

    public BezierEvaluator3(Point center) {
        mCenter = center;
        mResult = new Point();
    }

    @Override
    public Point evaluate(float t, Point start, Point end) {
        mResult.set(bezier(t, mCenter.x, start.x, end.x), bezier(t, mCenter.y, start.y, end.y));
        return mResult;
    }

    private int bezier(float t, int center, int start, int end) {
        float f = 1f - t;
        return (int) (f * f * start + 2 * f * t * center + t * t * end);
    }
}
