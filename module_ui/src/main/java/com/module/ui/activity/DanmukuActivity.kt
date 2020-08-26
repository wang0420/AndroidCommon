package com.module.ui.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.module.ui.R
import com.module.ui.widget.danmuku.ShowIMMessage
import com.module.ui.widget.danmuku.richText.*
import kotlinx.android.synthetic.main.activity_danmuku.*
import java.util.*


class DanmukuActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_danmuku)
        /*    val item = ShowIMMessage()
            item.avatarURL = "https://photo.zastatic.com/images/photo/265871/1063483581/27916038324508680.png"
            item.nickname = "wangwei"
            item.wealthLevel = 52
            item.type = 2
            item.content = "我是条弹幕"
            link_text.text = ShowDanmakuBuilder.buildCommentDanmakuTest(item).get()
    */

        val spannedText = SpannedText("我爱中国人")
        spannedText.setCursor(0)
                //从当前光标，插入一段字符，并给该段字符设置一个span
                .insert(ShowDanmakuBuilder.REPLACEMENT_STRING, null)
                // 插入%s后光标归位到插入%s前
                .setCursor(0)
        spannedText.replace(ShowDanmakuBuilder.REPLACEMENT_STRING, "wangwei",
                NicknameSpan(ShowDanmakuBuilder.blueTextColor, "message.fromMemberID", "message.avatarURL", 1))
        spannedText.setSpan(Int.MAX_VALUE, ForegroundColorSpan(ShowDanmakuBuilder.blackTextColor))



        link_text.movementMethod = ShowLinkMovementMethodExt(object :OnDanmakuClickListener{
            override fun onCustomClick(type: Int, data: Any?) {
                Log.w("TAG", "----onCustomClick==" )

            }

            override fun onNicknameClick(type: Int, memberId: String?, gender: Int) {
                Log.w("TAG", "----onNicknameClick==" )


            }

            override fun onLinkClick(page: Int, roomActionType: Int, url: String?, title: String?, type: Int) {
                Log.w("TAG", "----onLinkClick==" )


            }
        })

        link_text.setText(spannedText.get())


        //spannedText.setSpan(Int.MAX_VALUE, ForegroundColorSpan(ShowDanmakuBuilder.systemMessageTextColor))


        /* sp.setSpan(ForegroundColorSpan(Color.RED), 1, 3,
                 Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
 */


/*
        val content = "预祝党的十九大完美谢慕"
        val stringBuilder = SpannableStringBuilder(content)
        val foregroundColorSpan = ForegroundColorSpan(Color.parseColor("#FF4040"))
        stringBuilder.setSpan(foregroundColorSpan, 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textview2.setText(stringBuilder)*/


        val builder = SpannedText("预祝党的十九大完美谢慕")
                .insert(" ", ImagetextSpan(this, "机会客户", 60f)).get()
        textview2.setText(builder)

        val msg = "带有文本《点击这里跳转》"
        val smp = SpannableString(msg)
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View?) {

            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }
        }
        Log.w("TAG", "----==" + msg.indexOf("《"))
        Log.w("TAG", "----=last=" + msg.lastIndexOf("》"))

        smp.setSpan(clickableSpan, msg.indexOf("《") + 1, msg.lastIndexOf("》"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        smp.setSpan(ForegroundColorSpan(Color.parseColor("#0AC3BC")), 4, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textview1.text = smp
        textview1.movementMethod = LinkMovementMethod.getInstance()



















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


        val item = ShowIMMessage()
        item.avatarURL = "https://photo.zastatic.com/images/photo/265871/1063483581/27916038324508680.png"
        item.nickname = "wangwei"
        item.type = 2
        item.content = "我是第条弹幕"
        layout_live_video_danmaku.handleOtherDanmaku(item)

        val item1 = ShowIMMessage()
        item1.avatarURL = "https://photo.zastatic.com/images/photo/265871/1063483581/27916038324508680.png"
        item1.nickname = "wangwei"
        item1.type = 3
        item1.content = "两个得分"
        layout_live_video_danmaku.handleOtherDanmaku(item1)

        /*
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
       */

    }


}
