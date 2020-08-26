package com.module.ui.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.common.router.ActivityPath;
import com.android.common.router.IUserModuleService;

/**
 * Created by wangwei on 2020/4/24.
 */

@Route(path = ActivityPath.UserModuleService)
public class UserModuleService implements IUserModuleService {
    @Override
    public String getUserInfo() {
        return "我是android_user Module里面的方法传递过来的数据";
    }

    @Override
    public void init(Context context) {

    }
}
