package com.moudlea.jetpack.viewmode;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class MyViewMode extends AndroidViewModel {
    //MutableLiveData是对LiveData的扩展，主要实现了set和post方法来方便更改LiveData的值。
    private MutableLiveData<String> userLiveData;

    public MutableLiveData<String> getUsers() {
        if (userLiveData == null) {
            Log.w("TAG", "getUsers");
            userLiveData = new MutableLiveData<>();
            loadUsers();
        }
        return userLiveData;
    }


    private void loadUsers() {

        userLiveData.setValue("初始化数据");
    }

    public void updateData() {
        userLiveData.setValue("数据更新了");
    }


    //：ViewMode中不能引用任何View的实例，也不能引用任何持有Activity或者Context的实例。如果有些请求数据的情况必须用到Context，在继承ViewMode的时候，
    // 可以改为继承AndroidViewMode，这个类会返回一个带有Context的构造函数。
    public MyViewMode(@NonNull Application application) {
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
