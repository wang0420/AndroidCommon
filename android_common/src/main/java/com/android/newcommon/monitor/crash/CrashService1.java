package com.android.newcommon.monitor.crash;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.android.newcommon.monitor.util.ServiceUtils;

/**
 * @author wangwei
 * @date 2020/8/28.
 * * Date: 2018/10/11 4:59 PM
 * * ModifyTime: 4:59 PM
 * * Description:奇怪的魅族的手机崩溃的时候直接调用startActivity会出现卡死，因此放到service里
 */

public class CrashService1 extends IntentService {
    private static final String TAG = "CrashService1";

    public CrashService1() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //在这里通过intent携带的数据，开进行任务的操作。
        Log.d(TAG, "onHandleIntent: " + Thread.currentThread().getName());
        String crashInfo = intent.getStringExtra(ServiceUtils.FLAG_INFO);
        Intent crashIntent = new Intent(getApplication(), CrashPanelActivity.class);
        crashIntent.putExtra(ServiceUtils.FLAG_INFO, crashInfo);
        crashIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(crashIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
