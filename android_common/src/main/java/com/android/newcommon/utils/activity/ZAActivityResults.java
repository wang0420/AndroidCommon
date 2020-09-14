package com.android.newcommon.utils.activity;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;


/**
 * Created by xiangyang on 2020/6/4.
 */
public class ZAActivityResults {

   private static final String TAG = ZAActivityResults.class.getSimpleName();


    Lazy<ZABlankFragment> mZABlankFragment;



    public ZAActivityResults(@NonNull FragmentActivity activity) {
        mZABlankFragment = this.getLazySingleton(activity.getSupportFragmentManager());
    }

    public ZAActivityResults(@NonNull Fragment fragment) {
        mZABlankFragment = this.getLazySingleton(fragment.getChildFragmentManager());
    }



    @NonNull
    private ZAActivityResults.Lazy<ZABlankFragment> getLazySingleton(@NonNull final FragmentManager fragmentManager) {
        return new Lazy<ZABlankFragment>() {
            private ZABlankFragment zABlankFragment;
            @Override
            public synchronized ZABlankFragment get() {
                if (zABlankFragment == null) {
                    zABlankFragment = ZAActivityResults.this.getZABlankFragment(fragmentManager);
                }
                return zABlankFragment;
            }
        };
    }

    private ZABlankFragment getZABlankFragment(@NonNull FragmentManager fragmentManager) {
        ZABlankFragment zABlankFragment = this.findZABlankFragment(fragmentManager);
        boolean isNewInstance = zABlankFragment == null;
        if (isNewInstance) {
            zABlankFragment =  ZABlankFragment.newInstance();
            fragmentManager.beginTransaction().add(zABlankFragment, TAG).commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }

        return zABlankFragment;
    }

    private ZABlankFragment findZABlankFragment(@NonNull FragmentManager fragmentManager) {
        return (ZABlankFragment) fragmentManager.findFragmentByTag(TAG);
    }


    public void startActivityForResult(Intent intent, int requestCode, IRequestCallback callback){
        mZABlankFragment.get().startForResult(intent,requestCode,callback);
    }



    @FunctionalInterface
    public interface Lazy<V> {
        V get();
    }


}
