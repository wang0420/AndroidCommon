package com.module.ui.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.module.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建： dongshuaijun .
 * 日期：2016/6/21.
 * 注释：顶部滑动标签栏
 */
public class SlideTabView extends HorizontalScrollView  {
    //标签布局容器
    private LinearLayout linearLayout;
    //tab容器
    private List<String> list;
    //text容器
    private List<TextView> textViews;
    //当前位置
    private int currIndex = 0;
    //上一次位置
    private int lastIndex = 0;
    //满屏显示数量
    private float maxCount = 3.5f;
    //偏移百分比
    private float offSet;
    //非选中字体颜色
    private int noCurrColor = R.color.color_5C5E66;
    //选中字体颜色
    private int currColor = R.color.color_7F57DB;
    //指示器颜色
   // private int offLineColor = R.color.color_F04A92;
    //背景色
    private int background = R.color.color_FFFFFF;
    //字体大小
    private int txtSize = 18;
    //上下文
    private Context context;

    public SlideTabView(Context context) {
        this(context, null);
    }

    public SlideTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public SlideTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    //初始化View
    private void init() {
        textViews = new ArrayList<>();
        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(params);
        setBackgroundResource(background);
        addView(linearLayout);
    }

    //初始化tab数据
    public void initTab(List<String> list) {
        this.list = list;
        addTab();
    }


    //添加tab 默认选中第一个
    private void addTab() {
        for (int i = 0; i < list.size(); i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            final TextView textView = new TextView(context);
            if (i == 0) {
                textView.setTextColor(getResources().getColor(currColor));
            } else {
                textView.setTextColor(getResources().getColor(noCurrColor));
            }
            layoutParams.setMargins(0, 30, 0, 30);
            layoutParams.width = (int) (((float) getWth()) / maxCount);
            textView.setLayoutParams(layoutParams);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(txtSize);
            textView.setText(list.get(i));

            final int finalI = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currIndex != finalI) { //点击的不是当前tab
                        refresh(finalI);
                    }

                }
            });
            textViews.add(textView);
            linearLayout.addView(textView);
        }
        invalidate();
    }

    private void refresh(int index) {
        for (int i = 0; i < textViews.size(); i++) {
            if (i != index) {
                textViews.get(i).setTextColor(getResources().getColor(noCurrColor));
            } else {
                textViews.get(i).setTextColor(getResources().getColor(currColor));
            }
        }
        scrollToChild(index, 0);
    }


    private void scrollToChild(int position, int offset) {
        int newScrollX = 0;
        if (lastIndex > position) {// 左滑
            newScrollX = linearLayout.getChildAt(position).getLeft() - linearLayout.getChildAt(position).getWidth();
        } else { //右滑
            newScrollX = linearLayout.getChildAt(lastIndex).getLeft() - linearLayout.getChildAt(position).getWidth();
        }
        Log.e("TAG", "newScrollX:" + newScrollX);
        smoothScrollTo(newScrollX, 0);
    }





    private int getWth() {
        WindowManager manager = ((Activity) context).getWindowManager();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public void setMaxCount(float maxCount) {
        this.maxCount = maxCount;
    }

    public void setTxtSize(int txtSize) {
        this.txtSize = txtSize;
    }

    public void setCurrColor(int currColor) {
        this.currColor = currColor;
    }

    public void setNoCurrColor(int noCurrColor) {
        this.noCurrColor = noCurrColor;
    }
}
