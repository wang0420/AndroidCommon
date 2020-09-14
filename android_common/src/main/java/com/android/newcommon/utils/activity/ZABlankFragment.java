package com.android.newcommon.utils.activity;

import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;


/**
 * Created by xiangyang on 2020/6/4.
 */

/**
 * 一个空的fragment用来处理startActivityForResult的逻辑并回调出去
 * 生命周期同activity或者fragment的生命周期绑定
 */
public class ZABlankFragment extends Fragment {

    private Map<Integer, IRequestCallback> mCallbacks = new HashMap<>();

    public static ZABlankFragment newInstance() {
        return new ZABlankFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }


    public void startForResult(Intent intent, int requestCode, IRequestCallback callback) {
        mCallbacks.put(requestCode, callback);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IRequestCallback callback = mCallbacks.remove(requestCode);
        if (callback != null) {
            callback.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
