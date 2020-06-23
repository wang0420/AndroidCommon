package com.android.newcommon.net.interceptor;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ResponseInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String requestMethodInfo = request.method();
        String urlInfo = request.url().toString();
        String requestHeader = getRequestHeader(request);
        long startTime = System.currentTimeMillis();
        long consumeTime;
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            consumeTime = System.currentTimeMillis() - startTime;
            // config.onRequestInfo(requestMethodInfo, urlInfo, consumeTime, requestHeader, null, e, -1);
            throw e;
        }
        consumeTime = System.currentTimeMillis() - startTime;
        if (response == null) {
            return response;
        }
        ResponseBody body = response.body();
        String json;
        json = body.string();// 响应的内容
        //  config.onResponse(json);
        // Response.body().string()会消耗掉原Response的输入流，这里返回一个新Response
        Response result = response.newBuilder().body(ResponseBody.create(body.contentType(), json.getBytes())).build();

        //  config.onRequestInfo(requestMethodInfo, urlInfo, consumeTime, requestHeader, json, null, response.code());
        return result;
    }

    public static String getRequestHeader(Request request) {
        if (request == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        RequestBody requestBody = request.body();
        if (requestBody != null) {
            if (requestBody.contentType() != null) {
                stringBuilder.append("Content-Type: ");
                stringBuilder.append(requestBody.contentType());
                stringBuilder.append("\n");
            }
            try {
                if (requestBody.contentLength() != -1) {
                    stringBuilder.append("Content-Length: ");
                    stringBuilder.append(requestBody.contentLength());
                    stringBuilder.append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Headers headers = request.headers();
        for (int i = 0, count = headers.size(); i < count; i++) {
            String name = headers.name(i);
            // Skip headers from the request body as they are explicitly logged above.
            if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                stringBuilder.append(name);
                stringBuilder.append(": ");
                stringBuilder.append(headers.value(i));
            }
        }
        return stringBuilder.toString();
    }
}
