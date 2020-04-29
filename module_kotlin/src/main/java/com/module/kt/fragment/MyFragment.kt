package com.module.kt.fragment

import android.view.View
import android.widget.TextView
import com.basemodule.BaseFragment
import com.module.kt.R

/**
 *  Created by wangwei on 2020/4/29.
 */

class MyFragment(var str: String) : BaseFragment() {
    var  frag_text: TextView? = null
    override fun initData() {
    }

    override fun initLayout(): Int = R.layout.fragment_my


    override fun initView(view: View?) {


        view?.run {
            frag_text = this.findViewById(R.id.frag_text)
            frag_text?.text = "\"在碎片中的参数:\" + $str"
        }
    }


}