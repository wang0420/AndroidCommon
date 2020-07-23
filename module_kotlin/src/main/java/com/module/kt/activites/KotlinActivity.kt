package com.module.kt.activites

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.android.common.ARouterManager
import com.module.kt.R
import kotlinx.android.synthetic.main.activity_kotlin.*


@Route(path = ARouterManager.KotlinActivity)
class KotlinActivity : AppCompatActivity() {
    //变量生命
    var age = 23//可变
    val name = "张三"//不可变

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_kotlin)
        //、let,with,run,apply,also函数区别
        //https://blog.csdn.net/u013064109/article/details/78786646

        //with 函数的使用  与let 的区别可带返回值
        var ee = with(button) {
            setText("Click")
            setTextSize(20f)
            setTextColor(Color.BLACK)
            10  //with 函数是可以返回值  返回值为函数块的最后一行或指定return表达式。
        }
        println("----->with--$ee")


        //另一种用途 判断object为null的操作
        //从源码let函数的结构来看它是只有一个lambda函数块block作为参数的函数,
        // 调用T类型对象的let函数，则该对象为函数的参数。
        //在函数体内使用it替代object对象去访问其公有的属性和方法,返回值为函数块的最后一行或指定return表达式。
        var ww = button?.let {
            println("----->let--$it")
            it.setTextColor(this.resources.getColor(R.color.colorAccent))
            it.text = "let表达式"
            122

        }
        println("----->let--$ww")
        //以闭包形式返回  run  不用像let 一样用it 去访问
        //返回值为最后一行的值或者指定的return的表达式。
        var ac = button?.run {
            setText("Click")
            setTextSize(20f)
            setTextColor(Color.BLACK)
            0  //可以返回值  可写可不写
        }
        println("----->run--$ac")

        //apply函数的用法  该函数与run函数基本相同，
        // 只不过run函数返回的是最后一行的值，apply 函数返回的是传入的对象
        var pp = button?.apply {
            text = "Click"
            textSize = 20f
            setTextColor(Color.BLACK)

        }
        println("----->pp--$pp")


        //also函数的结构实际上和let很像唯一的区别就是返回值的不一样，
        // let是以闭包的形式返回，返回函数体内最后一行的值，如果最后一行为空就返回一个Unit类型的默认值。
        // 而also函数返回的则是传入对象的本身
        button?.also {
            println("----->also--$it")
            it.setTextColor(this.resources.getColor(R.color.colorAccent))
            it.text = "let表达式"
        }


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

        //截取前n个元素为子集合
        val mList = arrayListOf(0, 1, 2, 3)
        val mNewList = mList.take(2)
        println(mNewList)//[0, 1]


        foo()
        foo("a")
        foo("aa", 1)
        foo("aaa", 1, "cc")


    }

    /*在Kotlin中@JvmOverloads注解的作用就是：在有默认参数值的方法中使用@JvmOverloads注解，则Kotlin就会暴露多个重载方法。*/
    @JvmOverloads
    fun foo(a: String = "a", b: Int = 0, c: String = "c") {
        println(a)
        println(b)
        println(c)
    }

    //vararg 关键字，表示 可变参数，即可接收多个同类参数，可变参数，类似于数组
    private fun getNum(vararg a: Int) {
        println("------KotlinActivity : " + a[0])
        println("------KotlinActivity : " + a[3])
    }


    //匿名函数的定义，lambda形式   接受2个参数(数字),并返回他们的差值
    val sumLambda: (Int, Int) -> Int = { x, y -> x + y }

    private fun circle() {
        /*mutableListOf()：该函数返回可变的MutableList集合。该函数可接受0个或多个参数，这些参数将作为集合的元素。
arrayListOf()：该函数返回可变的ArrayList集合。该函数可接受0个或多个参数，这些参数将作为集合的元素。
*/
        val datas = mutableListOf<String>()
        val datas1 = arrayListOf<String>()
        val datas2 = arrayListOf(1, 23, 32)


        val array = arrayOf(1, 23, 32)

        for (item in array) println("-------$item")

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