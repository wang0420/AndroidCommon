package com.module.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.android.newcommon.utils.DisplayUtil;
import com.module.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangwei on 2019/6/19.
 * 步骤Step
 */

public class StepView extends View {
    private Context mContext;
    List<String> mDatas = new ArrayList<>();
    private Paint circlePaint;
    private Paint circleTextPaint;
    private Paint textPaint;
    private Paint linePaint;
    int stepWidth;
    int textTotalWidth;
    private int width, height;
    int textAndRaduis = 0;

    private int stepPosition = 1;//当前在第几步
    private int lineGapWidth;//线 边距
    private int textGapWidth;//step 字边距
    private int raduis = 10;//圆半径
    private int stepSize = 13;//圆里面的字体大小
    private int stepTextSize = 20;//单位字体大小
    private int lineHeight = 2;
    private int lineWidth = 100;//线宽度
    private int textselectColor = 0xff50b586;
    private int textUnselectColor = 0xff333333;


    public StepView(Context context) {
        this(context, null);
    }

    public StepView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepView(Context context, AttributeSet attrs, int defStyleAttrs) {
        super(context, attrs, defStyleAttrs);
        this.mContext = context;
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.StepView, 0, 0);
        stepPosition = typedArray.getInt(R.styleable.StepView_stepPosition, stepPosition);
        stepTextSize = typedArray.getDimensionPixelSize(R.styleable.StepView_stepTextSize, DisplayUtil.spToPx(mContext, stepTextSize));
        stepSize = typedArray.getDimensionPixelSize(R.styleable.StepView_stepSize, DisplayUtil.spToPx(mContext, stepSize));
        raduis = typedArray.getDimensionPixelSize(R.styleable.StepView_raduis, raduis);
        lineHeight = typedArray.getDimensionPixelSize(R.styleable.StepView_lineHeight, lineHeight);
        lineGapWidth = typedArray.getDimensionPixelSize(R.styleable.StepView_lineMargin, lineGapWidth);
        textGapWidth = typedArray.getDimensionPixelSize(R.styleable.StepView_textMargin, textGapWidth);
        textUnselectColor = typedArray.getColor(R.styleable.StepView_textUnselectColor, textUnselectColor);
        textselectColor = typedArray.getColor(R.styleable.StepView_textselectColor, textselectColor);
        typedArray.recycle();
        initPaint();
    }

    private void initPaint() {
        mDatas.add("第一步");
        mDatas.add("第一步");
        mDatas.add("第一步");
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(stepTextSize);
        textPaint.setColor(textselectColor);


        circleTextPaint = new Paint();
        circleTextPaint.setAntiAlias(true);
        circleTextPaint.setColor(Color.WHITE);
        circleTextPaint.setTextSize(stepSize);


        linePaint = new Paint();
        linePaint.setStrokeWidth(lineHeight);
        linePaint.setAntiAlias(true);
        linePaint.setColor(textUnselectColor);


        circlePaint = new Paint();
        circlePaint.setColor(textUnselectColor);
        circlePaint.setAntiAlias(true);
        circlePaint.setDither(true);
        circlePaint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int mHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);//获取总宽度,是包含padding值
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.w("TAG", "------onMeasure-------");

        switch (mHeightMode) {
            case MeasureSpec.AT_MOST:
                Paint.FontMetrics fm = textPaint.getFontMetrics();
                height = (int) Math.max(2 * raduis, Math.abs(fm.bottom - fm.top)) + getPaddingBottom() + getPaddingTop();
                break;
            case MeasureSpec.EXACTLY:
                height = heightSize + getPaddingTop() + getPaddingBottom();
                break;
            case MeasureSpec.UNSPECIFIED:
                //一般情况下不考虑
                break;
        }

        switch (mWidthMode) {
            case MeasureSpec.AT_MOST:
                width = (int) (getPaddingLeft() + getPaddingRight() + mDatas.size() * 2 * raduis) + lineWidth * (mDatas.size() - 1)
                        + lineGapWidth * (2 * (mDatas.size() - 1)) + textGapWidth * mDatas.size();
                for (int i = 0; i < mDatas.size(); i++) {
                    width += textPaint.measureText(mDatas.get(i));
                }
                break;
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                //一般情况下不考虑
                break;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.w("TAG", "------onLayout-------");

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.w("TAG", "------onDraw-------");
        canvas.drawColor(Color.WHITE);
        initValues();
        width = getMeasuredWidth();
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        Paint.FontMetrics textFm = circleTextPaint.getFontMetrics();
        canvas.save();
        canvas.translate(getPaddingLeft(), height / 2);

        for (int i = 0; i < mDatas.size(); i++) {
            textAndRaduis += textPaint.measureText(mDatas.get(i)) + 2 * raduis;
        }
        lineWidth = (int) ((width - textAndRaduis - getPaddingRight() - getPaddingLeft() - (mDatas.size() - 1) * lineGapWidth * 2 - mDatas.size() * textGapWidth) * 1.0f / (mDatas.size() - 1));

        for (int i = 0; i < mDatas.size(); i++) {
            if (i <= stepPosition) {
                textPaint.setColor(textselectColor);
                linePaint.setColor(textselectColor);
                circlePaint.setColor(textselectColor);
            } else {
                textPaint.setColor(textUnselectColor);
                linePaint.setColor(textUnselectColor);
                circlePaint.setColor(textUnselectColor);
            }

            if (i == 0) {
                canvas.drawCircle(raduis, 0, raduis, circlePaint);
                canvas.drawText((i + 1) + "", raduis - circleTextPaint.measureText((i + 1) + "") / 2, -((textFm.descent + textFm.ascent) / 2), circleTextPaint);
                canvas.drawText(mDatas.get(i), 2 * raduis + textGapWidth, -((fm.descent + fm.ascent) / 2), textPaint);
            } else {
                textTotalWidth += textPaint.measureText(mDatas.get(i - 1));
                stepWidth = (int) (2 * raduis * i + textTotalWidth + lineWidth * (i - 1)) + textGapWidth * i;
                canvas.drawLine(stepWidth + (2 * i - 1) * lineGapWidth, 0, stepWidth + lineWidth + (2 * i - 1) * lineGapWidth, 0, linePaint);
                canvas.drawCircle(stepWidth + lineWidth + raduis + lineGapWidth * 2 * i, 0, raduis, circlePaint);
                canvas.drawText((i + 1) + "", stepWidth + lineWidth + raduis + lineGapWidth * 2 * i - circleTextPaint.measureText((i + 1) + "") / 2, -((textFm.descent + textFm.ascent) / 2), circleTextPaint);
                canvas.drawText(mDatas.get(i), 2 * raduis + stepWidth + lineWidth + lineGapWidth * 2 * i + +textGapWidth, -((fm.descent + fm.ascent) / 2), textPaint);
            }
        }
        canvas.restore();
    }

    private void initValues() {
        textAndRaduis = 0;
        textTotalWidth = 0;
        stepWidth = 0;
    }


    public void setStepData(List<String> mDatas) {
        this.mDatas = mDatas;
    }


    public void setStepPosition(int stepPosition) {
        this.stepPosition = stepPosition;
        invalidate();
    }

}
