package com.basemodule.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.basemodule.R;

import androidx.annotation.RequiresApi;

/**
 * User: newSalton@outlook.com
 * Date: 2019/11/5 14:01
 * ModifyTime: 14:01
 * Description:
 */
public class DensityUtils {
    private static int statusBarHeight;

    /**
     * dp to px, return by int
     *
     * @param context Context
     * @param dpValue value in dp
     * @return value in px, by int
     */
    public static int dp2px(Context context, float dpValue) {
        return (int) (dp2pxF(context, dpValue) + 0.5f);
    }

    /**
     * dp to px, return by float
     *
     * @param context Context
     * @param dpValue value in dp
     * @return value in px, by float
     */
    public static float dp2pxF(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale;
    }

    /**
     * px to dp, return by int
     *
     * @param context Context
     * @param pxValue value in px
     * @return value in dp, by int
     */
    public static int px2dp(Context context, float pxValue) {
        return (int) (px2dpF(context, pxValue) + 0.5f);
    }

    /**
     * px to dp, return by float
     *
     * @param context Context
     * @param pxValue value in px
     * @return value in dp
     */
    public static float px2dpF(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return pxValue / scale;
    }

    /**
     * px to sp, return by int
     *
     * @param context Context
     * @param pxValue value in px
     * @return value in sp
     */
    public static int px2sp(Context context, float pxValue) {
        return (int) (px2spF(context, pxValue) + 0.5f);
    }

    /**
     * px to sp, return by float
     *
     * @param context Context
     * @param pxValue value in px
     * @return value in sp
     */
    public static float px2spF(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return pxValue / fontScale;
    }

    /**
     * sp to px, return by int
     *
     * @param context Context
     * @param spValue value in sp
     * @return value in px
     */
    public static int sp2px(Context context, float spValue) {
        return (int) (sp2pxF(context, spValue) + 0.5f);
    }

    /**
     * sp to px, return by float
     *
     * @param context Context
     * @param spValue value in sp
     * @return value in px
     */
    public static float sp2pxF(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return spValue * fontScale + 0.5f;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context Context
     * @return 屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context Context
     * @return 屏幕高度
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕高度（包含虚拟键）
     *
     * @param context
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int getDisplayHeight(Context context) {
        int height;
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            wm.getDefaultDisplay().getRealMetrics(dm);
            height = dm.heightPixels;
        } else {
            height = getScreenHeight(context) + getStatusBarHeight(context);
        }
        return height;
    }

    /**
     * 获取状态栏高度
     *
     * @param context Context
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        if (statusBarHeight > 0) {
            return statusBarHeight;
        }
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 沉浸式情况下titlebar的高度=状态栏的高度+titlebar的高度
     */
    public static int getTitlebarHeight(Context context) {
        return getStatusBarHeight(context) + context.getResources().getDimensionPixelSize(R.dimen.dp_40);
    }

    /**
     * 获取导航栏高度
     *
     * @param context Context
     * @return 导航栏高度
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelOffset(identifier);
    }
}
