package com.android.newcommon.utils.anr;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.common.R;
import com.android.common.R2;
import com.android.newcommon.base.BaseTitleActivity;
import com.android.newcommon.floatview.FloatPageManager;
import com.android.newcommon.floatview.PageIntent;
import com.android.newcommon.floatview.RealTimePerformDataFloatPage;
import com.android.newcommon.monitor.PerformanceDataManager;
import com.android.newcommon.monitor.block.core.BlockInfo;
import com.android.newcommon.monitor.block.core.BlockMonitorManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我们都知道Android应用程序只有一个主线程ActivityThread，这个主线程会创建一个Looper(Looper.prepare)，
 * 而Looper又会关联一个MessageQueue，主线程Looper会在应用的生命周期内不断轮询(Looper.loop)，
 * 从MessageQueue取出Message 更新UI。
 * 使用主线程的Looper监测系统发生的卡顿和丢帧
 * https://blog.csdn.net/zhangphil/article/details/81179265?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-3.channel_param&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-3.channel_param
 * https://www.jianshu.com/p/e58992439793
 */

public class ANRActivity extends BaseTitleActivity {

    private final String TAG = "卡顿性能检测";
    private CheckTask mCheckTask = new CheckTask();
    @BindView(R2.id.block)
    Button block;
    @BindView(R2.id.memory)
    Button memory;
    @BindView(R2.id.block_info)
    Button block_info;
    @BindView(R2.id.crash)
    Button crash;

    @BindView(R2.id.panel)
    Button panel;

    private Long time;

    private boolean panelOn = true;

    @OnClick({R2.id.block, R2.id.block_info, R2.id.memory, R2.id.crash, R2.id.panel})
    void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.block) {
            mockBlock();
        } else if (id == R.id.block_info) {
            List<BlockInfo> infos = new ArrayList<>(BlockMonitorManager.getInstance().getBlockInfoList());
            Collections.sort(infos, new Comparator<BlockInfo>() {
                @Override
                public int compare(BlockInfo lhs, BlockInfo rhs) {
                    return Long.valueOf(rhs.time)
                            .compareTo(lhs.time);
                }
            });
            Log.w("TAG", "--i->" + new Gson().toJson(infos));
            Toast.makeText(ANRActivity.this, "所有 blockInfo--長度--" + infos.size(), Toast.LENGTH_LONG).show();

        } else if (id == R.id.memory) {
            for (int i = 0; i < 30000; i++) {
                int d = i * i;

            }

        } else if (id == R.id.crash) {
            String aa = null;
            Log.w("TAG", "==" + aa.length());
        } else if (id == R.id.panel) {
            if (panelOn) {
                panelOn = false;
                PerformanceDataManager.getInstance().stopUploadMonitorData();
                FloatPageManager.getInstance().removeAll(RealTimePerformDataFloatPage.class);
            } else {
                panelOn = true;
                initView();
            }


        }
    }

    @Override
    public void initView() {
        PerformanceDataManager.getInstance().init(getApplicationContext());
        PerformanceDataManager.getInstance().startUploadMonitorData();
        PageIntent pageIntent = new PageIntent(RealTimePerformDataFloatPage.class);
        pageIntent.mode = PageIntent.MODE_SINGLE_INSTANCE;
        FloatPageManager.getInstance().add(pageIntent);


    }

    @Override
    public void initData() {


    }

    @Override
    public int layoutResID() {
        return R.layout.activity_example;
    }


    /**
     * 输出当前异常或及错误堆栈信息。
     */
    private void log() {
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] stackTrace = Looper.getMainLooper().getThread().getStackTrace();
        for (StackTraceElement s : stackTrace) {
            sb.append(s + "\n");
        }

        Log.w("TAG", sb.toString());
    }


    //loop 注册打印
    private void check() {
        Looper.getMainLooper().setMessageLogging(new Printer() {
            private final String START = ">>>>> Dispatching to";
            private final String END = "<<<<< Finished to";

            @Override
            public void println(String s) {
                if (s.startsWith(START)) {
                    time = System.currentTimeMillis();
                    mCheckTask.start();
                } else if (s.startsWith(END)) {
                    mCheckTask.end();
                    Log.w("TAG", System.currentTimeMillis() - time + "-----><" + s);
                }
            }
        });
    }

    private class CheckTask {
        private HandlerThread mHandlerThread = new HandlerThread("卡顿检测");
        private Handler mHandler;

        private final int THREAD_HOLD = 1000;

        public CheckTask() {
            mHandlerThread.start();
            mHandler = new Handler(mHandlerThread.getLooper());
        }

        private Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                log();
            }
        };

        public void start() {
            mHandler.postDelayed(mRunnable, THREAD_HOLD);
        }

        public void end() {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    private void mockBlock() {
        try {
            block.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, 100);
        } catch (Exception e) {

        }
    }
}