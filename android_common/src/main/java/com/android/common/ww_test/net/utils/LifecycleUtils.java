package com.android.common.ww_test.net.utils;

import android.app.Activity;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import androidx.fragment.app.Fragment;
import io.reactivex.Observable;



public class LifecycleUtils {

    private LifecycleUtils() {
    }

    public static <T> Observable<T> bindLifecycle(Observable<T> observable, LifecycleProvider lifecycleProvider) {
        if (lifecycleProvider != null) {
            if (lifecycleProvider instanceof Activity) {
                return observable.compose(lifecycleProvider.bindUntilEvent(ActivityEvent.DESTROY));
            } else if (lifecycleProvider instanceof Fragment || lifecycleProvider instanceof android.app.Fragment) {
                return observable.compose(lifecycleProvider.bindUntilEvent(FragmentEvent.DESTROY));
            }
        }
        return observable;
    }
}
