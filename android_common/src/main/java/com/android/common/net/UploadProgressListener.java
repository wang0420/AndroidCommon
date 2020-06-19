package com.android.common.net;

/**
 * Created by popper on 2017/11/24.
 */

public interface UploadProgressListener {
    void onProgress(long currentCount, long totalCount);
}
