package com.module.kt.activites

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.basemodule.ARouterManager
import com.module.kt.R


@Route(path = ARouterManager.KotlinActivity)
class KotlinActivity : AppCompatActivity() {
    //变量生命
    var age = 23//可变
    val name = "张三"//不可变

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        //vararg 关键字多参数
        getNum(1, 2, 3, 4, 9)
        println("-----${sumLambda(1, 32)}")// 接受2个参数(数字),并返回他们的差值
        println("-----$lazyStr")
        println("--------->>>>$lazyStr")
        val words = listOf("Lets", "find", "something", "in", "collection", "somehow")
        //寻找集合里面的制定值  没有则返回null
        println("-----${words.find { it == "find" }}")
        words.find { it == "find1" }?.run { println("--------->>>>找到了find") }
                ?: run { println("--------->>>为null则执行这里") }
        //循环
        circle()

        // .. 符号表示范围 range且是 [ ]左右都闭合的区间
        for (i in 1..4) {
            println("---------$i") //i++
        }
        //这样不会输出，想要i-- 需要down to
        for (i in 4 downTo 0) {
            println("--------s-$i")//i--
        }
        for (i in 1..10 step 2) {
            println("--------s-$i")//跳过2
        }
        for (i in 8 downTo 3 step 2) {//i--跳过2
            println(i)
        }

        for (i in 1 until 7) {
            println("--------s-$i") //左闭右开 [ ) 使用until
        }

        //if else
        var abc = 0
        var a = 2
        var b = 12
        abc = if (a < b) 1 else 3
        if (a in 0..3) abc = 100



        //when 类似于switch case
        var x = 1
        when (x) {
            1 -> {
                Log.w("kotlin----", "x == 1")
            }
            2 -> {
                Log.w("kotlin----", "x == 2")
            }
            else -> { // 注意这个块类似于default
                Log.w("kotlin----", "\"x 不是 1 ，也不是 2\"")

            }
        }


    }

    //vararg 关键字，表示 可变参数，即可接收多个同类参数，可变参数，类似于数组
    private fun getNum(vararg a: Int) {
        println("------KotlinActivity : " + a[0])
        println("------KotlinActivity : " + a[3])
    }


    //匿名函数的定义，lambda形式   接受2个参数(数字),并返回他们的差值
    val sumLambda: (Int, Int) -> Int = { x, y -> x + y }

    private fun circle() {

        val array = arrayOf(1, 23, 32)

        for (item in array)  println("-------$item")

        for (j in array.indices) println("-------${array[j]}")

        for ((index, value) in array.withIndex()) {
            println("-------item $index is $value")

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

    // kotlin都是封装类型，不能自动的类型转换,lazy懒加载也可以
    private val lazyStr: String by lazy {
        println("-----这条语句，只会在第一次加载时候调用，再次调用这个变量的时候，就不会打印了");
        "-----懒加载的lazyStr"
    }


}