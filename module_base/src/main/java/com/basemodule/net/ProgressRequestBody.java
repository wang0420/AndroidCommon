package com.basemodule.net;


import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;



public class ProgressRequestBody extends RequestBody {
    private UploadProgressListener mUploadProgressListener;
    private RequestBody mRequestBody;
    private CountingSink mCountingSink;

    //为什么还要传一个RequestBody进去
    public ProgressRequestBody(RequestBody requestBody, UploadProgressListener uploadProgressListener) {
        mUploadProgressListener = uploadProgressListener;
        mRequestBody = requestBody;
    }

    //指定上传的类型
    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    //返回文件总的字节大小，如果文件大小获取失败则返回-1
    @Override
    public long contentLength() {
        try {
            return mRequestBody.contentLength();
        } catch (IOException e) {
            return -1;
        }
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        mCountingSink = new CountingSink(sink);
        BufferedSink buffer = Okio.buffer(mCountingSink);
        mRequestBody.writeTo(buffer);
        buffer.flush();
    }

    public class CountingSink extends ForwardingSink {
        private long byteWritten;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            byteWritten += byteCount;
            mUploadProgressListener.onProgress(byteWritten, contentLength());
        }
    }

}
