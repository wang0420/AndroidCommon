package com.android.newcommon.net.download;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;


public class DownLoadResponseBody extends ResponseBody {

    private DownloadInfo downloadInfo;
    private ResponseBody mResponseBody;
    private DownLoadBodyListener downLoadBodyListener;
    private BufferedSource bufferedSource;

    public DownLoadResponseBody(ResponseBody mResponseBody) {
        this.mResponseBody = mResponseBody;
    }

    public DownLoadResponseBody(DownloadInfo downloadInfo, ResponseBody mResponseBody, DownLoadBodyListener downLoadBodyListener) {
        this.downloadInfo = downloadInfo;
        this.mResponseBody = mResponseBody;
        this.downLoadBodyListener = downLoadBodyListener;
    }

    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(mResponseBody.source()));
        }
        return bufferedSource;
    }

    public Source source(Source source) {
        return new ForwardingSource(source) {

            //当前读取字节数
            long bytesRead = 0L;
            //总字节长度
            long totalLength = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                //读取的字节数
                if (downloadInfo != null &&
                        (downloadInfo.state == DownloadInfo.STATE_PAUSE || downloadInfo.state == DownloadInfo.STATE_WAITING)) {
                    bytesRead = 0;
                    return bytesRead;
                }
                try {
                    bytesRead = super.read(sink, byteCount);
                } catch (Exception e) {
                    Log.e("download", "download", e);
                    bytesRead = 0;
                    if (e.getMessage() != null) {
                        postMainThread(e.getMessage());
                    } else {
                        postMainThread("");
                    }
                    e.printStackTrace();
                }
                Log.d("download", "bytesRead =" + bytesRead);
                if (bytesRead != -1) {

                } else {
                    bytesRead = 0;
                }
                totalLength += bytesRead;
                if (downloadInfo != null) {
                    downloadInfo.currentLength = downloadInfo.currentLength + bytesRead;
                    Log.d("download", "remain =" + (downloadInfo.fileLength - downloadInfo.currentLength) +
                            ",currentLength =" + downloadInfo.currentLength +
                            ",fileLength =" + downloadInfo.fileLength);

                    postMainThread(downloadInfo.currentLength, downloadInfo.fileLength);
                }
                return bytesRead;
            }
        };
    }

    private void postMainThread(long progress, long total) {
        if (downLoadBodyListener != null) {
            downLoadBodyListener.onProgress(progress, total, progress >= total);
        }
    }

    private void postMainThread(String message) {
        if (downLoadBodyListener != null) {
            downLoadBodyListener.onFailed(message);
        }
    }


}
