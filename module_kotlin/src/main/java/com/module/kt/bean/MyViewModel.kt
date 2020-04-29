package com.module.kt.bean

import androidx.lifecycle.ViewModel
import com.module.kt.CSKoinLog

/**
 *  Created by wangwei on 2020/4/28.
 */

class MyViewModel : ViewModel() {
    var NumData: Int = 0
    override fun onCleared() {
        super.onCleared()
        CSKoinLog.I("调用了销毁方法")
    }

}