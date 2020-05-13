package com.module.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.module.ui.R;

/**
 * Created by wangwei on 2018/9/27.
 */

public class NumberButton extends RelativeLayout implements View.OnClickListener {

    private EditText mCount;
    private ImageView addButton;
    private ImageView subButton;
    private int maxCount = 99;//允许输入的最大值 默认99
    // 如果View是在Java代码里面new的，则调用第一个构造函数
    public NumberButton(Context context) {
        this(context, null);
        Log.w("wangwei", "one");
    }

    // 如果View是在.xml里声明的，则调用第二个构造函数
    // 自定义属性是从AttributeSet参数传进来的
    public NumberButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        Log.w("wangwei", "two");

    }

    // 不会自动调用
    // 一般是在第二个构造函数里主动调用
    // 如View有style属性时
    public NumberButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }



    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.num_button_layout, this);
        addButton = findViewById(R.id.button_add);
        subButton = findViewById(R.id.button_sub);
        mCount = findViewById(R.id.text_count);
        subButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberButton);
        boolean editable = typedArray.getBoolean(R.styleable.NumberButton_editable, true);
        int textSize = typedArray.getDimensionPixelSize(R.styleable.NumberButton_Size, -1);
        int textColor = typedArray.getColor(R.styleable.NumberButton_Color, 0xff000000);
        maxCount = typedArray.getInteger(R.styleable.NumberButton_maxCountNum, 99);
        typedArray.recycle();
        setEditable(editable);
        mCount.setTextColor(textColor);
        if (textSize > 0) {
            mCount.setTextSize(textSize);
        }
    }

    /**
     * 获取数字
     */
    public int getCurrentNumber() {
        try {
            return Integer.parseInt(mCount.getText().toString());
        } catch (NumberFormatException e) {
        }
        mCount.setText("0");
        return 0;
    }

    /**
     * 设置数字
     */
    public void setCurrentNumber(int number) {
        mCount.setText("" + number);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        int count = getCurrentNumber();
        if (id == R.id.button_sub) {
            if (count > 0) {
                mCount.setText("" + (count - 1));
            }
        } else if (id == R.id.button_add) {
            if (count < maxCount) {
                mCount.setText("" + (count + 1));
            }
        } else if (id == R.id.text_count) {
            mCount.setSelection(mCount.getText().toString().length());
        }
    }

    /**
     * 设置输入框能不能点击输入
     */
    private void setEditable(boolean editable) {
        if (editable) {
            mCount.setFocusable(true);
            mCount.setKeyListener(new DigitsKeyListener());
        } else {
            mCount.setFocusable(false);
            mCount.setKeyListener(null);
        }
    }
}
