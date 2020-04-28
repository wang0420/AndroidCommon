package com.module.kt

import com.module.kt.presenter.UserPresenter
import com.module.kt.presenter.UserRepo
import com.module.kt.presenter.UserRepoImpl
import org.koin.dsl.module

/**
 *  Created by wangwei on 2020/4/28.
 */

val appModule = module {
    single<UserRepo> { UserRepoImpl() }
    factory { UserPresenter(get()) }

}