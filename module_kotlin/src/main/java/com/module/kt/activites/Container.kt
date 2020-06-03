package com.module.kt.activites

/**
 * 请添加注释说明
 * @author wangwei
 * @date 2020/6/3.
 */


import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout

class ContainerViewOne : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var container = Container(this)
        container.setListener { string ->
            return@setListener "item " + string
        }
        setContentView(container, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
    }


    /**
     * 以下介绍了 let、also、with、apply、run函数的用法及不同
     */
    class Container : FrameLayout {

        lateinit var onItemClic: (String) -> String

        constructor(context: Context) : super(context) {

            var framLayout = FrameLayout(context)
            addView(framLayout)
            var button = Button(context)

            //let 函数的使用
            /*  button?.let {
                  it.setTextColor(Color.BLACK)
                  it.setText("Click")
                  it.setTextSize(20f)
                  it.setOnClickListener{ view ->
                      var str = onItemClic?.invoke(view.toString())
                      Log.d("TAG","str:"+str)
                  }
              }*/

            //also 函数和let函数基本一样，不同的是also返回的是当前的对象
            /*button?.also {
                it.setTextColor(Color.BLACK)
                it.setText("Click")
                it.setTextSize(20f)
                it.setOnClickListener { view ->
                    var str = onItemClic?.invoke(view.toString())
                    Log.d("TAG", "str:" + str)
                }
            }*/

            //with 函数的使用  与let 的区别可带返回值
            /*with(button){
                setText("Click")
                setTextSize(20f)
                setTextColor(Color.BLACK)
                setOnClickListener{ view ->
                    var str = onItemClic?.invoke(view.toString())
                    Log.d("TAG","str:"+str)
                }
                0  //with 函数是可以返回值  可写可不写
            }*/

            //run函数的用法   结合了with和let共同的优点   可判断空 可带返回值
            /*button?.run {
                setText("Click")
                setTextSize(20f)
                setTextColor(Color.BLACK)
                setOnClickListener{ view ->
                    var str = onItemClic?.invoke(view.toString())
                    Log.d("TAG","str:"+str)
                }
                0  //可以返回值  可写可不写
            }*/

            //apply函数的用法  该函数与run函数基本相同，
            // 只不过run函数返回的是最后一行的值，apply 函数返回的是传入的对象
            button?.apply {
                setText("Click")
                setTextSize(20f)
                setTextColor(Color.BLACK)
                setOnClickListener { view ->
                    var str = onItemClic?.invoke(view.toString())
                    Log.d("TAG", "str:" + str)
                }
            }

            var layoutparams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
            layoutparams.gravity = Gravity.CENTER
            framLayout.addView(button, layoutparams)
        }

        /**
         * (String)  接受的参数
         *  -> String  返回类型
         */
        fun setListener(onItem: (String) -> String) {
            this.onItemClic = onItem
        }
    }

}
