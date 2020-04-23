package com.module.ui.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by wangwei on 2019/5/14.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;

    // 在构造函数里进行绘制的初始化，如画笔属性设置等
    public DividerItemDecoration() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        // 画笔颜色设置为红色
    }

    // 重写getItemOffsets（）方法
    // 作用：设置矩形OutRect 与 Item 的间隔区域
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int itemPosition = parent.getChildAdapterPosition(view);
        Log.w("TAG","--getItemOffsets---"+itemPosition);

        // 获得每个Item的位置
        // 第1个Item不绘制分割线
            outRect.set(0, 0, 0, 50);
            // 设置间隔区域为10px,即onDraw()可绘制的区域为10px

    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        Log.w("TAG","--onDrawOver---");

        // 获取RecyclerView的Child view的个数
        int childCount = parent.getChildCount();

        // 遍历每个Item，分别获取它们的位置信息，然后再绘制对应的分割线
        for ( int i = 0; i < childCount; i++ ) {
            // 获取每个Item的位置
            final View child = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(child);


            // 获取布局参数
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            // 设置矩形(分割线)的宽度为10px
            final int mDivider = 10;

            // 根据子视图的位置 & 间隔区域，设置矩形（分割线）的2个顶点坐标(左上 & 右下)

            // 矩形左上顶点 = (ItemView的左边界,ItemView的下边界)
            // ItemView的左边界 = RecyclerView 的左边界 + paddingLeft距离 后的位置
            final int left = parent.getPaddingLeft();
            // ItemView的下边界：ItemView 的 bottom坐标 + 距离RecyclerView底部距离 +translationY
            final int top = child.getBottom() + params.bottomMargin +
                    Math.round(ViewCompat.getTranslationY(child));

            // 矩形右下顶点 = (ItemView的右边界,矩形的下边界)
            // ItemView的右边界 = RecyclerView 的右边界减去 paddingRight 后的坐标位置
            final int right = parent.getWidth() - parent.getPaddingRight();
            // 绘制分割线的下边界 = ItemView的下边界+分割线的高度
            final int bottom = top + mDivider;
            // 通过Canvas绘制矩形（分割线）
            c.drawRect(left,top,right,bottom,mPaint);
        }
    }

    // 重写onDraw（）
    // 作用:在间隔区域里绘制一个矩形，即分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        Log.w("TAG","--onDraw---");


    }
}