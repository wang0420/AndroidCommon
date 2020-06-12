package com.basemodule.ww_test.net.download;

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
        Response originalResponse = chain.proceed(chain.request());
        DownLoadResponseBody body = new DownLoadResponseBody(downloadInfo, originalResponse.body(), downLoadBodyListener);

        return originalResponse.newBuilder()
                .body(body)
                .build();

    }

}
