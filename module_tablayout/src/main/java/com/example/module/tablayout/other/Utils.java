package com.example.module.tablayout.other;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import com.example.module.tablayout.R;



public class Utils {


	public static int getStatusBarHeightPixel(Context context) {
		int result = 0;
		int resourceId =
				context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	public static int getActionBarHeightPixel(Context context) {
		TypedValue tv = new TypedValue();
		if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
			return TypedValue.complexToDimensionPixelSize(tv.data,
					context.getResources().getDisplayMetrics());
		} else if (context.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
			return TypedValue.complexToDimensionPixelSize(tv.data,
					context.getResources().getDisplayMetrics());
		} else {
			return 0;
		}
	}

	public static int getTabHeight(Context context) {
		return context.getResources().getDimensionPixelSize(R.dimen.tab_height);
	}

	public static Point getDisplayDimen(Context context) {
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay();
		Point size = new Point();
		if (Build.VERSION.SDK_INT >= 13) {
			display.getSize(size);
		} else {
			size.x = display.getWidth();
			size.y = display.getHeight();
		}
		return size;
	}

	public static boolean isLand(Context context) {
		return context.getResources().getConfiguration().orientation ==
				Configuration.ORIENTATION_LANDSCAPE;
	}

}
