package com.android.newcommon.monitor;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.common.R;
import com.android.newcommon.utils.DisplayUtil;

import java.text.DecimalFormat;

import androidx.core.content.ContextCompat;

/**
 * 直播监控显示
 */

public class LiveMonitorUtils {
    private static AppMonitor mAppMonitor;

    private static LinearLayout layoutLog;
    private static TextView tvFps, tvCpu, tvMemory;
    private static final DecimalFormat decimal = new DecimalFormat("#.00");
    ;

    /**
     * 打开性能显示
     *
     * @param context
     */
    public static void startLiveMonitor(Context context, LinearLayout layoutLog) {
        if (mAppMonitor == null) {
            mAppMonitor = new AppMonitor();
            // layoutLog = new LinearLayout(context);
            layoutLog.setBackgroundColor(ContextCompat.getColor(context, R.color.black));
            layoutLog.removeAllViews();
            int dp25 = DisplayUtil.dpToPx(context, 20);
            tvFps = new TextView(context);
            tvFps.setTextColor(ContextCompat.getColor(context, R.color.white));
            tvFps.setTextSize(16);
            layoutLog.addView(tvFps);

            tvCpu = new TextView(context);
            tvCpu.setTextColor(ContextCompat.getColor(context, R.color.white));
            tvCpu.setTextSize(16);
            layoutLog.addView(tvCpu);

            tvMemory = new TextView(context);
            tvMemory.setTextColor(ContextCompat.getColor(context, R.color.white));
            tvMemory.setTextSize(16);
            layoutLog.addView(tvMemory);

        }

        mAppMonitor.startLogMonitorFps(context, new MonitorCallback() {
            @Override
            public void framePerSecond(double fps) {
                if (tvFps != null) {
                    Log.w("wangwei", "fps--" + fps);
                    tvFps.setText("fps: " +decimal.format(fps));
                }
            }

            @Override
            public void cpuRate(final float rate) {
                Log.w("wangwei", "cpuRate--" + rate);
                if (tvCpu != null) {
                    tvCpu.post(new Runnable() {
                        @Override
                        public void run() {
                            if (tvCpu != null) {
                                //tvCpu.setText(cpuDecimal.format(rate));
                            }
                        }
                    });
                }
            }

            @Override
            public void appMemory(final long memory) {
                if (tvMemory != null) {
                    tvMemory.post(new Runnable() {
                        @Override
                        public void run() {
                            if (tvMemory != null) {
                                tvMemory.setText("memory: " + decimal.format(MemoryUtil.getRunningMemory(context)) + "M");
                            }
                        }
                    });
                }
            }
        });
    }


    /**
     * 开始性能检测
     *
     * @param callback
     */
    public static void startAppMonitorCheck(CheckMonitorCallback callback) {
        if (mAppMonitor == null) {
            mAppMonitor = new AppMonitor();
        }
        mAppMonitor.startMonitorFps(callback);
    }


}
