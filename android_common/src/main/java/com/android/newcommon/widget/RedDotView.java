package com.android.newcommon.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;


import com.android.common.R;

import androidx.annotation.Px;
import androidx.appcompat.widget.AppCompatTextView;


/**
 * 用于需要圆角矩形框背景的TextView的情况,减少直接使用TextView时引入的shape资源文件
 */
public class RedDotView extends AppCompatTextView {

    private int backgroundColor;
    private int cornerRadius;
    private int strokeWidth;
    private int strokeColor;
    private boolean isRadiusHalfHeight;
    private boolean isWidthHeightEqual;
    private int mDrawableResourceId;

    public RedDotView(Context context) {
        this(context, null);
    }

    public RedDotView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RedDotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttributes(context, attrs);

        setIncludeFontPadding(false);
        setGravity(Gravity.CENTER);
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RedDotView);
        backgroundColor = ta.getColor(R.styleable.RedDotView_backgroundColor, Color.TRANSPARENT);
        cornerRadius = ta.getDimensionPixelSize(R.styleable.RedDotView_cornerRadius, 0);
        strokeWidth = ta.getDimensionPixelSize(R.styleable.RedDotView_strokeWidth, 0);
        strokeColor = ta.getColor(R.styleable.RedDotView_strokeColor, Color.TRANSPARENT);
        isRadiusHalfHeight = ta.getBoolean(R.styleable.RedDotView_isRadiusHalfHeight, false);
        isWidthHeightEqual = ta.getBoolean(R.styleable.RedDotView_isWidthHeightEqual, false);
        mDrawableResourceId = ta.getResourceId(R.styleable.RedDotView_backgroundDrawable, 0);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isWidthHeightEqual() && getWidth() > 0 && getHeight() > 0) {
            int max = Math.max(getWidth(), getHeight());
            int measureSpec = MeasureSpec.makeMeasureSpec(max, MeasureSpec.EXACTLY);
            super.onMeasure(measureSpec, measureSpec);
            return;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (isRadiusHalfHeight()) {
            setCornerRadius(getHeight() / 2);
        } else {
            setDrawable();
        }
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        setDrawable();
    }

    public void setCornerRadius(@Px int cornerRadius) {
        this.cornerRadius = cornerRadius;
        setDrawable();
    }

    public void setStrokeWidth(@Px int strokeWidth) {
        this.strokeWidth = strokeWidth;
        setDrawable();
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        setDrawable();
    }

    public void setIsRadiusHalfHeight(boolean isRadiusHalfHeight) {
        this.isRadiusHalfHeight = isRadiusHalfHeight;
        setDrawable();
    }

    public void setIsWidthHeightEqual(boolean isWidthHeightEqual) {
        this.isWidthHeightEqual = isWidthHeightEqual;
        setDrawable();
    }

    public void setDrawableResourceId(int drawableResourceId) {
        this.mDrawableResourceId = drawableResourceId;
        setDrawable();
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public boolean isRadiusHalfHeight() {
        return isRadiusHalfHeight;
    }

    public boolean isWidthHeightEqual() {
        return isWidthHeightEqual;
    }

    public int getDrawableResourceId() {
        return mDrawableResourceId;
    }

    private void setDrawable() {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(backgroundColor);
        gd.setCornerRadius(cornerRadius);
        gd.setStroke(strokeWidth, strokeColor);
        setBackground(gd);
        if (mDrawableResourceId != 0) {
            setBackgroundDrawable(getResources().getDrawable(mDrawableResourceId));
        }
    }
}
