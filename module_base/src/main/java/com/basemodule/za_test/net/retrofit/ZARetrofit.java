package com.basemodule.za_test.net.retrofit;

import android.text.TextUtils;

import com.basemodule.za_test.net.adapter.GsonDoubleDefaultAdapter;
import com.basemodule.za_test.net.adapter.GsonFloatDefaultAdapter;
import com.basemodule.za_test.net.adapter.GsonIntegerDefaultAdapter;
import com.basemodule.za_test.net.adapter.GsonLongDefaultAdapter;
import com.basemodule.za_test.net.interceptor.AddHeaderInterceptor;
import com.basemodule.za_test.net.interceptor.LogInterceptor;
import com.basemodule.za_test.net.utils.HttpsUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ZARetrofit {
    private static final String TAG = ZARetrofit.class.getSimpleName();
    private static final String HTTPS_PREFIX = "https://";

    private static ZARetrofit instance;

    private Retrofit mHttpRetrofit;
    private Retrofit mHttpsRetrofit;
    private Retrofit mUploadHttpRetrofit;
    private Retrofit mUploadHttpsRetrofit;


    public static ZARetrofit getInstance() {
        if (instance == null) {
            synchronized (ZARetrofit.class) {
                if (instance == null) {
                    instance = new ZARetrofit();
                }
            }
        }
        return instance;
    }

    private Retrofit getHttpRetrofit() {
        if (mHttpRetrofit == null) {
            mHttpRetrofit = initRetrofit(false, false);
        }
        return mHttpRetrofit;
    }

    private Retrofit getHttpsRetrofit() {
        if (mHttpsRetrofit == null) {
            mHttpsRetrofit = initRetrofit(true, false);
        }
        return mHttpsRetrofit;
    }

    private Retrofit getUploadHttpRetrofit() {
        if (mUploadHttpRetrofit == null) {
            mUploadHttpRetrofit = initRetrofit(false, true);
        }
        return mUploadHttpRetrofit;
    }

    private Retrofit getUploadHttpsRetrofit() {
        if (mUploadHttpsRetrofit == null) {
            mUploadHttpsRetrofit = initRetrofit(true, true);
        }
        return mUploadHttpsRetrofit;
    }

    private Retrofit initRetrofit(boolean isHttps, boolean isUpload) {
        return new Retrofit.Builder()
                .baseUrl("https://ulove.zhenai.com")
                .addConverterFactory(GsonConverterFactory.create(buildGSON()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient(isHttps, isUpload))
                .build();
    }

    private Gson buildGSON() {
        return new GsonBuilder()
                .setLenient()
                .serializeNulls()
                .registerTypeAdapter(Integer.class, new GsonIntegerDefaultAdapter())
                .registerTypeAdapter(int.class, new GsonIntegerDefaultAdapter())
                .registerTypeAdapter(Double.class, new GsonDoubleDefaultAdapter())
                .registerTypeAdapter(double.class, new GsonDoubleDefaultAdapter())
                .registerTypeAdapter(Float.class, new GsonFloatDefaultAdapter())
                .registerTypeAdapter(float.class, new GsonFloatDefaultAdapter())
                .registerTypeAdapter(Long.class, new GsonLongDefaultAdapter())
                .registerTypeAdapter(long.class, new GsonLongDefaultAdapter())
                .create();
    }

    private final static int CONNECT_TIMEOUT = 30;
    private final static int READ_TIMEOUT = 100;
    private final static int WRITE_TIMEOUT = 100;

    public OkHttpClient getOkHttpClient(boolean isHttps, boolean isUpload) {


        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//连接超时时间
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new AddHeaderInterceptor())
                .addInterceptor(new LogInterceptor());
        SSLSocketFactory sslSocketFactory = HttpsUtil.getNoCheckSSLSocketFactory();
        if (sslSocketFactory != null) {
            builder.sslSocketFactory(sslSocketFactory);
        }
        return builder.build();
    }


    public <T> T create(Class<T> service) {
        return getUploadHttpsRetrofit().create(service);
    }

    public <T> T create(Class<T> service, String fullUrl) {
        if (TextUtils.isEmpty(fullUrl)) {
            return null;
        }
        if (fullUrl.startsWith(ZARetrofit.HTTPS_PREFIX)) {
            return getHttpsRetrofit().create(service);
        } else {
            return getHttpRetrofit().create(service);
        }
    }


}
