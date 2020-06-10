package com.basemodule.utils;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by rade.chan on 2018/4/25.
 */

public class SoftInputManager {

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(Activity context) {
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (context.getCurrentFocus() != null) {
            manager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }


    public static void hideLoveSoftInput(Activity context) {
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
    }

    public static void hideActivitySoftInput(Activity context) {
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null
                && context.getWindow() != null
                && context.getWindow().getDecorView() != null
                && context.getWindow().getDecorView().getWindowToken() != null) {
            manager.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 控制软键盘的显示隐藏
     */
    public static void showSoftInput(Activity context) {
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public static void showDelayedSoftInput(final Activity context) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                showSoftInput(context);
            }
        }, 600);
    }

    public static void showDelayedSoftInput(final Activity context, long delayMillis) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                showSoftInput(context);
            }
        }, delayMillis);
    }


    public static void hideDelayedSoftInput(final Activity context, long delayMillis) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                showSoftInput(context);
            }
        }, delayMillis);
    }

    /**
     *  解决输入框在底部，键盘收起后，EditText没有回到底部的bug
     *
     * @param rootView 最好是根布局
     */
    public static void hideSoftInputWithEditText(final Activity context, View rootView) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null && rootView != null && rootView.getWindowToken() != null) {
            inputManager.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
            rootView.requestLayout();
        }
    }

}
