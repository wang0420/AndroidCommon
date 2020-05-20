package com.module.kt.bean

/**
 * 抽象和继承，接口
 * @author wangwei
 * @date 2020/5/20.
 */

//声明接口
interface IMain {
    fun xiaodi()
    fun ear() {}//接口默认实现

}

//抽象类
abstract class Human(var name: String) {

    abstract fun eat()//抽象方法
    //  abstract fun  pee()
}

//: 继承
class Man(name: String) : Human(name), IMain {


    override fun xiaodi() {
    }

    //实现抽象方法
    override fun eat() {
    }
}