package com.basemodule.ww_test.net;

import com.basemodule.ww_test.net.manager.RequestManager;
import com.basemodule.ww_test.net.retrofit.ZRetrofit;
import com.trello.rxlifecycle2.LifecycleProvider;


public class ZNetwork {

    /**
     * 获取service
     * @param service
     * @param <T>
     * @return
     */
    public static <T> T getService(Class<T> service) {
        return ZRetrofit.getInstance().create(service);
    }

    /**
     * 进行网络请求
     *
     * @param lifecycleProvider 绑定生命周期
     * @return 请求管理器
     */
    public static RequestManager with(LifecycleProvider lifecycleProvider) {
        return new RequestManager(lifecycleProvider);
    }

    /**
     * 进行网络请求
     * (不绑定生命周期)
     *
     * @return 请求管理器
     */
    public static RequestManager with() {
        return new RequestManager(null);
    }


    /**
     * 获取service
     * 上传使用
     *
     * @param service
     * @param <T>
     * @return
     */
    public static <T> T getUploadService(Class<T> service) {
        return ZRetrofit.getInstance().create(service);
    }


    public static <T> T getOtherDomainService(Class<T> service, String fullUrl) {
        return ZRetrofit.getInstance().create(service, fullUrl);
    }


}
