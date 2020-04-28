package com.module.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.module.ui.R;

import java.util.ArrayList;
import java.util.List;



public class StepInfoView extends View {
    private Paint paint;
    private Paint textPaint;
    private Paint circlePaint;
    private Paint linePaint;

    List<String> mDatas;
    private int stepPostion = 0;
    private int lineWidth = 160;
    private float raduis = 0;
    int stepWidth;
    int textTotalWidth;
    private int width;
    int textAndRaduis = 0;

    private int lineGapWidth;
    private int textGapWidth;
    private int height = 0;

    public StepInfoView(Context context) {
        super(context);

    }

    public StepInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDatas = new ArrayList<>();
        paint = new Paint();
        paint.setAntiAlias(true);

        paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.dp22));
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.dp18));

        linePaint = new Paint();
        linePaint.setStrokeWidth(getResources().getDimensionPixelOffset(R.dimen.dp2));
        linePaint.setColor(Color.parseColor("#D8D8D8"));

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setDither(true);
        circlePaint.setStyle(Paint.Style.FILL);
        raduis = getResources().getDimensionPixelSize(R.dimen.dp11);
        lineGapWidth = getResources().getDimensionPixelSize(R.dimen.dp10);
        textGapWidth = getResources().getDimensionPixelSize(R.dimen.dp2);
    }

    public StepInfoView(Context context, AttributeSet attrs, int defStyleAttrs) {
        super(context, attrs, defStyleAttrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int mHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int mMeasureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int mMeasureHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (mWidthMode == MeasureSpec.AT_MOST) {// wrap_content
            width = (int) (getPaddingLeft() + getPaddingRight() + mDatas.size() * 2 * raduis) + lineWidth * (mDatas.size() - 1)
                    + lineGapWidth * (2 * (mDatas.size() - 1)) + textGapWidth * mDatas.size();
            for (int i = 0; i < mDatas.size(); i++) {
                width += paint.measureText(mDatas.get(i));
            }
        } else if (mWidthMode == MeasureSpec.EXACTLY) {
            width = mMeasureWidth;
        }

        if (mHeightMode == MeasureSpec.AT_MOST) {// wrap_content
            Paint.FontMetrics fm = paint.getFontMetrics();
            height = (int) Math.max(2 * raduis, Math.abs(fm.bottom - fm.top)) + getPaddingBottom() + getPaddingTop();

        } else if (mHeightMode == MeasureSpec.EXACTLY) {
            height = mMeasureHeight;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        initValues();
        width = getMeasuredWidth();
        Paint.FontMetrics fm = paint.getFontMetrics();
        Paint.FontMetrics textFm = textPaint.getFontMetrics();
        canvas.save();
        canvas.translate(getPaddingLeft(), height / 2);
        for (int i = 0; i < mDatas.size(); i++) {
            textAndRaduis += paint.measureText(mDatas.get(i)) + 2 * raduis;
        }
        lineWidth = (int) ((width - textAndRaduis - getPaddingRight() - getPaddingLeft() - (mDatas.size() - 1) * lineGapWidth * 2 - mDatas.size() * textGapWidth) * 1.0f / (mDatas.size() - 1));
        for (int i = 0; i < mDatas.size(); i++) {
            if (i <= stepPostion) {
                paint.setColor(Color.parseColor("#F063B9"));
                linePaint.setColor(Color.parseColor("#F063B9"));
                circlePaint.setColor(Color.parseColor("#F063B9"));
                if (i == 0) {
                    canvas.drawCircle(raduis, 0, raduis, circlePaint);
                    canvas.drawText((i + 1) + "", raduis - textPaint.measureText((i + 1) + "") / 2, -((textFm.descent + textFm.ascent) / 2), textPaint);
                    canvas.drawText(mDatas.get(i), 2 * raduis + textGapWidth, -((fm.descent + fm.ascent) / 2), paint);
                }
            } else {
                paint.setColor(Color.parseColor("#666666"));
                linePaint.setColor(Color.parseColor("#D8D8D8"));
                circlePaint.setColor(Color.parseColor("#999999"));
            }
            if (i != 0) {
                textTotalWidth += paint.measureText(mDatas.get(i - 1));
                stepWidth = (int) (2 * raduis * i + textTotalWidth + lineWidth * (i - 1)) + textGapWidth * i;
                canvas.drawLine(stepWidth + (2 * i - 1) * lineGapWidth, 0, stepWidth + lineWidth + (2 * i - 1) * lineGapWidth, 0, linePaint);
                canvas.drawCircle(stepWidth + lineWidth + raduis + lineGapWidth * 2 * i, 0, raduis, circlePaint);
                canvas.drawText((i + 1) + "", stepWidth + lineWidth + raduis + lineGapWidth * 2 * i - textPaint.measureText((i + 1) + "") / 2, -((textFm.descent + textFm.ascent) / 2), textPaint);
                canvas.drawText(mDatas.get(i), 2 * raduis + stepWidth + lineWidth + lineGapWidth * 2 * i + +textGapWidth, -((fm.descent + fm.ascent) / 2), paint);
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

    public void setStepPostion(int stepPostion) {
        this.stepPostion = stepPostion;
        invalidate();
    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());
    }
}
