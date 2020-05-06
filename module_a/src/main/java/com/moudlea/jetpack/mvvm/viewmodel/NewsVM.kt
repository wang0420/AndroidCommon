package com.moudlea.jetpack.mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
//：ViewMode中不能引用任何View的实例，也不能引用任何持有Activity或者Context的实例。如果有些请求数据的情况必须用到Context，在继承ViewMode的时候，
// 可以改为继承AndroidViewMode，这个类会返回一个带有Context的构造函数。

class NewsVM(application: Application) : AndroidViewModel(application) {

    private val mModel by lazy {
        RealModel()
    }

    val liveData = MutableLiveData<String>()

    fun loadData() {
        var result = mModel.loadDataFromNet()
        liveData.value = result
    }

}

