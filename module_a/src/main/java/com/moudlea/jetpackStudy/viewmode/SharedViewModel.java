package com.moudlea.jetpackStudy.viewmode;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

/**
 * Created by wangwei on 2020/4/29.
 * MasterFragment与DetailFragment并不直接进行交互，而是各自与ViewMode进行交互，
 * MasterFragment用来更新维护ViewMode中的数据，DetailFragment可以收到来自ViewMode中数据更新的通知。
 * 这样便达到了两个frangment之间的数据通信。
 */

public class SharedViewModel extends AndroidViewModel {
    //userLiveData保存的是被选中的item的状态或者数据
    private MutableLiveData<String> userLiveData;


    public MutableLiveData<String> getUserLiveData() {
        if (userLiveData == null) {
            Log.w("TAG", "SharedViewModel-getUserLiveData");
            userLiveData = new MutableLiveData<>();

        }
        return userLiveData;
    }

    //主要通过masterFragment进行调用交互，用来更新selected中的值
    public void select(String item) {
        userLiveData.setValue(item);
    }


    public SharedViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 这里可以执行一些资源释放、数据清理的操作
     * ViewMode会执行onCleared操作，这个是ViewMode的一个回调，
     * 表明当前Activity要彻底关闭，ViewMode需要做一些回收清理的操作，如下代码：
     */
    @Override
    protected void onCleared() {
        super.onCleared();

    }
}
