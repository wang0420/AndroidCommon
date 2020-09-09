package com.app.matrix;

import android.content.Context;
import android.util.Log;

import com.tencent.matrix.plugin.DefaultPluginListener;
import com.tencent.matrix.report.Issue;
import com.tencent.matrix.trace.config.SharePluginInfo;

/**
 * @author wangwei
 * @date 2020/9/9.
 */
public class TestPluginListener extends DefaultPluginListener {
    /**
     * fps 的阈值，小于这个值就上报
     */
    public static final int FPS_THRESHOLD = 50;
    /**
     * 闪屏页为"default"统计不准确，过滤掉
     */
    public static final String FPS_SCENE_DEFAULT = "default";


    public TestPluginListener(Context context) {
        super(context);

    }

    @Override
    public void onReportIssue(Issue issue) {

        super.onReportIssue(issue);
        Log.e("matrix---", issue.toString());
        switch (issue.getTag()) {
            case SharePluginInfo.TAG_PLUGIN_FPS:
                //fps数据
                break;
            case SharePluginInfo.TAG_PLUGIN_EVIL_METHOD:
                //慢函数
//                fileMatrix(issue.getContent().toString());
                break;
            case SharePluginInfo.TAG_PLUGIN_STARTUP:
                //启动耗时

                break;
            default:
        }


        //add your code to process data
    }
}