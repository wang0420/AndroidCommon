package com.basemodule.ww_test.net.interceptor;

import android.util.Log;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * copy from code {@link okhttp3.logging.HttpLoggingInterceptor}
 */
public class LogInterceptor implements Interceptor {

    public LogInterceptor() {
    }


    @Override
    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        //拿到Request对象
        Request request = chain.request();
        long t1 = System.nanoTime();
        System.out.println(" request  = " + String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));
        //拿到Response对象
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        //得出请求网络,到得到结果,中间消耗了多长时间
      Log.e("TAGIn","response  " + String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        return response;
    }

}
