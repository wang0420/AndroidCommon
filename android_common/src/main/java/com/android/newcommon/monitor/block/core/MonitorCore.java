package com.android.newcommon.monitor.block.core;

import android.os.SystemClock;
import android.util.Printer;
import android.widget.Toast;

import com.android.common.BaseApplication;
import com.android.newcommon.monitor.util.FileManager;
import com.android.newcommon.utils.FileUtils;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * @desc: 检测卡顿的日志类
 */
class MonitorCore implements Printer {
    private static final String TAG = "MonitorCore";
    private static final int BLOCK_THRESHOLD_MILLIS = 200;

    private long mStartTime = 0;
    private long mStartThreadTime = 0;
    private boolean mPrintingStarted = false;

    private StackSampler mStackSampler;


    public MonitorCore() {
        mStackSampler = new StackSampler();
        mStackSampler.init();
    }

    @Override
    public void println(String x) {
        if (!mPrintingStarted) {
            mStartTime = System.currentTimeMillis();
            mStartThreadTime = SystemClock.currentThreadTimeMillis();
            mPrintingStarted = true;
            mStackSampler.startDump();
        } else {
            final long endTime = System.currentTimeMillis();
            long endThreadTime = SystemClock.currentThreadTimeMillis();
            mPrintingStarted = false;
            if (isBlock(endTime)) {
                final ArrayList<String> entries = mStackSampler.getThreadStackEntries(mStartTime, endTime);
                if (entries.size() > 0) {
                    final BlockInfo blockInfo = BlockInfo.newInstance()
                            .setMainThreadTimeCost(mStartTime, endTime, mStartThreadTime, endThreadTime)
                            .setThreadStackEntries(entries)
                            .flushString();
                    String crashPath = FileUtils.getInstance().getAnrFile().getAbsolutePath();
                    BlockMonitorManager.getInstance().notifyBlockEvent(blockInfo);

                    String filePath = FileManager.getBlockDir().getAbsolutePath();
                    FileManager.writeTxtToFile(new Gson().toJson(blockInfo).toString(), filePath);
                    Toast.makeText(BaseApplication.getInstance(), "blockInfo--->" + new Gson().toJson(blockInfo), Toast.LENGTH_LONG).show();
                }
            }
            mStackSampler.stopDump();
        }
    }

    private boolean isBlock(long endTime) {
        return endTime - mStartTime > BLOCK_THRESHOLD_MILLIS;
    }


    public void shutDown() {
        mStackSampler.shutDown();
    }
}
