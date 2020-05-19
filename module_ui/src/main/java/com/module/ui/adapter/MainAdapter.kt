package com.module.ui.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.module.ui.R
import com.module.ui.bean.UIItem

import java.util.ArrayList

import androidx.recyclerview.widget.RecyclerView
import com.module.ui.activity.UIActivity

/**
 * Created by wangwei on 2018/9/27.
 */

class MainAdapter(val mContext: Activity) : RecyclerView.Adapter<MainAdapter.MyViewHolder>() {
    var mData = ArrayList<UIItem>()
    private var mOnItemClickListener: OnItemClickListener? = null

    //初始化数据
    init {

        Log.w("TAG", "---init${UIActivity.start("4566")}" )
        Log.w("TAG", "---init" + UIActivity.Companion.name)

    }
    fun addItem(item: UIItem) {
        mData.add(item)
    }

    fun getItem(index: Int): UIItem {
        return mData[index]
    }

    fun addAll(data: List<UIItem>) {
        mData.addAll(data)
    }


    interface OnItemClickListener {
        fun onItemClick(index: Int)
    }

    fun setOnItemClickListener(lis: OnItemClickListener) {
        this.mOnItemClickListener = lis
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflate = LayoutInflater.from(mContext).inflate(R.layout.main_item, parent, false)
        return MyViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val oj = mData[position].title
        upDateHolderView(holder, oj, position)
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener { mOnItemClickListener!!.onItemClick(position) }
        }
    }

    private fun upDateHolderView(holder: MainAdapter.MyViewHolder, oj: String, position: Int) {
        holder.itemTv.text = oj
    }

    override fun getItemCount(): Int {
        return mData.size
    }

     inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemTv: TextView = itemView.findViewById(R.id.item_tv)
       //  var  itemView:View=view


     }

}



