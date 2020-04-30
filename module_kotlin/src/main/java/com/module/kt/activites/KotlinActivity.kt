package com.module.kt.activites

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.basemodule.ARouterManager
import com.module.kt.R

/**
 *  Created by wangwei on 2020/4/30.
 */

@Route(path = ARouterManager.KotlinActivity)
class KotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        //多参数
        getSum(1, 2, 3, 4, 9)
        //匿名函数定义
        sumLambda(1, 32)

        Log.w("TAG", lazyStr);
    }

    //匿名函数的定义，lambda形式
    val sumLambda: (Int, Int) -> Int = { x, y -> x + y }

    //vararg 关键字，表示 可变参数，即可接收多个同类参数
    fun getSum(vararg a: Int) {
        //可变参数，类似于数组
        println("KotlinActivity.getSum : " + a[0])
        println("KotlinActivity.getSum : " + a[3])
    }


    fun circle() {

        val array = arrayOf(1, 23, 32)

        for (item in array) println(item)

        for (j in array.indices) println(array[j])

        for ((index, value) in array.withIndex()) {

            print("item $index is $value")
        }
    }

    // .. 符号表示范围 range且是 [ ]左右都闭合的区间
    fun conditation() {
        //i++
        for (i in 1..4) {
            println(i)
        }
        //这样不会输出，想要i-- 需要down to
        for (i in 4..0) {
            println(i)
        }
        //step
        for (i in 1..10 step 2) {
            println(i)
        }
        //i--
        for (i in 8 downTo 3 step 2) {
            println(i)
        }
        //左闭右开 [ ) 使用until
        for (i in 1 until 7) {
            println(i)
        }
        //if else
        var abc = 0
        if (1 < 2) abc = 1 else abc = 3

        if (1 in 0..3) abc = 100

        //when 类似于switch case
        var x = 1009
        when (x) {
            0 -> abc = 0//类似于case
            2 -> abc = 3
            in 10..100 -> abc = 2300
            is Int -> println("int")
            !is Int -> println("! Int")

            else -> abc = 200//类似于default
        }
    }

    //Kotlin的null判断，? 问号表示可能为null，会抛出异常。!!则表示 断定非 null
    fun parseInt(s: String): Int? {
        return s.toInt()
    }

    // 带参数，返回值的函数 可以简写为  fun getSum(a: Int, b: Int)=a+b
    fun getSum(a: Int, b: Int): Int {
        return a + b
    }

    //todo kotlin都是封装类型，不能自动的类型转换,lazy懒加载也可以
    val lazyStr: String by lazy {
        Log.w("TAG", "这条语句，只会在第一次加载时候调用，再次调用这个变量的时候，就不会打印了");
        "懒加载的返回值"
    }


}