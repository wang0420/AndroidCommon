package com.android.common.ww_test.net.interceptor;

import com.android.common.ww_test.net.download.DownLoadBodyListener;
import com.android.common.ww_test.net.download.DownLoadResponseBody;
import com.android.common.ww_test.net.download.DownloadHelper;
import com.android.common.ww_test.net.download.DownloadInfo;
import com.android.common.ww_test.net.download.DownloadManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class DownloadInterceptor implements Interceptor {

    public DownloadInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String urlInfo = originalRequest.url().toString();
        DownloadInfo downloadInfo = DownloadManager.getInstance().findDownloadInfo(urlInfo);
        DownloadHelper downloadHelper = DownloadManager.getInstance().findDownloadHelper(urlInfo);
        DownLoadBodyListener downLoadBodyListener = null;
        if (downloadHelper != null) {
            downLoadBodyListener = downloadHelper.getDownloadBodyListener();
        }

        String headerRange;
        if (downloadInfo != null) {
            headerRange = "bytes=" + downloadInfo.breakLength + "-";
        } else {
            headerRange = "bytes=0-";
        }
        Request request = originalRequest.newBuilder().addHeader("RANGE", headerRange).build();
        Response originalResponse = null;
        Response response;

        String requestMethodInfo = request.method();
        String requestHeader = ResponseInterceptor.getRequestHeader(request);
        long startTime = System.currentTimeMillis();
        long consumeTime;
        try {
            originalResponse = chain.proceed(request);
            DownLoadResponseBody body = new DownLoadResponseBody(downloadInfo, originalResponse.body(), downLoadBodyListener);
            response = originalResponse.newBuilder().body(body).build();
            consumeTime = System.currentTimeMillis() - startTime;
            if (response.isSuccessful()) {
                //config.onRequestInfo(requestMethodInfo, urlInfo, consumeTime, requestHeader, null, null, response.code());
                return response;
            } else {
                Exception e = new Exception("error http code:" + response.code());
               // config.onRequestInfo(requestMethodInfo, urlInfo, consumeTime, requestHeader, null, e, response.code());
                throw e;
            }
        } catch (IOException e) {
            if (downLoadBodyListener != null) {
                downLoadBodyListener.onFailed(e.getMessage());
            }
            consumeTime = System.currentTimeMillis() - startTime;
            //config.onRequestInfo(requestMethodInfo, urlInfo, consumeTime, requestHeader, null, e, -1);
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            consumeTime = System.currentTimeMillis() - startTime;
            //config.onRequestInfo(requestMethodInfo, urlInfo, consumeTime, requestHeader, null, e, -1);
        }
        return originalResponse;
    }

}
