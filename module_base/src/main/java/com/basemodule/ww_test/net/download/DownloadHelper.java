package com.basemodule.ww_test.net.download;

import android.util.Log;

import com.basemodule.ww_test.net.ZNetwork;
import com.basemodule.ww_test.net.download.break_point.BreakPointManager;
import com.basemodule.ww_test.net.utils.LifecycleUtils;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.ref.WeakReference;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class DownloadHelper implements ILoad {

    private DownloadInfo downloadInfo;
    private IDownloadCallback iDownloadCallback;
    private DownLoadBodyListener downloadBodyListener;

    private volatile long currentUploadProgress = 0;
    private volatile long totalUploadProgress = 0;

    //网络数据
    private DisposableObserver<ResponseBody> bodySubscriber;
    private Observable<ResponseBody> bodyObservable;

    //计时器，每隔200ms刷新下载进度
    private Observable<Long> timerObservable;
    private Disposable timerDisposable;

    private static final String ERROR_FILE_INVALID = "文件无效";
    private static final String ERROR_FILE_SAVE = "文件保存失败";
    private static final String SUCCESS_TIP = "下载成功";
    public static final int BUFFER_SIZE = 1024;
    public static final String TEMP_FILE_PATH_NAME = "_temp";

    /**
     * 是否重置旧文件
     */
    private boolean isResetFile = true;

    /**
     * 是否取消下载
     * 取消则不回调下载结果
     */
    private volatile boolean isCancel = false;

    private WeakReference<LifecycleProvider> lifecycleProviderWeakReference;

    //计时器，每隔1000ms记录下载进度
    private Observable<Long> recordBreakPointObservable;
    private DisposableObserver<Long> recordBreakPointSubscriber;
    private BreakPointManager breakPointManager;

    public DownloadHelper(DownloadInfo downloadInfo, IDownloadCallback iDownloadCallback, LifecycleProvider lifecycleProvider) {
        this.downloadInfo = downloadInfo;
        this.iDownloadCallback = iDownloadCallback;

        if (lifecycleProvider != null) {
            lifecycleProviderWeakReference = new WeakReference<>(lifecycleProvider);
        }
        initHttp();
        initData(lifecycleProvider);
    }

    private void initHttp() {
        this.downloadBodyListener = new DownLoadBodyListener() {
            @Override
            public void onProgress(long progress, long total, boolean done) {
                currentUploadProgress = progress;
                totalUploadProgress = total;
            }

            @Override
            public void onFailed(String message) {
                downloadInfo.state = DownloadInfo.STATE_ERROR;
                recordBreakPointInNewThread();
                onStop();
                removeDownTask();
                callbackError(message);
            }
        };
//        retrofit = ZARetrofit.getDownloadRetrofit(
//                new DownloadInterceptor(this.downloadBodyListener, this.downloadInfo),
//                downloadInfo.url,
//                downloadInfo.connectionTimeOut);
    }

    private void recordBreakPointInNewThread() {

        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                recordBreakPoint();
            }
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 记录断点
     */
    private void recordBreakPoint() {
        if (breakPointManager != null) {
            breakPointManager.recordBreakPoint();
        }
    }

    private void initData(LifecycleProvider lifecycleProvider) {
        timerObservable = Observable.interval(0, 200, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread());
        timerObservable = LifecycleUtils.bindLifecycle(timerObservable, lifecycleProvider);

        bodyObservable = ZNetwork.getService(FileLoadService.class)
//        bodyObservable = retrofit.create(FileLoadService.class)
                .download(downloadInfo.url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    private class TimerSubscriber implements Consumer<Long> {

        @Override
        public void accept(Long aLong) throws Exception {
            if ((totalUploadProgress != 0 && currentUploadProgress >= totalUploadProgress)
                    || downloadInfo.state != DownloadInfo.STATE_DOWNLOADING) {
                stopRefreshTimer();
            }
            if (iDownloadCallback != null && totalUploadProgress != 0) {
                iDownloadCallback.onProgress(downloadInfo,
                        currentUploadProgress, totalUploadProgress,
                        currentUploadProgress >= totalUploadProgress);
            }
        }
    }

    private class BodySubscriber extends DisposableObserver<ResponseBody> {
        @Override
        public void onComplete() {
            Log.d("download", "onComplete --------------");
            if (updateFileName()) {
                callbackSuccess(SUCCESS_TIP);
                if (breakPointManager != null) {
                    breakPointManager.clearTempFile();
                }
            } else {
                callbackError(ERROR_FILE_SAVE);
            }
            downloadInfo.state = DownloadInfo.STATE_FINISH;
            stopRefreshTimer();
            stopRecordBreakPointTimer();
            removeDownTask();
        }

        @Override
        public void onError(final Throwable e) {
            callbackError(e.toString());
            downloadInfo.state = DownloadInfo.STATE_ERROR;
            stopRefreshTimer();
            removeDownTask();
        }

        @Override
        public void onNext(ResponseBody responseBody) {
            if (downloadInfo.fileLength <= 0) {
                downloadInfo.fileLength = responseBody.contentLength();
                if (downloadInfo.breakLength > 0) {
                    downloadInfo.fileLength = downloadInfo.fileLength + downloadInfo.breakLength;
                }
            }
            if (downloadInfo.fileLength <= 0) {
                callbackError(ERROR_FILE_INVALID);
                onStop();
            } else {
                if (downloadInfo.isBreakPointEnable()) {
                    if (downloadInfo.breakLength > 0) {
                        downloadInfo.currentLength = downloadInfo.breakLength;
                    }
                    startRecordBreakPointTimer();
                }
                writeFile(responseBody.byteStream());
            }
        }
    }


    private class RecordBreakPointSubscriber extends DisposableObserver<Long> {

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onComplete() {

        }

        @Override
        public void onNext(Long aLong) {
//            Log.i("download", "RecordBreakPointSubscriber:" + aLong);
            recordBreakPoint();

        }
    }


    private void startRefreshTimer() {
        stopRefreshTimer();
        timerDisposable = timerObservable.subscribe(new TimerSubscriber());
    }

    private void stopRefreshTimer() {
        if (timerDisposable != null && !timerDisposable.isDisposed()) {
            timerDisposable.dispose();
        }
    }

    private void startRecordBreakPointTimer() {
        stopRecordBreakPointTimer();
        recordBreakPointSubscriber = new RecordBreakPointSubscriber();
        recordBreakPointObservable.subscribe(recordBreakPointSubscriber);
    }

    private void stopRecordBreakPointTimer() {
        if (recordBreakPointSubscriber != null && !recordBreakPointSubscriber.isDisposed()) {
            recordBreakPointSubscriber.dispose();
        }
    }

    private LifecycleProvider getLifecycleProvider() {
        return lifecycleProviderWeakReference == null ? null : lifecycleProviderWeakReference.get();
    }

    private void callbackError(final String errorMsg) {
        if (isCancel) {
            return;
        }
        LifecycleUtils
                .bindLifecycle(Observable.just(0), getLifecycleProvider())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (iDownloadCallback != null) {
                            iDownloadCallback.onFailed(downloadInfo, errorMsg);
                        }
                    }
                });
    }

    private void callbackSuccess(final String msg) {
        if (isCancel) {
            return;
        }
        LifecycleUtils
                .bindLifecycle(Observable.just(0), getLifecycleProvider())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if (iDownloadCallback != null) {
                            iDownloadCallback.onSuccess(downloadInfo, msg);
                        }
                    }
                });
    }

    private void download() {
        if (downloadInfo.isBreakPointEnable()) {
            breakPointManager = new BreakPointManager(downloadInfo);
            recordBreakPointObservable = Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io());
            isResetFile = !breakPointManager.isGoOnBreakPointDownloadEnable();
        } else {
            isResetFile = true;
        }
        this.downloadInfo.state = DownloadInfo.STATE_DOWNLOADING;
        bodySubscriber = new BodySubscriber();
        bodyObservable.subscribe(bodySubscriber);
    }

    public void writeFile(InputStream inputStream) {
        File fileDir = new File(downloadInfo.fileSavePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        String savePath = new File(downloadInfo.fileSavePath, downloadInfo.fileName + TEMP_FILE_PATH_NAME).getAbsolutePath();
        File file = new File(savePath);
        if (isResetFile) {
            if (file.exists()) {
                file.delete();
            }
            isResetFile = false;
        }
        FileChannel channelOut = null;
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "rwd");
            channelOut = randomAccessFile.getChannel();
            MappedByteBuffer mappedBuffer = channelOut.map(
                    FileChannel.MapMode.READ_WRITE,
                    downloadInfo.currentLength,
                    downloadInfo.fileLength - downloadInfo.breakLength);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len;
            int record = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                mappedBuffer.put(buffer, 0, len);
                record += len;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                if (channelOut != null) {
                    channelOut.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 更新文件名
     * 临时文件改成正式文件名
     */
    private boolean updateFileName() {
        File tempFile = new File(downloadInfo.fileSavePath, downloadInfo.fileName + TEMP_FILE_PATH_NAME);
        if (!tempFile.exists()) {
            return false;
        }
        File oldFile = new File(downloadInfo.fileSavePath, downloadInfo.fileName);
        if (oldFile.exists()) {
            oldFile.delete();
        }
        return tempFile.renameTo(oldFile);
    }

    @Override
    public void onStart() {
        isCancel = false;
        startRefreshTimer();
        download();
    }

    @Override
    public void onStop() {
        stopRefreshTimer();
        stopRecordBreakPointTimer();
        if (bodySubscriber != null && !bodySubscriber.isDisposed()) {
            bodySubscriber.dispose();
        }
    }

    @Override
    public void onCancel() {
        isCancel = true;
        onStop();
    }

    public void resetFile(boolean resetFile) {
        isResetFile = resetFile;
    }


    private void removeDownTask() {
        DownloadManager.getInstance().removeDownloadTask(downloadInfo, false);
    }

    public DownLoadBodyListener getDownloadBodyListener() {
        return downloadBodyListener;
    }

    public long getTotalProgress() {
        return totalUploadProgress;
    }

    public long getCurrentProgress() {
        return currentUploadProgress;
    }
}
