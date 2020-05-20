package com.module.ui.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.module.ui.R
import com.module.ui.activity.UIActivity
import com.module.ui.bean.UIItem
import java.util.*


/**
 * Created by wangwei on 2018/9/27.
 */

class FlexboxLayoutAdapter(val mContext: Activity) : RecyclerView.Adapter<FlexboxLayoutAdapter.MyViewHolder>() {
    var mData = ArrayList<String>()
    private var mOnItemClickListener: OnItemClickListener? = null

    //初始化数据
    init {

        Log.w("TAG", "---init${UIActivity.start("4566")}" )
        Log.w("TAG", "---init" + UIActivity.Companion.name)

    }
    fun addItem(item: String) {
        mData.add(item)
    }





    interface OnItemClickListener {
        fun onItemClick(index: Int)
    }

    fun setOnItemClickListener(lis: OnItemClickListener) {
        this.mOnItemClickListener = lis
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflate = LayoutInflater.from(mContext).inflate(R.layout.main_item_image, parent, false)
        return MyViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val oj = mData[position]
        upDateHolderView(holder, oj, position)
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener { mOnItemClickListener!!.onItemClick(position) }
        }
    }

    private fun upDateHolderView(holder: FlexboxLayoutAdapter.MyViewHolder, oj: String, position: Int) {
        holder.itemTv.text = oj
        val lp = holder.itemTv.getLayoutParams()

        if (lp is FlexboxLayoutManager.LayoutParams) {
            val flexboxLp = holder.itemTv.getLayoutParams() as FlexboxLayoutManager.LayoutParams
            flexboxLp.flexGrow = 1.0f
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

     inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemTv: TextView = itemView.findViewById(R.id.item_tv)
       //  var  itemView:View=view


     }

}



