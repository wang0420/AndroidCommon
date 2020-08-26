package com.android.common.router;

import android.content.Context;
import android.widget.Toast;


/**
 * Created by toney on 2017/6/5.
 * 全局页面路由工具类
 * Banner广告栏，发现标签 ，push，html
 */

public class ZARouter {


    private int mType = 1;


    private long memberID;
    private long bizID;
    private String ext = "";

    private ZARouter() {
    }

    private void init() {
        mType = 1;
        memberID = 0;
        bizID = 0;
        ext = "";
    }


    private static ZARouter instance;

    public synchronized static ZARouter build() {
        if (instance == null) {
            instance = new ZARouter();
        }
        instance.init();
        return instance;
    }


    public ZARouter type(int type) {
        mType = type;
        return this;
    }

    public ZARouter memberID(long memberID) {
        this.memberID = memberID;
        return this;
    }
    public ZARouter bizID(long bizID) {
        this.bizID = bizID;
        return this;
    }
    public ZARouter ext(String ext) {
        this.ext = ext;
        return this;
    }



    /**
     * 路由
     *
     * @param context
     * @return
     */
    public boolean router(final Context context) {
        if (context == null) {
            return false;
        }
        switch (mType) {
            case 1:   //无跳转
                RouterManager.gotoActivity(context, ActivityPath.WOMAN_EMOTION_ASSESSMENT_ACTIVITY);
                break;
            case 2:   //无跳转
                RouterManager.getARouter(ActivityPath.VERSION_CENTER_ACTIVITY)
                        .navigation(context);
                break;

        }
        return true;
    }



}
