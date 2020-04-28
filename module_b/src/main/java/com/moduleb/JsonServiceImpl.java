package com.moduleb;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.google.gson.Gson;

/**
 * Created by wangwei on 2018/4/28.
 */

// 如果需要传递自定义对象，需要实现 SerializationService,并使用@Route注解标注(方便用户自行选择序列化方式)，例如：
@Route(path = "/service/json")
public class JsonServiceImpl implements SerializationService {
    @Override
    public void init(Context context) {

    }

    @Override
    public <T> T json2Object(String text, Class<T> clazz) {
        Log.w("TAG", "text---" + text);
        return new Gson().fromJson(text, clazz);
    }

    @Override
    public String object2Json(Object instance) {
        Log.w("TAG", "Object---" + instance);
        return new Gson().toJson(instance);
    }
}