package com.module.kt.activites

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.basemodule.ARouterManager
import com.module.kt.Girl
import com.module.kt.R
import com.module.kt.presenter.UserPresenter
import kotlinx.android.synthetic.main.activity_zhijie.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

/**
 *  Created by wangwei on 2020/4/28.
 *  https://www.jianshu.com/p/bccb93a78cee?utm_campaign=shakespeare&utm_content=note&utm_medium=seo_notes&utm_source=recommendation
 */

@Route(path = ARouterManager.ZhuJieActivity)
class ZhuJieActivity : AppCompatActivity() {


    val girl: Girl = get()


    //调用方式一,为了再Activity中使用，我们通过by inject()来注入它。by inject()方法允许我们在组件运行时（Activity, fragment, Service...）获取去Koin实例，
    private val userPresenter: UserPresenter by inject()
    //调用方式二
    // val userPresenter: UserPresenter = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zhijie)
        tv_inject.text = userPresenter.sayHi()
        /*  Log.w("TAG", "--->" + userPresenter.sayHi())
          Log.w("TAG", "--->" + girl.urlGirl())*/


        //普通注入使用方式--Factory注入--通过by inject(),get<>()这几种方法就可以获取被注入的对象,
        // 从日志上就可以看到,每次调用之后,都会生成一个新的对象
        /* val person1: Person by inject()//方法一
         val person2 by inject<Person>()//方法二
         val person3 = get<Person>()//方法三
         val person4: Person = get()//方法四
         Log.w("TAG", "--->>" + person1.speak())
         Log.w("TAG", "--->>" + person2.speak())
         Log.w("TAG", "--->>" + person3.speak())
         Log.w("TAG", "--->>" + person4.speak())
         Log.w("TAG", "--->>" + person1.hashCode().toString())
         Log.w("TAG", "--->>" + person2.hashCode().toString())*/

//单例模式--Single用法我们可以看到,
// 2个UserDate的对象的地址位是同一个,然后一个属性改变之后,另一个也对应地改变.
        /* val userData: UserData by inject()
         val userData2: UserData by inject()
         CSKoinLog.I(userData.hashCode().toString())
         CSKoinLog.I(userData2.hashCode().toString())

         userData.userName = "张飞"
         userData.age = 17
         userData2.info()

         userData2.userName = "关羽"
         userData2.age = 18
         userData.info()
 */

        //viewModel用法(官方原文)
        /*在ViewModelActivity中,我获取了2个viewModel对象,里面写了个按钮,
        点击可以改变viewModel中的属性,大家可以看到,这2个viewModel都是同一个,
        然后我修改了里面的值,接着我将手机屏幕变成横屏(这个看实际操作),再变成竖屏,
        Activity重新调用生命周期,但是viewModel仍旧是那个viewModel(地址不变),
        接着退出该界面,发现viewModel中的clear回调被调用了.

*/
/*        val myViewModel: MyViewModel by viewModel()
        val myViewModel2 by viewModel<MyViewModel>()

        CSKoinLog.I(myViewModel.hashCode().toString())
        CSKoinLog.I(myViewModel2.hashCode().toString())
        CSKoinLog.I(myViewModel.NumData.toString())

        findViewById<TextView>(R.id.tv_inject).setOnClickListener {
            myViewModel.NumData = 1
            CSKoinLog.I(myViewModel.NumData.toString())
        }*/

        //Scope---作用域的使用
        /*  val scope = getKoin().createScope("scopeId1", named("myScope"))//创建scope方式一
          bindScope(scope)//scope与界面绑定,只有这边创建绑定了之后,其他地方才能获取到这个作用域
          val scopeData = scope.get<ScopeData>()//获取作用域下的类
          CSKoinLog.I("ScopeCurentActivity中的ScopeData是否为空:" + (scopeData == null))
          CSKoinLog.I("ScopeCurentActivity中的ScopeData地址:" + (scopeData.hashCode()))*/

        findViewById<TextView>(R.id.tv_inject).setOnClickListener {
            startActivity(Intent(this, FragActivity::class.java))
        }
    }
}
