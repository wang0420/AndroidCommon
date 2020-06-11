package com.basemodule.za_test.net.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class AddHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("source_type", "android")
                .addHeader("memberId", "" + 1256981313)
                .addHeader("App-Token","c133930c-c692-4990-9718-3c07389f8d19" )
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json, text/plain, */*")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                .method(request.method(), request.body())
                .build();
        return chain.proceed(request);
    }

}
