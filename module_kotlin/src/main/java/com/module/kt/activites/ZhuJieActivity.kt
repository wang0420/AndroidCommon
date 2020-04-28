package com.module.kt.activites

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.basemodule.ARouterManager
import com.module.kt.R
import com.module.kt.presenter.UserPresenter
import kotlinx.android.synthetic.main.activity_zhijie.*
import org.koin.android.ext.android.get

/**
 *  Created by wangwei on 2020/4/28.
 */

@Route(path = ARouterManager.ZhuJieActivity)
class ZhuJieActivity : AppCompatActivity() {

    //注入依赖 UserPresenter 和Presenter 实例一起创建。
    // 为了再Activity中使用，我们通过by inject()来注入它。
    // private val userPresenter: UserPresenter by inject()
    //随便get都行
    //val userPresenter: UserPresenter = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zhijie)
        tv_inject.text = "11"
      //  Toast.makeText(this, userPresenter.sayHi(), Toast.LENGTH_LONG).show()

    }
}
