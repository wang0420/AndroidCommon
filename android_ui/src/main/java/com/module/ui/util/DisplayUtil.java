package com.module.ui.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

/**
 * @TODO 屏幕适配类工具
 * @version:1.0
 */
public class DisplayUtil {
    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
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
