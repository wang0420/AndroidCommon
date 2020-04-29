package com.module.kt

/**
 *  Created by wangwei on 2020/4/28.
 */
import android.util.Log
import org.koin.core.scope.ScopeID

object CSKoinLog {
    fun I(str: String) {
        Log.w("TAG", str)
    }
    //var scopeId :ScopeID = ""//保存Scopeid
}

