package com.module.kt

import org.koin.dsl.module

/**
 *  Created by wangwei on 2020/4/28.
 */
@JvmField
val girlModule = module {

    factory { Girl() }


}