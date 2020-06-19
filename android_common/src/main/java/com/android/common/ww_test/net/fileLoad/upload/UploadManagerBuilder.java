package com.android.common.ww_test.net.fileLoad.upload;

import android.util.Log;

import com.android.common.ww_test.net.fileLoad.callback.UploadCallback;
import com.android.common.ww_test.net.fileLoad.upload.entity.FileAndParamName;
import com.android.common.ww_test.net.fileLoad.upload.entity.UploadInfo;
import com.android.common.ww_test.net.fileLoad.upload.entity.UploadRequestBody;
import com.android.common.ww_test.net.manager.ARequestManagerBuilder;
import com.android.common.ww_test.net.retrofit.BaseSubscriber;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class UploadManagerBuilder<T> extends ARequestManagerBuilder {
    private boolean isDone = false;
    /**
     * 所有已完成的文件进度(不包含未完成部分)
     */
    private long allHaveDoneAddUpProgress;
    /**
     * 全部文件累计进度
     */
    private long allAddUpProgress;
    /**
     * 全部文件全部进度
     */
    private long allTotalProgress;
    /**
     * 当前文件进度
     */
    private long currentOneProgress;
    /**
     * 当前文件全部进度
     */
    private long currentOneTotalProgress;
    /**
     * 上传文件位置
     */
    private int fileIndex = 0;


    private UploadCallback iUploadCallback;

    private Observable<T> observable;

    //计时器，每隔100ms刷新上传进度
    private Observable<Long> timerObservable;
    private DisposableObserver<Long> timerSubscriber;

    public UploadManagerBuilder() {
        timerObservable = Observable.interval(0, 100, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void startRefreshTimer() {
        stopRefreshTimer();
        timerSubscriber = new TimerSubscriber();
        timerObservable.subscribe(timerSubscriber);
    }

    private void stopRefreshTimer() {
        if (timerSubscriber != null && !timerSubscriber.isDisposed()) {
            timerSubscriber.dispose();
        }
    }

       private class TimerSubscriber extends DisposableObserver<Long> {

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onComplete() {

        }

        @Override
        public void onNext(Long aLong) {
//            Log.i("UploadBuilder", "tick:" + aLong + "   iUploadCallback:" + (iUploadCallback != null));
            if (iUploadCallback != null) {
                if (!isDone && allTotalProgress != 0) {
                    isDone = allAddUpProgress >= allTotalProgress;
                    iUploadCallback.onProgress(fileIndex, allAddUpProgress, allTotalProgress,
                            currentOneProgress, currentOneTotalProgress, isDone);
                }

//                Log.i("UploadBuilder", "isDone:" + isDone + " allAddUpProgress:" + allAddUpProgress + " allTotalProgress:" + allTotalProgress);
            }
        }
    }


    public UploadManagerBuilder api(UploadInfo<T> uploadInfo) {
        observable = uploadInfo.getApi(getParams(uploadInfo));
        return this;
    }

    public void callback(final UploadCallback<T> iUploadCallback) {
        this.iUploadCallback = iUploadCallback;
        final BaseSubscriber<T> subscriber = new BaseSubscriber<T>(iUploadCallback) {
            @Override
            public void onBegin() {
                super.onBegin();
                startRefreshTimer();
            }



            @Override
            public void onError(Throwable e) {
                super.onError(e);
                stopRefreshTimer();
            }

            @Override
            public void onComplete() {
                super.onComplete();
                stopRefreshTimer();
            }
        };
        observable = bindRxLifecycle(observable);
        observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (disposable.isDisposed()) {
                            return;
                        }
                        subscriber.onBegin();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    private HashMap<String, RequestBody> getParams(final UploadInfo entity) {
        final List<FileAndParamName> fileAndParamNames = entity.getFileAndParamNames();
        if (fileAndParamNames == null || fileAndParamNames.isEmpty()) {
            try {
                throw new Exception("文件和上传参数不能为空！");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        HashMap<String, RequestBody> params = new HashMap<>();
        for (FileAndParamName fileAndParamName : fileAndParamNames) {
            final File file = fileAndParamName.file;
            if (!file.exists()) {
                try {
                    throw new Exception("文件不存在！");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
            RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            UploadRequestBody uploadRequestBody = new UploadRequestBody(body,
                    new UploadRequestBody.UploadBodyListener() {
                        @Override
                        public void onProgress(long progress, long total, boolean done) {
                            //每次done表示一个文件完成
                            if (done) {
                                if (fileIndex < fileAndParamNames.size()) {
                                    fileIndex++;
                                }
                                allHaveDoneAddUpProgress += total;
                                allAddUpProgress = allHaveDoneAddUpProgress;
                            } else {
                                allAddUpProgress = allHaveDoneAddUpProgress + progress;
                            }
                            currentOneProgress = progress;
                            currentOneTotalProgress = total;
                            allTotalProgress = entity.getFilesTotalSize();
//                            Log.i("progress_s", "" + progress + "  " + total + " " + done);
                        }
                    });
            ////注意：paramName就是与服务器对应的key,后面filename是服务器得到的文件名
            Log.w("TAG", "------7---" + fileAndParamName.paramName + "\"; filename=\"" + file.getName());

            params.put(fileAndParamName.paramName + "\"; filename=\"" + file.getName(), uploadRequestBody);
        }
        return params;
    }
}
