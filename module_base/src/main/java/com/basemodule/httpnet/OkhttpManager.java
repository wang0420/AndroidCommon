package com.basemodule.httpnet;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import com.basemodule.utils.FileUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 当前类注释:OKHttpManager_Test 工具类封装
 * ProjectName：OkhttpTest
 * Author:<a href="http://www.cniao5.com">菜鸟窝</a>
 * Description：
 * 菜鸟窝是一个只专注做Android开发技能的在线学习平台，课程以实战项目为主，对课程与服务”吹毛求疵”般的要求，
 * 打造极致课程，是菜鸟窝不变的承诺
 */
public class OkhttpManager {
    private static final String FILE_PREFIX = "CNIAO5_";
    //OKhttp对象实例
    private OkHttpClient mOkHttpClient;
    private static OkhttpManager okhttpManager;
    private Handler handler;

    //http  json方式请求
    public static final MediaType TYPE = MediaType.parse("application/json; charset=utf-8");
    public static final String BASE_URL = "http://218.17.158.113:13001/servlet/json?";

    public static OkhttpManager getInstance() {
        if (okhttpManager == null) {
            okhttpManager = new OkhttpManager();
        }
        return okhttpManager;
    }

    private OkhttpManager() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();
        handler = new Handler(Looper.getMainLooper());
    }

    //Http  键值对  post请求
    public void p_postAsyncParams(Map<String, String> params, final SyncDataBack callBack) {
        try {
            if (BASE_URL == null || BASE_URL.length() == 0) {
                throw new IllegalArgumentException("url can not be null !");
            }
            if (params == null) {
                params = new HashMap<String, String>();
            }
            FormBody.Builder builder = new FormBody.Builder();//3.0之后没有FormEncodingBuilder  被FormBody替代了
            appendParams(builder, params);//循环Map添加请求参数
            RequestBody requestBody = builder.build();
            final Request request = new Request.Builder().url(BASE_URL).post(requestBody).build();
            //request.tag();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call request, IOException e) {
                    deliverFailure(request, e, callBack);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        deliverSuccess(response.body().string(), callBack);
                    } catch (IOException e) {
                        deliverFailure(call, e, callBack);
                    }
                }
            });
        } catch (Exception e) {
            Log.e("Post enqueue error:", "" + e.getMessage());


        }

    }

    private void appendParams(FormBody.Builder builder, Map<String, String> params) {
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey().toString();
                String value = null;
                if (entry.getValue() == null) {
                    value = "";
                } else {
                    value = entry.getValue();
                }
                builder.add(key, value);
            }
        }
        /*if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }*/
    }

    //Http  JSon  post请求
    public void post_jsonRequest(Map<String, String> params, final SyncDataBack callBack) {
        JSONObject paramObject = null;
        try {
            paramObject = new JSONObject(params);
        } catch (Exception e) {
            return;
        }
        Log.w("MainActivity", "----" + paramObject);
        RequestBody body = RequestBody.create(TYPE, paramObject.toString());
        Request request = new Request.Builder().url(BASE_URL).post(body).build();
        request.tag();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deliverFailure(call, e, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    deliverSuccess(response.body().string(), callBack);
                } catch (IOException e) {
                    deliverFailure(call, e, callBack);
                }
            }
        });

    }

    /**
     * do cacel by tag
     *
     * @param tag tag
     */
    public void cancel(Object tag) {
        Dispatcher dispatcher = mOkHttpClient.dispatcher();
        for (Call call : dispatcher.queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : dispatcher.runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }


    // 进行发送同步GET请求，并且返回String类型数据
    private String p_getSyncAsString(String url) throws IOException {
        Response response = p_getSync(url);
        return response.body().string();
    }

    //  同步Get请求方法
    private Response p_getSync(String url) throws IOException {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        Response response = call.execute();
        return response;
    }

    //进行GET异步请求
    public void p_getAsync(String url, final SyncDataBack callBack) {
        final Request request = new Request.Builder().url(url).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call request, IOException e) {
                deliverFailure(request, e, callBack);
            }

            /**
             * 异步返回数据
             * @param response
             * @throws IOException
             */
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    deliverSuccess(response.body().string(), callBack);
                } catch (IOException e) {
                    deliverFailure(call, e, callBack);
                }
            }
        });
    }


    //文件下载
    public void downFile(String url) {
        getInstance().downAsynFile(url);
    }

    public static void downAsynFile(String url) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call request, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream = null;
                try {
                    File dirFile = FileUtils.getInstance().getImageFile();
                    if (!dirFile.exists()) {
                        dirFile.mkdir();
                    }
                    File file = new File(dirFile, "wangweiname.jpg");
                    fileOutputStream = new FileOutputStream(file);
                    long total = response.body().contentLength();

                    long current = 0;
                    byte[] buffer = new byte[2048];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        current += len;
                        fileOutputStream.write(buffer, 0, len);
                        Log.i("wangshu", "total------>" + total + "--" + current);
                    }

                    fileOutputStream.flush();
                } catch (IOException e) {
                    Log.i("wangshu", "IOException");
                    e.printStackTrace();
                }

                Log.d("wangshu", "文件下载成功");
            }
        });
    }

    //*************************数据请求成功或者失败分发方法**********************

    /**
     * 进行分发请求失败的数据情况
     *
     * @param
     * @param e
     * @param callBack
     */
    private void deliverFailure(final Call call, final IOException e, final SyncDataBack callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.requestFailure(call, e);
                }
            }
        });
    }

    /**
     * 请求分发请求成功的数据情况
     *
     * @param result
     * @param callBack
     */
    private void deliverSuccess(final String result, final SyncDataBack callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.requestSuccess(result);
                }
            }
        });
    }

    //*************对外公布的方法********************

}

