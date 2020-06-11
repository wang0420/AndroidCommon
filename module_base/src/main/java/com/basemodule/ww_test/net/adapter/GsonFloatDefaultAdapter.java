package com.basemodule.ww_test.net.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * Gson Float解析器，解决Gson解析空字符串为float类型时报错的问题.
 *
 * @author Hubery
 * @date 2019-1-14
 */
public class GsonFloatDefaultAdapter implements JsonSerializer<Float>, JsonDeserializer<Float> {
    @Override
    public Float deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            if (isIllegal(json)) return 0.0f;
        } catch (Exception ignore) {
        }
        try {
            return json.getAsFloat();
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }

    private boolean isIllegal(JsonElement json) {
        if (json == null || "".equals(json.getAsString()) || "null".equals(json.getAsString())) {//定义为int类型,如果后台返回""或者null,则返回0
            return true;
        }
        return false;
    }

    @Override
    public JsonElement serialize(Float src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }
}