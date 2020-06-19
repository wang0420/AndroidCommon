package com.basemodule.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.basemodule.R;

import androidx.annotation.RequiresApi;


/**
 * @TODO 屏幕适配类工具
 * @version:1.0
 */
public class DisplayUtil {
    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     */
    public static int pxToDp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    public static int dpToPx(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int pxToSp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */

    public static int spToPx(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    private static int statusBarHeight;
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
        return getStatusBarHeight(context) + context.getResources().getDimensionPixelSize(R.dimen.dp40);
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

    /**
     * 获取屏幕高宽参数
     */
    public static void getDisplay(Context mContext) {
        StringBuilder s = new StringBuilder();
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        s.append('\n');
        s.append("widthPixels=").append(dm.widthPixels).append('\n');
        s.append("heightPixels=").append(dm.heightPixels).append('\n');
        s.append("density=").append(dm.density).append('\n');
        s.append("densityDpi=").append(dm.densityDpi).append('\n');
        s.append("scaledDensity=").append(dm.scaledDensity).append('\n');
        s.append("xdpi=").append(dm.xdpi).append('\n');//水平方向的真实密度
        s.append("ydpi=").append(dm.ydpi).append('\n');
        s.append("ydpi=").append(dm.ydpi).append('\n');
        s.append("smallestScreenWidth=").append(mContext.getResources().getConfiguration().smallestScreenWidthDp).append('\n');
        Log.w("TAG", "-----" + s);

    }

}
