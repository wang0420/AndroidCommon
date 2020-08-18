package com.module.ui.widget.danmuku

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.newcommon.recyleview.LayoutManager.FixOOBLinearLayoutManager
import com.module.ui.R
import kotlinx.android.synthetic.main.layout_show_live_danmaku.view.*
import java.util.*

/**
 * @author wangwei
 * @date 2020/8/18.
 */
class ShowDanmakuLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr), IShowDanmakuHandler {

    private var mDanMuKuAdapter: ShowLiveDanmakuAdapter? = null
    private var danMuKuList = LinkedList<ShowLiveDanmakuEntity>()
    private var mIsLastItemVisible = true

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_show_live_danmaku, this)
        initData()
        setListener()
    }


    private fun initData() {
        recycler_view?.run {
            layoutManager = FixOOBLinearLayoutManager(context)
            mDanMuKuAdapter = ShowLiveDanmakuAdapter(context, danMuKuList)
            adapter = mDanMuKuAdapter
        }
    }

    private fun setListener() {
        recycler_view?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    recycler_view?.run {
                        val lastVisible = (recycler_view.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                        val itemCount: Int = (recycler_view.layoutManager as LinearLayoutManager).itemCount
                        Log.w("TAG", "-----${lastVisible}---${itemCount}")
                        //这是一个Int类型的扩展函数，用于判断某个值是否大于传入默认最小值，如果大于就直接返回这个值，否则返回这个默认最小值
                        Log.w("TAG", "----->>${(-7).coerceAtLeast(0)}")

                    }


                }
            }

        })

    }

    //IM消息
    override fun handleOtherDanmaku(message: String?) {
        if (message == null) return
        addDanMuKus(ShowLiveDanmakuEntity(type = 12, template = message))
    }

    private fun addDanMuKus(entity: ShowLiveDanmakuEntity) {
        danMuKuList.add(entity)
        mDanMuKuAdapter?.notifyDataSetChanged()
        scrollToNewItem()
    }


    private fun scrollToNewItem() {
        mIsLastItemVisible = true
        mDanMuKuAdapter?.run {
            postDelayed({
                recycler_view?.scrollToPosition(itemCount - 1)
            }, 100)
        }
    }


    fun reset() {
        danMuKuList.clear()
        mDanMuKuAdapter?.notifyDataSetChanged()
    }
}