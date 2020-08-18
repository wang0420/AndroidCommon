package com.module.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.module.ui.R
import com.module.ui.widget.danmuku.ShowIMMessage
import com.module.ui.widget.danmuku.richText.OnDanmakuClickListener
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_danmuku.*
import java.util.*
import java.util.concurrent.TimeUnit


class DanmukuActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_danmuku)
        layout_live_video_danmaku.setOnDanmakuClickListener(object : OnDanmakuClickListener {
            override fun onCustomClick(type: Int, data: Any?) {
                Log.w("TAG", "---onCustomClick")

            }

            override fun onNicknameClick(type: Int, memberId: String?, gender: Int) {
                Log.w("TAG", "---onNicknameClick")
            }

            override fun onLinkClick(page: Int, roomActionType: Int, url: String?, title: String?, type: Int) {
                Log.w("TAG", "---onLinkClick")

            }
        })

        val ext: MutableMap<String, Any> = HashMap()
        ext["showUserId"] = "1234455"
        ext["linkContent"] = getString(R.string.zhima_start_video_detail2)
        ext["linkUrl"] = "https://i.zhenai.com/m/client/live/agreeMents.html"
        val message = ShowIMMessage()
        message.type = 1
        message.content = "系统通知：" + getString(R.string.live_video_system_notification)
        message.msgExt = ext
        layout_live_video_danmaku.handleOtherDanmaku(message)

        for (i in 1..5) {
            val item = ShowIMMessage()
            item.avatarURL = "https://photo.zastatic.com/images/photo/265871/1063483581/27916038324508680.png"
            item.nickname = "wangwei${i}"
            item.wealthLevel = i
            item.type = 2
            item.content = "我是第${i}条弹幕"
            layout_live_video_danmaku.handleOtherDanmaku(item)
        }

        Observable.interval(2, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val randoms = (0..100).random()
                    val item = ShowIMMessage()
                    item.avatarURL = "https://photo.zastatic.com/images/photo/265871/1063483581/27916038324508680.png"
                    item.nickname = "wangwei${randoms}"
                    item.type = 2
                    item.wealthLevel = randoms
                    item.content = "我是第${randoms}条弹幕"

                    layout_live_video_danmaku.handleOtherDanmaku(item)
                }


    }


}
