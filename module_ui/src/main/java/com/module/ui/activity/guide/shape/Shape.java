package com.module.ui.activity.guide.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.module.ui.activity.guide.HollowInfo;

public interface Shape {

    /**
     * 画你想要的任何形状
     */
    void drawShape(Canvas canvas, Paint paint, HollowInfo info);

}
