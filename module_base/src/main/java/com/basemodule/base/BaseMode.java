package com.basemodule.base;

import com.basemodule.bean.DataBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangwei on 2019/4/17.
 */

public abstract class BaseMode {

    private CompositeDisposable mCompositeDisposable;

    /**
     * 数据请求
     */
    public void addSubscriptionStartRequest(Observable observable, BaseObserver<DataBean> observer) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(observer);
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
    }


    //RxJava取消注册，以避免内存泄露
    public void onUnSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable=null;
        }

    }


//    //上传文件（图片）和参数
//    public MultipartBody.Part getUploadFile(File file, final UpLoadCallBack callBack) {
//        final ProgressDialog progressDialog = new ProgressDialog((Context) iView);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        progressDialog.setMessage("上传中...");
//        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part part = MultipartBody.Part.createFormData("file_name", file.getName(),
//                new ProgressRequestBody(requestBody, new UploadProgressListener() {
//                    @Override
//                    public void onProgress(final long currentCount, final long totalCount) {
//                        Observable.just(currentCount)
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .subscribe(new Observer<Long>() {
//                                    @Override
//                                    public void onSubscribe(Disposable d) {
//
//                                    }
//
//                                    @Override
//                                    public void onNext(Long aLong) {
//                                        if (null != callBack) {
//                                            callBack.success(aLong);
//                                        }
//                                        progressDialog.setMax((int) totalCount);
//                                        progressDialog.setProgress((int) currentCount);
//                                        progressDialog.show();
//                                        new Handler().postDelayed(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                if (currentCount == totalCount) {
//                                                    progressDialog.dismiss();
//                                                }
//                                            }
//                                        }, 1200);
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable e) {
//
//                                    }
//
//                                    @Override
//                                    public void onComplete() {
//
//                                    }
//                                });
//                    }
//                }));
//        return part;
//    }
//
//    public HashMap<String, RequestBody> getUploadParameter(HashMap<String, String> map) {
//        HashMap<String, RequestBody> hashMap = new HashMap<>();
//        for (String key : map.keySet()) {
//            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), map.get(key));
//            hashMap.put(key, requestBody);
//        }
//        return hashMap;
//    }


}
