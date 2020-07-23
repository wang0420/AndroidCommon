package com.android.newcommon.widget.tab_indicator;

/**
 * Created by Wenjing.Tang
 * on 2019/1/2
 */
import android.content.Context;
import android.content.res.TypedArray;

import com.android.common.R;


class ThemeUtils {

    private static final int[] APPCOMPAT_CHECK_ATTRS = {
            R.attr.colorPrimary
    };

    static void checkAppCompatTheme(Context context) {
        TypedArray a = context.obtainStyledAttributes(APPCOMPAT_CHECK_ATTRS);
        final boolean failed = !a.hasValue(0);
        if (a != null) {
            a.recycle();
        }
        if (failed) {
            throw new IllegalArgumentException("You need to use a Theme.AppCompat theme "
                    + "(or descendant) with the design library.");
        }
    }
}
