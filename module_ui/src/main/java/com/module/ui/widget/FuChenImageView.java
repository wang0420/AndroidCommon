package com.module.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by wangwei on 2019/5/8.
 * ImageView表面有一个浮沉效果
 */

@SuppressLint("AppCompatCustomView")
public class FuChenImageView extends ImageView {

    private int moreNum = 0;              //显示更多的数量
    private int maskColor = 0x73000000;   //默认的遮盖颜色
    private float textSize = 55;          //显示文字的大小单位sp
    private int textColor = 0xFFFFFFFF;   //显示文字的颜色
    private TextPaint textPaint;              //文字的画笔
    private String msg = "+2";                  //要绘制的文字


    public FuChenImageView(Context context) {
        this(context, null);
    }

    public FuChenImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FuChenImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        textPaint = new TextPaint();
        textPaint.setTextAlign(Paint.Align.CENTER);  //文字居中对齐
        textPaint.setAntiAlias(true);                //抗锯齿
        textPaint.setTextSize(textSize);             //设置文字大小
        textPaint.setColor(textColor);               //设置文字颜色
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    Canvas s = new Canvas();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (moreNum > 0) {
            canvas.drawColor(maskColor);
            int width = getWidth();
            int height = getHeight();
            canvas.drawText(msg, getWidth() / 2, getHeight() / 2, textPaint);
            Log.w("TAG", "--width----" + width);
            Log.w("TAG", "---height---" + height);
            float baseY = getHeight() / 2 - (textPaint.ascent() + textPaint.descent()) / 2;
            Log.w("TAG", "---baseY---" + baseY);

            Log.w("TAG", "---height-111--" + textPaint.ascent() + textPaint.descent());


        }
    }


    public void setMoreNum(int moreNum) {
        this.moreNum = moreNum;
        msg = "+" + moreNum;
        invalidate();
    }

    public int getMaskColor() {
        return maskColor;
    }

    public void setMaskColor(int maskColor) {
        this.maskColor = maskColor;
        invalidate();
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        textPaint.setTextSize(textSize);
        invalidate();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        textPaint.setColor(textColor);
        invalidate();
        requestLayout();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * ，onAttachedToWindow是在第一次onDraw前调用的。
     * 也就是我们写的View在没有绘制出来时调用的，但只会调用一次。
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.w("TAG", "---onAttachedToWindow---");

    }

    /**
     * 。也就是我们销毁View的时候。我们写的这个View不再显示。
     * <p>
     * 这时我们就在这个方法做一些收尾工作，如：取消广播注册等等。
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.w("TAG", "---onDetachedFromWindow---");

    }
}
