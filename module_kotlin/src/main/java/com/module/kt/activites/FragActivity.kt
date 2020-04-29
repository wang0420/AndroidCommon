package com.module.kt.activites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.module.kt.R
import com.module.kt.fragment.MyFragment
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import org.koin.androidx.scope.currentScope

/**
 *  Created by wangwei on 2020/4/29.
 */

class FragActivity : AppCompatActivity() {
    val myFrag: MyFragment by currentScope.inject()

    override fun onCreate(savedInstanceState: Bundle?) {
       // setupKoinFragmentFactory()//要在调用父类的方法之前调用
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frag)
       /* supportFragmentManager.beginTransaction()
                .replace(R.id.mvvm_frame, MyFragment::class.java, null, null)
                .commit()*/

        supportFragmentManager.beginTransaction()
                .replace(R.id.mvvm_frame,myFrag)
                .commit()
    }
}