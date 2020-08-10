package com.android.newcommon.utils.anr;

import android.os.Looper;
import android.util.Log;
import android.view.Choreographer;

/**
 * @author wangwei
 * @date 2020/7/23.
 * Android性能：通过Choreographer检测UI丢帧和卡顿
 * https://blog.csdn.net/zhangphil/article/details/81129246
 * https://blog.csdn.net/u013493809/article/details/62215250?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.channel_param&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.channel_param
 * Choreographer.getInstance().postFrameCallback(mFrameCallback);
 * https://blog.csdn.net/lwj_zeal/article/details/88724217?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param
 */
public class FPSFrameCallback implements Choreographer.FrameCallback {

    private static final String TAG = "FPS_TEST";
    private long mLastFrameTimeNanos = 0;
    private long mFrameIntervalNanos;

    public FPSFrameCallback(long lastFrameTimeNanos) {
        mLastFrameTimeNanos = lastFrameTimeNanos;
        //单位是纳秒      FPS =60  即  16ms 1次
        mFrameIntervalNanos = (long) (1000000000 / 60.0);
    }

    @Override
    public void doFrame(long frameTimeNanos) {

        //初始化时间
        if (mLastFrameTimeNanos == 0) {
            mLastFrameTimeNanos = frameTimeNanos;
        }
        final long jitterNanos = frameTimeNanos - mLastFrameTimeNanos;
        if (jitterNanos >= mFrameIntervalNanos) {
            final long skippedFrames = jitterNanos / mFrameIntervalNanos;
            //  如果大于16  一般就标识有丢帧
            if (skippedFrames > 30) {
                log();
                Log.i(TAG, "Skipped " + skippedFrames + " frames!  "
                        + "The application may be doing too much work on its main thread.");
            }
        }
        mLastFrameTimeNanos = frameTimeNanos;
        //注册下一帧回调
        Choreographer.getInstance().postFrameCallback(this);
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

        Log.w(TAG, sb.toString());
    }
}
