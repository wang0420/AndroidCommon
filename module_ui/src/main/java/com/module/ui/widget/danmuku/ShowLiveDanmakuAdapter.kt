package com.module.ui.widget.danmuku

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.newcommon.utils.CollectionUtils
import com.module.ui.R
import kotlinx.android.synthetic.main.layout_show_live_danmaku_item.view.*

class ShowLiveDanmakuAdapter(private var context: Context, private var danMuKuList: MutableList<ShowLiveDanmakuEntity>)
    : RecyclerView.Adapter<ShowLiveDanmakuAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return CollectionUtils.getSize(danMuKuList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_show_live_danmaku_item, parent, false))
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindViewData(danMuKuList[position])
    }

     class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        fun bindViewData(entity: ShowLiveDanmakuEntity) {
            view.tv_content.text = entity.template
            view.img_send_fail.visibility = if (entity.isSendFailure) View.VISIBLE else View.GONE
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        danMuKuList.clear()


    }
}