package com.moudlea.jetpack.mvvm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.moudlea.R
import com.moudlea.jetpack.mvvm.viewmodel.NewsVM
import kotlinx.android.synthetic.main.mvvm_layout.*

/**
 * Created by wangwei on 2020/5/6.
 * 1.mvvm和MVP比较大的区别是：vm和v是单向引用，
 * 只有activity持有vm引用，vm是不持有view的引用的，所以vm的构造方法中不能传入视图相关的对象
 * 所以vm都不應該持有view接口和adapter
 * 之所以会有这种写法，是受限于当时的技术水平和网络论调，在那个时代，网上绝大多数人
 * 都是加了databinding就认为是mvvm了，实际上不是这样的，MVVM是一种架构模式，
 * 而DataBinding是一个实现数据和UI绑定的框架，是构建MVVM模式的一个工具。
 *
 *
 * 2.数据驱动。在常规的开发模式中，数据变化需要更新UI的时候，需要先获取UI控件的引用，然后再更新UI。
 * 获取用户的输入和操作也需要通过UI控件的引用。在MVVM中，这些都是通过数据驱动来自动完成的，
 * 数据变化后会自动更新UI，UI的改变也能自动反馈到数据层，数据成为主导因素。这样MVVM层在业务逻辑处理中
 * 只要关心数据，不需要直接和UI打交道，在业务处理过程中简单方便很多。
 *
 *
 * 3.mvvm解决了mvp中接口繁杂、内存泄漏等疑难杂症。
 */

class MVVMActivity : AppCompatActivity() {
    private lateinit var realVm: NewsVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mvvm_layout)
        Log.w("TAG", lazyStr)
        realVm = ViewModelProviders.of(this).get(NewsVM::class.java)
        realVm.loadData()
        realVm.liveData.observe(this, Observer<String> { result ->
            id_content.text = result
        })

        id_content.setOnClickListener {
            Log.w("TAG", lazyStr)

        }
    }

    //lazy  可实现单例
    val lazyStr: String by lazy {
        Log.w("TAG", "这条语句，只会在第一次加载时候调用，再次调用这个变量的时候，就不会打印了");
        "懒加载的返回值"
    }

}
