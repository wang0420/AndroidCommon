package com.module.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.module.ui.R;

/**
 * Created by wangwei on 2019/5/8.
 * imageView   右上角有消息提醒數字
 */

public class ImgWithNumTipsView extends View {

    private Drawable mDrawable;
    private Paint mTxtPain;
    private String mTipsString = "22";


    public ImgWithNumTipsView(Context context) {
        this(context, null);
    }

    public ImgWithNumTipsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImgWithNumTipsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        mTxtPain = new Paint();
        mTxtPain.setColor(Color.RED);
        mTxtPain.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTxtPain.setTextSize(23);

        mDrawable = getResources().getDrawable(R.drawable.ic_launcher_round);
        Rect drawableRect = new Rect(0, getStringHeight() / 2, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight() + getStringHeight() / 2);
        mDrawable.setBounds(drawableRect);
        requestLayout();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDrawable.draw(canvas);
        int textLeft = mDrawable.getIntrinsicWidth();
        int textTop = 0;
        int textRight = textLeft + getStringWidth(mTipsString);
        int textBottom = getStringHeight();

        Rect textRect = new Rect(textLeft, textTop, textRight, textBottom);
        Paint.FontMetricsInt fontMetrics = mTxtPain.getFontMetricsInt();
        int baseline = (textRect.bottom + textRect.top - fontMetrics.bottom - fontMetrics.top) / 2;

        canvas.drawText(mTipsString, textLeft, baseline, mTxtPain);
    }

    private int getStringWidth(String str) {
        return (int) mTxtPain.measureText(str);
    }

    private int getStringHeight() {
        Paint.FontMetrics fr = mTxtPain.getFontMetrics();
        return (int) Math.ceil(fr.descent - fr.top) + 2;  //ceil() 函数向上舍入为最接近的整数。
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = getStringWidth(mTipsString) + mDrawable.getIntrinsicWidth();
        int heightSize = getStringHeight() / 2 + mDrawable.getIntrinsicWidth();

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.AT_MOST);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.AT_MOST);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }


    public void setTipsNum(int tipsNum) {
        mTipsString = "999+";
        requestLayout();
    }

    public void setTipsText(String text) {
        mTipsString = text;
        requestLayout();
        requestLayout();
    }

    public void setTipsTextColor(int color) {
        mTxtPain.setColor(color);
        invalidate();
    }

    /**
     * @param sizePx 像素为单位
     */
    public void setTipsTextSize(int sizePx) {
        mTxtPain.setTextSize(sizePx);
        requestLayout();
    }


}

