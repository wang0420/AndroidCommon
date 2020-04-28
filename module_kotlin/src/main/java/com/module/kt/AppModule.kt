package com.module.kt

import com.module.kt.presenter.UserPresenter
import com.module.kt.presenter.UserRepo
import com.module.kt.presenter.UserRepoImpl
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
    single<UserRepo> { UserRepoImpl() }

    //创建个依赖对象使用 Koin 注入给它
    factory { UserPresenter(get()) }

}