package com.android.common.ww_test.net.download;

public class DownloadCallbackWrapper implements IDownloadCallback {
    private IDownloadCallback inner;

    public DownloadCallbackWrapper(IDownloadCallback inner) {
        this.inner = inner;
    }

    @Override
    public void onProgress(DownloadInfo info, long progress, long total, boolean done) {
        if (this.inner != null) {
            this.inner.onProgress(info, progress, total, done);
        }
    }

    @Override
    public void onSuccess(DownloadInfo info, String result) {
        if (this.inner != null) {
            this.inner.onSuccess(info, result);
        }
        DownloadManager.getInstance().tryStartNextTask();
    }

    @Override
    public void onFailed(DownloadInfo info, String errorMsg) {
        if (this.inner != null) {
            this.inner.onFailed(info, errorMsg);
        }
        DownloadManager.getInstance().tryStartNextTask();
    }
}
