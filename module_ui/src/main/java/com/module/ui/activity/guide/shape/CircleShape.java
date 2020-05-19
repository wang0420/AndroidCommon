package com.module.ui.activity.guide.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.module.ui.activity.guide.HollowInfo;


/**
 * 圆形
 */
public class CircleShape implements Shape {

    @Override
    public void drawShape(Canvas canvas, Paint paint, HollowInfo info) {
        canvas.drawOval(new RectF(info.targetBound.left,
                info.targetBound.top, info.targetBound.right, info.targetBound.bottom), paint);
    }
}
