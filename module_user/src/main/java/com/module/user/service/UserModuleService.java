package com.module.user.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.basemodule.ARouterManager;
import com.basemodule.service.IUserModuleService;

/**
 * Created by wangwei on 2020/4/24.
 */

@Route(path = ARouterManager.UserModuleService)
public class UserModuleService implements IUserModuleService {
    @Override
    public String getUserInfo() {
        return "我是android_user Module里面的方法传递过来的数据";
    }

    @Override
    public void init(Context context) {

    }
}
