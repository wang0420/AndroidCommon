package com.module.kt.fragment

import android.view.View
import com.basemodule.BaseFragment
import com.module.kt.R
import kotlinx.android.synthetic.main.fragment_my.*


/**
 *  Created by wangwei on 2020/4/29.
 */

class MyFragment(var str: String) : BaseFragment() {

    override fun initData() {
    }

    override fun initLayout(): Int = R.layout.fragment_my


    override fun initView(view: View?) {
        frag_text.text = "\"在碎片中的参数:\" + $str"
    }


}