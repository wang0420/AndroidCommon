package com.android.newcommon.net.download;

import android.text.TextUtils;

import java.io.Serializable;


public class DownloadInfo implements Serializable, Cloneable {
    //定义下载状态常量
    public static final int STATE_WAITING = 1;      //等待    --> 下载，暂停
    public static final int STATE_DOWNLOADING = 2;  //下载中  --> 暂停，完成，错误
    public static final int STATE_PAUSE = 3;        //暂停    --> 等待，下载
    public static final int STATE_FINISH = 4;       //完成    --> 重新下载
    public static final int STATE_ERROR = 5;        //错误    --> 等待

//    public int connectionTimeOut;//下载超时时长，不再使用

    public String url;// 原先的key字段不再使用，直接以url作为key了
    public String fileName;
    public String fileSavePath;
    public boolean immediately = true;//是否立即下载，默认true，因为实际写代码，立即下载的场景更多
    public long fileLength;
    /**
     * 断点进度
     */
    public long breakLength;
    /**
     * 当前进度
     */
    public long currentLength;

    public long refreshTime = 0;
    public int state = STATE_WAITING;//默认是1

    /**
     * 断点文件md5
     */
    public String breakPointFileMD5;
    /**
     * 是否支持断点下载
     */
    private boolean isBreakPointEnable = false;

    public boolean isBreakPointEnable() {
        return isBreakPointEnable;
    }

    public void setBreakPointEnable(boolean breakPointEnable) {
        isBreakPointEnable = breakPointEnable;
    }


    public DownloadInfo() {

    }

    public DownloadInfo(String url, String fileName, String fileSavePath) {
        this.url = url;
        this.fileName = fileName;
        this.fileSavePath = fileSavePath;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && object instanceof DownloadInfo) {
            if (TextUtils.equals(url, ((DownloadInfo) object).url)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (url == null || url.isEmpty()) {
            return super.hashCode();
        }
        int hash = 0;
        for (int i = 0; i < url.length(); i++) {
            hash = 31 * hash + url.charAt(i);
        }
        if (hash == 0) {
            hash = super.hashCode();
        }
        return hash;
    }

    @Override
    public DownloadInfo clone() {
        DownloadInfo downloadInfo = null;
        try {
            downloadInfo = (DownloadInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return downloadInfo;
    }
}
