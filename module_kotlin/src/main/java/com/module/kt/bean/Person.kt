package com.module.kt.bean

import android.util.Log

/**
 *  Created by wangwei on 2020/4/28.
 */

class Person {
    var name: String? = null
    var age: Int? = null
    fun speak() {
        Log.w("TAG", "武汉加油,中国加油")
    }
}