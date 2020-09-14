package com.android.newcommon.utils.activity;

import android.content.Intent;


/**
 * Created by xiangyang on 2020/6/4.
 */

public interface IRequestCallback {

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
