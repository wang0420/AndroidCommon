package com.module.kt.bean

import android.util.Log

/**
 *  Created by wangwei on 2020/4/28.
 */
class UserData {
    var userName: String? = null
    var age: Int? = null
    fun info() {
        Log.w("TAG", "用户名:" + userName + "////年龄:" + age)
    }
}
