package com.moudlea.jetpackStudy.work;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.moudlea.R;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

/**
 * workmanager使用演示
 * Author: zhiwei.
 * Date: 2018/11/6 0006,13:51.
 */
public class WorkActivity extends AppCompatActivity {


    //类似于intent的bundle
    Data data = new Data.Builder()
            .putString("name", "wangwei")
            .putInt("age", 111)
            .build();


    //这里workmanager的request有个高级用法，就是添加环境约束 ，比如网络、电量等  符合这几个条件才执行
    Constraints constraints = new Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)//联网状态
            .setRequiresBatteryNotLow(true)//低电量不操作
            .setRequiresCharging(true)//充电时候才开始
//            .setRequiresDeviceIdle(true)//待机状态下才执行，api 23 以上
            .setRequiresStorageNotLow(true)//存储空间不能太小
            .build();


    //2、创建一个workrequest 一次性任务,这里有onetime,还有个PeriodicWorkRequest
    private OneTimeWorkRequest workRequest1 = new OneTimeWorkRequest.Builder(MyWorker.class)
            .setConstraints(constraints)//添加约束
            .setInputData(new Data.Builder()
                    .putString("name", "wangwei")
                    .putInt("age", 111)
                    .build())//传递data到worker中
            .build();

    private OneTimeWorkRequest workRequest2 = new OneTimeWorkRequest.Builder(MyWorker.class)
            .setConstraints(constraints)//添加约束
            .setInputData(new Data.Builder()
                    .putString("name", "wangwei")
                    .putInt("age", 222)
                    .build())//传递data到worker中
            .build();

    private OneTimeWorkRequest workRequest3 = new OneTimeWorkRequest.Builder(MyWorker.class)
            .setConstraints(constraints)//添加约束
            .setInputData(new Data.Builder()
                    .putString("name", "wangwei")
                    .putInt("age", 333)
                    .build())//传递data到worker中
            .build();

    // 创建一个workrequest 周期性任务 worker的角色定位用于特殊的任务操作，
    // 可以脱离于本App的进程，所以这里的定期任务，做了最小限制，间隔至少15分钟，最小弹性伸缩时间为5分钟
    private PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest
            .Builder(MyWorker.class, 15, TimeUnit.MINUTES)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 20, TimeUnit.MINUTES)
            .build();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        //3、加入任务管理，但不是执行，执行的代码稍后队列里面任务（还在等待调度 未执行的那种）不能超过100个，不然会crash，这是workmanager代码的限制
        WorkManager.getInstance().enqueue(periodicWorkRequest);
        //4、通过workRequest的唯一标记id，来操作request，并获取返回数据

        WorkManager.getInstance().beginWith(workRequest1)
                .then(workRequest2)
                .then(workRequest3)
                .enqueue();

        //这里因为在oncreate中执行，会先与work执行，toast会弹出未执行work的空结果，
        // work变化后，还会显示出成功后的结果。这是因为observe监测worker的status变化
        // enqueued、RUNNING、successed、retry、failure等
        WorkManager.getInstance().getWorkInfoByIdLiveData(periodicWorkRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workStatus) {
                        //接收从worker中返回的任务结果,最好在这里判断status为success再做具体操作
                        if (workStatus.getState() == WorkInfo.State.SUCCEEDED) {
                            Data data = workStatus.getOutputData();
                            String result = data.getString("result");
                            int status = data.getInt("status", 0);
                            Toast.makeText(WorkActivity.this, "result: " + result + " status: " + status, Toast.LENGTH_SHORT).show();
                            Log.w("TAG", "workStatus: " + workStatus.getState().name());
                        }
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        WorkManager.getInstance().cancelWorkById(workRequest1.getId());
    }
}
