package com.basemodule.ww_test.net.download;

import android.text.TextUtils;
import android.util.Log;

import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class DownloadManager {

    private static DownloadManager instance;
    private int maxParallelCount = 5;//最大同时并行下载数，显式指定立即下载的任务不受此限制
    private final List<DownloadInfo> downloadInfoList;
    private HashMap<String, DownloadHelper> taskArray;

    private DownloadManager() {
        downloadInfoList = new ArrayList<>();
        taskArray = new HashMap<>();
    }

    public static DownloadManager getInstance() {
        if (instance == null) {
            synchronized (DownloadManager.class) {
                if (instance == null) {
                    instance = new DownloadManager();
                }
            }
        }
        return instance;
    }

    public int getMaxParallelCount() {
        return this.maxParallelCount;
    }

    public void setMaxParallelCount(int maxParallelCount) {
        this.maxParallelCount = Math.max(maxParallelCount, 1);
    }

    public int getDownloadInfoCount() {
        return downloadInfoList.size();
    }

    public DownloadInfo getDownloadInfoByIndex(int index) {
        synchronized (downloadInfoList) {
            if (index >= downloadInfoList.size()) {
                return null;
            }
            return downloadInfoList.get(index);
        }
    }

    public DownloadInfo findDownloadInfo(String url) {
        if (!downloadInfoList.isEmpty()) {
            for (DownloadInfo downloadInfo : downloadInfoList) {
                if (TextUtils.equals(downloadInfo.url, url)) {
                    return downloadInfo;
                }
            }
        }
        return null;
    }

    public DownloadHelper findDownloadHelper(String url) {
        return taskArray.get(url);
    }

    public void addDownloadTask(DownloadInfo info, IDownloadCallback iDownloadCallback) {
        addDownloadTask(info, iDownloadCallback, null);
    }

    /**
     * @param info 下载信息
     * @param iDownloadCallback 下载回调
     * @param lifecycleProvider
     */
    public void addDownloadTask(DownloadInfo info, IDownloadCallback iDownloadCallback, LifecycleProvider lifecycleProvider) {
        if (info == null) {
            return;
        }
        synchronized (downloadInfoList) {
            if (downloadInfoList.contains(info)) {
                return;
            }
            downloadInfoList.add(info);
            Log.e("DownloadManager", "downloadInfoList.size="+downloadInfoList.size());

            DownloadCallbackWrapper callbackWrapper = new DownloadCallbackWrapper(iDownloadCallback);
            DownloadHelper downLoadHelper = new DownloadHelper(info, callbackWrapper, lifecycleProvider);
            taskArray.put(info.url, downLoadHelper);
            if (info.immediately) {
                downLoadHelper.onStart();
            } else {
                if (getDownloadingCount() < maxParallelCount) {
//                    Log.e("DownloadManager", "downloadingCount < maxParallelCount, onStart");
                    downLoadHelper.onStart();
                }
            }
        }
    }

    public void tryStartNextTask() {
        synchronized (downloadInfoList) {
            if (downloadInfoList.isEmpty() || getDownloadingCount() >= maxParallelCount) {
                return;
            }
            DownloadInfo nextWaiting = null;
            for (DownloadInfo downloadInfo : downloadInfoList) {
                if (downloadInfo.state == DownloadInfo.STATE_WAITING && !downloadInfo.immediately) {
                    nextWaiting = downloadInfo;
                    break;
                }
            }
            if (nextWaiting != null) {
                DownloadHelper downLoadHelper = taskArray.get(nextWaiting.url);
                if (downLoadHelper != null) {
                    downLoadHelper.onStart();
                }
            }
        }
    }

    private int getDownloadingCount() {
        synchronized (downloadInfoList) {
            int downloadingCount = 0;
            if (!downloadInfoList.isEmpty()) {
                for (DownloadInfo downloadInfo : downloadInfoList) {
                    if (downloadInfo.state == DownloadInfo.STATE_DOWNLOADING && !downloadInfo.immediately) {
                        ++downloadingCount;
                    }
                }
            }
//        Log.e("DownloadManager", "downloadingCount=" + downloadingCount);
            return downloadingCount;
        }
    }

    public void removeDownloadTask(DownloadInfo info) {
        removeDownloadTask(info, true);
    }

    public void removeDownloadTask(DownloadInfo info, boolean isCancel) {
        synchronized (downloadInfoList) {
            if (downloadInfoList.contains(info)) {
                downloadInfoList.remove(info);
                DownloadHelper downLoadHelper = taskArray.get(info.url);
                if (downLoadHelper != null) {
                    if (isCancel) {
                        downLoadHelper.onCancel();
                    }
                    taskArray.remove(info.url);
                }
            }
        }
    }

    public boolean containsTask(String key) {
        synchronized (downloadInfoList) {
            for (DownloadInfo info : downloadInfoList) {
                if (info != null && TextUtils.equals(info.url, key)) {
                    return true;
                }
            }
            return false;
        }
    }
}
