package com.module.ui.widget.danmuku.richText;
import android.content.Context;;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;
import android.widget.TextView;

/**
 * @author wangwei
 * @date 2020/8/25.
 */
public class MySpan extends ImageSpan {



    private int textSize = 20;//默认
    private int color = Color.GRAY;
    private TextView mTextView;
    static float textboundhight;
    static float textY;

    /**
     *
     * @param d 接收图片
     * @param tv 通过传入的TextView获取需要的属性
     */
    public MySpan(Drawable d, TextView tv) {
        super(d);
        mTextView = tv;
        textSize = (int) mTextView.getTextSize();

    }


    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y,
                     int bottom, Paint paint) {
        //获取需要设置样式的字符串
        String str = text.subSequence(start, end).toString();
        //得到宽高
        Rect bounds = new Rect();
        //先根据TextView 属性 设置字体大小
        paint.setTextSize(textSize);
        //获得字符串所占空间大小
        paint.getTextBounds(str, 0, str.length(), bounds);
        //得到宽高
        int textHeight = bounds.height();
        int textWidth = bounds.width();

        //设置背景绘制宽高 根据字符串大小扩大一定比例 否则会紧贴字符
        getDrawable().setBounds(0, (int) (top*1.3), (int) (bounds.width()*1.3), bottom);
        //调用父类draw绘制背景
        super.draw(canvas, str, start, end, x, top, y, bottom, paint);
        //绘制文本
        //文本颜色
        paint.setColor(mTextView.getTextColors().getDefaultColor());
        //文本字体
        paint.setTypeface(Typeface.create("normal", Typeface.NORMAL));

        //得到之前设置的背景图的大小
        Rect bounds1 = getDrawable().getBounds();

        //根据背景图算出 字符串居中绘制的位置
        float textX = x + bounds1.width() / 2 - bounds.width() / 2;
        if (textboundhight == 0) {
            textboundhight = bounds.height();
            textY = (bounds1.height()) / 2 + textboundhight / 2 ;
        }
        //绘制字符串
        canvas.drawText(str, textX, textY, paint);

    }
}

