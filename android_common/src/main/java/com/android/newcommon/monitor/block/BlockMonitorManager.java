package com.android.newcommon.monitor.block;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.common.BaseApplication;
import com.android.common.R;
import com.android.common.net.NetActivity;
import com.android.newcommon.monitor.util.NotificationUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

/**
 * @desc: 卡顿检测管理类
 */
public class BlockMonitorManager {
    private static final String TAG = "BlockMonitorManager";
    private static final int MAX_SIZE = 50;


    private static class Holder {
        private static BlockMonitorManager INSTANCE = new BlockMonitorManager();
    }

    private boolean mIsRunning;
    private MonitorCore mMonitorCore;
    private Context mContext;
    private List<BlockInfo> mBlockInfoList = Collections.synchronizedList(new ArrayList<BlockInfo>());
    private OnBlockInfoUpdateListener mOnBlockInfoUpdateListener;


    public static BlockMonitorManager getInstance() {
        return Holder.INSTANCE;
    }

    private BlockMonitorManager() {

    }

    public void start(Context context) {
        if (mIsRunning) {
            LogHelper.i(TAG, "start when manager is running");
            return;
        }
        if (context == null) {
            LogHelper.e(TAG, "start fail, context is null");
            return;
        }
        mContext = context.getApplicationContext();
        if (mMonitorCore == null) {
            mMonitorCore = new MonitorCore();
        }
        mIsRunning = true;
        Looper.getMainLooper().setMessageLogging(mMonitorCore);
    }

    public boolean isRunning() {
        return mIsRunning;
    }

    public void stop() {
        if (!mIsRunning) {
            LogHelper.i(TAG, "stop when manager is not running");
            return;
        }
        Looper.getMainLooper().setMessageLogging(null);
        if (mMonitorCore != null) {
            mMonitorCore.shutDown();
            mMonitorCore = null;
        }
        NotificationUtils.cancelNotification(mContext, NotificationUtils.ID_SHOW_BLOCK_NOTIFICATION);
        mIsRunning = false;
        mContext = null;
    }

    public void setOnBlockInfoUpdateListener(OnBlockInfoUpdateListener onBlockInfoUpdateListener) {
        mOnBlockInfoUpdateListener = onBlockInfoUpdateListener;
    }

    public void notifyBlockEvent(BlockInfo blockInfo) {
        blockInfo.concernStackString = BlockCanaryUtils.concernStackString(mContext, blockInfo);
        blockInfo.time = System.currentTimeMillis();
        if (!TextUtils.isEmpty(blockInfo.concernStackString)) {
            showNotification(blockInfo);
            if (mBlockInfoList.size() > MAX_SIZE) {
                mBlockInfoList.remove(0);
            }
            mBlockInfoList.add(blockInfo);
            if (mOnBlockInfoUpdateListener != null) {
                mOnBlockInfoUpdateListener.onBlockInfoUpdate(blockInfo);
            }
        }

    }

    private void showNotification(BlockInfo info) {
        String contentTitle = mContext.getString(R.string.dk_block_class_has_blocked, info.timeStart);
        String contentText = mContext.getString(R.string.dk_block_notification_message);
        Toast.makeText(BaseApplication.getInstance(), "app卡顿了", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mContext, NetActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("---", "111");
        intent.putExtra("ss", true);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 1, intent, FLAG_UPDATE_CURRENT);
        NotificationUtils.setInfoNotification(mContext, NotificationUtils.ID_SHOW_BLOCK_NOTIFICATION,
                contentTitle, contentText, contentText, pendingIntent);
    }

    public List<BlockInfo> getBlockInfoList() {
        return mBlockInfoList;
    }
}
