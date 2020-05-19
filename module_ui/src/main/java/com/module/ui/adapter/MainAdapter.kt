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

/**
 * Created by wangwei on 2018/9/27.
 */

class MainAdapter(private val mContext: Activity) : RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    private val mLayoutInflater: LayoutInflater

    var mDatas = ArrayList<UIItem>()

    private var mOnItemClickLitener: MainAdapter.OnItemClickLitener? = null

    init {
        mLayoutInflater = LayoutInflater.from(mContext)
    }

    fun addItem(item: UIItem) {
        mDatas.add(item)
    }

    fun getItem(index: Int): UIItem {
        return mDatas[index]
    }

    fun addAll(data: List<UIItem>) {
        mDatas.addAll(data)
    }


    interface OnItemClickLitener {
        fun onItemClick(index: Int)
    }

    fun setOnItemClickLitener(mOnItemClickLitener: MainAdapter.OnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener
    }


    override fun onCreateViewHolder(parent: ViewGroup, i: Int): MyViewHolder {
        val inflate = mLayoutInflater.inflate(R.layout.main_item, parent, false)
        return MyViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: MainAdapter.MyViewHolder, position: Int) {
        val oj = mDatas[position].title
        upDateHolderView(holder, oj, position)
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener { mOnItemClickLitener!!.onItemClick(position) }
        }
        holder.itemView.setOnTouchListener { v, event ->
            Log.w("TAG", "---" + event.action)
            false
        }
    }

    private fun upDateHolderView(holder: MainAdapter.MyViewHolder, oj: String, position: Int) {
        holder.item_tv.text = oj
    }

    override fun getItemCount(): Int {
        return mDatas.size

    }

    internal inner class MyViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item_tv: TextView

        init {
            item_tv = itemView.findViewById(R.id.item_tv)
        }

    }

}



