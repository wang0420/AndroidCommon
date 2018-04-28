package com.basemodule.bean;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;

/**
 * Created by wangwei on 2018/4/28.
 */
// 如果需要传递自定义对象，需要实现 SerializationService,并使用@Route注解标注(方便用户自行选择序列化方式)，例如：
@Route(path = "/service/json")
public class EmployeeBean implements SerializationService {

    @Override
    public <T> T json2Object(String json, Class<T> clazz) {
        return null;
    }

    @Override
    public String object2Json(Object instance) {
        return null;
    }

    @Override
    public void init(Context context) {

    }
}