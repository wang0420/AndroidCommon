package com.android.newcommon.net.download.break_point;

import android.util.Log;

import com.android.newcommon.net.download.DownloadHelper;
import com.android.newcommon.net.download.DownloadInfo;
import com.google.gson.Gson;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

import static com.android.newcommon.net.download.DownloadHelper.TEMP_FILE_PATH_NAME;

public class BreakPointManager {
    public static final String BREAK_POINT_TEMP_FILE_NAME = "_temp.bp";
    private DownloadInfo downloadInfo;

    public BreakPointManager(DownloadInfo downloadInfo) {
        this.downloadInfo = downloadInfo;
    }

    /**
     * 是否能从文件中获取到临时文件的断点
     *
     * @return 能不能
     */
    public boolean isGoOnBreakPointDownloadEnable() {
        DownloadInfo downloadInfoLocal = getBreakPointInfoFromDisk();
        if (downloadInfoLocal != null && downloadInfoLocal.breakLength > 0) {
            downloadInfo.breakLength = downloadInfoLocal.breakLength;
//            Log.i("download", "isGoOnBreakPointDownloadEnable() \n" + downloadInfo.breakProgress);
            return true;
        }
        return false;
    }

    public DownloadInfo getBreakPointInfoFromDisk() {
        if (downloadInfo == null || downloadInfo.fileSavePath == null || downloadInfo.fileName == null) {
            return null;
        }
        File tempFile = new File(downloadInfo.fileSavePath, downloadInfo.fileName + TEMP_FILE_PATH_NAME);
        if (!tempFile.exists()) {
            return null;
        }
        File breakPointFile = new File(downloadInfo.fileSavePath, downloadInfo.fileName + BREAK_POINT_TEMP_FILE_NAME);
        if (!breakPointFile.exists()) {
            return null;
        }
        DownloadInfo downloadInfoLocal = null;
        try {
            Source source = Okio.source(breakPointFile);
            BufferedSource bufferedSource = Okio.buffer(source);
            String content = bufferedSource.readUtf8();
            downloadInfoLocal = new Gson().fromJson(content, DownloadInfo.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadInfoLocal;
    }

    /**
     * 记录断点位置
     * 将断点信息保存到文件中
     */
    public synchronized void recordBreakPoint() {
        if (downloadInfo == null || downloadInfo.fileSavePath == null || downloadInfo.fileName == null
                || downloadInfo.currentLength <= 0) {
            return;
        }
        DownloadInfo recordDownloadInfo = downloadInfo.clone();
        if (recordDownloadInfo == null) {
            return;
        }
        recordDownloadInfo.breakLength = recordDownloadInfo.currentLength;
        //防止那一行数据没有写成功
        recordDownloadInfo.breakLength -= DownloadHelper.BUFFER_SIZE;
        if (recordDownloadInfo.breakLength <= 0) {
            recordDownloadInfo.breakLength = 0;
        }
        String fileContent = new Gson().toJson(recordDownloadInfo);
        Log.i("download", "recordBreakPoint() \nbreakLength:" + recordDownloadInfo.breakLength
                + " time:" + System.currentTimeMillis()
                + " Thread:" + Thread.currentThread());
        //记录下载信息的文件
        File breakPointFile = new File(recordDownloadInfo.fileSavePath, recordDownloadInfo.fileName + BREAK_POINT_TEMP_FILE_NAME);
        Sink sink;
        BufferedSink bufferedSink = null;
        try {
            sink = Okio.sink(breakPointFile);
            bufferedSink = Okio.buffer(sink);
            bufferedSink.writeUtf8(fileContent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(bufferedSink);
        }
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 清除临时文件
     */
    public void clearTempFile() {
        if (downloadInfo == null || downloadInfo.fileSavePath == null || downloadInfo.fileName == null) {
            return;
        }
        //记录下载信息的文件
        File breakPointFile = new File(downloadInfo.fileSavePath, downloadInfo.fileName + BREAK_POINT_TEMP_FILE_NAME);
        if (breakPointFile.exists()) {
            breakPointFile.delete();
        }
    }
}
