package com.android.newcommon.utils.anr;


import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.widget.Button;

import com.android.common.R;
import com.android.newcommon.utils.FileUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 使用主线程的Looper监测系统发生的卡顿和丢帧
 * https://blog.csdn.net/zhangphil/article/details/81179265?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-3.channel_param&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-3.channel_param
 */

public class ANRActivity extends AppCompatActivity {

    private final String TAG = "卡顿性能检测";
    private CheckTask mCheckTask = new CheckTask();
    private Button go;
    private Long time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        check();
        FileUtils.getInstance().getAnrFile().getAbsolutePath();
        setContentView(R.layout.activity_example);
        go = findViewById(R.id.go);
        go.setText("主线程模拟卡顿");
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uiLongTimeWork();
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    private void uiLongTimeWork() {
        try {
            Log.w("TAG", "----->uiLongTimeWork<");

            Thread.sleep(5000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
}