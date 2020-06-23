package com.android.newcommon.net.download;

/**
 * 请添加注释说明
 *
 * @author wangwei
 * @date 2020/6/12.
 */
// body内部回调
public interface DownLoadBodyListener {
    void onProgress(long progress, long total, boolean done);

    void onFailed(String message);
}