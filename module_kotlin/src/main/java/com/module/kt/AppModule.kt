package com.module.kt

import android.util.Log
import com.module.kt.activites.FragActivity
import com.module.kt.bean.*
import com.module.kt.fragment.MyFragment
import com.module.kt.presenter.UserPresenter
import com.module.kt.presenter.UserRepo
import com.module.kt.presenter.UserRepoImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 *  Created by wangwei on 2020/4/28.
 */
@JvmField
val appModule = module {
    //添加 Service 有两个函数分别是 factory 与 single。
    // 区别在于前者将在每次被需要时都创建（获取）一个新的实例，
    // 也就是说后边代码块将被多次运行。而 single 会让 Koin
    // 保留实例用于今后直接返回，
    //创建一个单例的Bean 也可以通过不同的别名去声明不同一种类型的Bean
    single<UserRepo> { UserRepoImpl() }

    //创建个依赖对象使用 Koin 注入给它,
    //factory提供一个工厂(每次注入都会创建新的) Bean
    //get() 方法使用获取了 声明的UserRepoImpl()
    factory {
        UserPresenter(get()) }


    //Koin的三种注入方式 里面添加各种注入对象
    factory {
        Person()//普通的注入方式
    }
    single {
        UserData()//单例的注入方式
    }
    viewModel {
        MyViewModel()
    }
    factory { AppData(get()) }

    scope(named("myScope")) {//scope类型的注入方式一,通过标签的方式
        //,其他界面通过scopeId可以获取这个对象.当该视图被销毁的时候,被绑定的对象也会被销毁.其他界面也就获取不到这个scope对象了.
      /*Scope 用于控制对象在 Koin 内的生命周期。类似于生命周期，我们可以控制它的存活范围。事实上，
      前面所讲的 single 与 factory 都是 scope。single 创建的对象在整个容器的生命周期内都是存在的，
      因此任意地方注入都是同一实例。factory 每次都创建新的对象，因此它不被保存，也不能共享实例。*/
        scoped {
            ScopeData()
        }
    }


    //fragment注入
    scope(named<FragActivity>()){
        scoped {
            MyFragment("张三")
        }
    }


}