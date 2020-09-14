package com.android.newcommon.utils.activity;

import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


/**
 * Created by xiangyang on 2020/6/4.
 */

public class ZAActivityHelper {

    /**
     *
     * @param fragmentActivity
     * @param intent
     * @param requestCode 在fragmentActivity的scope下同时使用此方法的requestCode必须不同，否则回调会被覆盖
     * @param callback
     */
    public static void startActivityForResult(FragmentActivity fragmentActivity, Intent intent, int requestCode, IRequestCallback callback) {

        ZAResultsFactory.create(fragmentActivity).startActivityForResult(intent, requestCode, callback);

    }

    /**
     *
     * @param fragment
     * @param intent
     * @param requestCode 在fragment的scope下同时使用此方法的requestCode必须不同，否则回调会被覆盖
     * @param callback
     */
    public static void startActivityForResult(Fragment fragment, Intent intent, int requestCode, IRequestCallback callback) {

        ZAResultsFactory.create(fragment).startActivityForResult(intent, requestCode, callback);

    }

    static class ZAResultsFactory {
        static ZAActivityResults create(FragmentActivity fragmentActivity) {
            return new ZAActivityResults(fragmentActivity);
        }

        static ZAActivityResults create(Fragment fragment) {
            return new ZAActivityResults(fragment);
        }
    }

}
