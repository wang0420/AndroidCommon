package com.module.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

/**
 * Created by wangwei on 2018/9/27.
 */

public class NumberButton extends RelativeLayout {
    // 如果View是在Java代码里面new的，则调用第一个构造函数
    public NumberButton(Context context) {
        super(context);
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
        Log.w("wangwei", "three");

    }


}
