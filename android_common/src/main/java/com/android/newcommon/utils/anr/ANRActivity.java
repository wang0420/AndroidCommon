package com.android.newcommon.utils.anr;


import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.common.R;
import com.android.newcommon.monitor.block.core.BlockInfo;
import com.android.newcommon.monitor.block.core.BlockMonitorManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 使用主线程的Looper监测系统发生的卡顿和丢帧
 * https://blog.csdn.net/zhangphil/article/details/81179265?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-3.channel_param&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-3.channel_param
 */

public class ANRActivity extends AppCompatActivity {

    private final String TAG = "卡顿性能检测";
    private CheckTask mCheckTask = new CheckTask();
    private Button block, button1, block_info, crash;
    private Long time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        //   LiveMonitorUtils.startLiveMonitor(this, findViewById(R.id.content));// 显示
        block = findViewById(R.id.block);
        button1 = findViewById(R.id.button1);
        block_info = findViewById(R.id.block_info);
        crash = findViewById(R.id.crash);
        // BlockMonitorManager.getInstance().stop();
        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mockBlock();
            }
        });

        block_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 3000; i++) {
                    int d = i * i;

                }
            }
        });
        crash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aa = null;
                Log.w("TAG", "==" + aa.length());
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
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
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, 100);
        } catch (Exception e) {

        }
    }
}