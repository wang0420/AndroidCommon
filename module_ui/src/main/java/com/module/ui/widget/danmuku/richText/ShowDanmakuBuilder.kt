package com.module.ui.widget.danmuku.richText


import android.graphics.Color
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import com.android.common.BaseApplication
import com.module.ui.widget.danmuku.ShowIMMessage


object ShowDanmakuBuilder {
    const val REPLACEMENT_STRING = "%s"

    const val REPLACEMENT_IMG = "%img"
    const val BLANK = " "
    const val REPLACEMENT_LINK = "%link"

    const val REPLACEMENT_TEXT = "%txt"

    const val SYSTEM_MESSAGE = "系统通知："

//        const val SEND_OUT = "送出"
//        const val ALL = "共"
//        const val GE = "个"



    var systemMessageTextColor = Color.parseColor("#FF8CE7FF")
    var normalMessageTextColor = Color.WHITE
    var linkHighLightTextColor = Color.WHITE
    var followTextColor = Color.parseColor("#FF8CE7FF")
    var nicknameTextColor = Color.parseColor("#FFD6D6D6")
    var giftTextColor = Color.parseColor("#FFFEBC05")
    var blueTextColor = Color.parseColor("#FFff3a32")
    var blackTextColor = Color.parseColor("#FF1E1D1D")


    const val FROM_AVATAR = "fromAvatar"
    const val RECEIVER_AVATAR = "receiverAvatar"

    const val RECEIVER_NICKNAME = "receiverNickname"
    const val FROM_NICK_NAME = "fromNickname"

    const val INVITE_TYPE = "inviteType"
    const val KEY_LINK_CONTENT = "linkContent"
    const val KEY_LINK_URL = "linkUrl"
    const val KEY_LINK_TITLE = "linkTitle"

    /**
     * 系统通知弹幕
     */
    @JvmStatic
    fun buildSystemDanmaku(message: ShowIMMessage): SpannedText {
        val spannedText = SpannedText(message.content)
                .setSpan(Int.MAX_VALUE, ForegroundColorSpan(systemMessageTextColor))
        if (message.msgExt != null) {
            val linkContent = message.msgExt[KEY_LINK_CONTENT] as? String?
            if (!linkContent.isNullOrEmpty()) {
                val linkUrl = message.msgExt[KEY_LINK_URL] as? String? ?: ""
                spannedText.setCursor(0)
                        .replace(REPLACEMENT_LINK, linkContent,
                                LinkSpan(linkHighLightTextColor, true, 0, 0, linkUrl, ""))
            }
        }
        return spannedText
    }

    /**
     * 普通文本弹幕
     */
    @JvmStatic
    fun buildNormalDanmaku(message: ShowIMMessage): SpannedText {
        return SpannedText(message.content)
                .setSpan(Int.MAX_VALUE, ForegroundColorSpan(normalMessageTextColor))
    }

    /**
     * 评论弹幕
     */
    @JvmStatic
    fun buildCommentDanmaku(message: ShowIMMessage): SpannedText {
        val spannedText = SpannedText(message.content)
        setNicknameWithIcons(message, spannedText, insertAt = 0, addColon = true)
        spannedText.setSpan(Int.MAX_VALUE, ForegroundColorSpan(normalMessageTextColor))
        return spannedText
    }

    /**
     * 关注主播弹幕
     */
    @JvmStatic
    fun buildFollowAnchorDanmaku(message: ShowIMMessage): SpannedText {
        val spannedText = SpannedText(message.content)
        setNicknameWithIcons(message, spannedText)
        spannedText.setSpan(Int.MAX_VALUE, ForegroundColorSpan(systemMessageTextColor))
        return spannedText
    }


    @JvmStatic
    fun buildMuteCommentDanmaku(message: ShowIMMessage): SpannedText {
        val fromNickName = message.msgExt[FROM_NICK_NAME].toString()
        val receiverName = message.msgExt[RECEIVER_NICKNAME].toString()
        val operaText = "禁言了"
        val content = "$fromNickName $operaText $receiverName"
        val spannedText = SpannedText(content)
        spannedText.setCursor(fromNickName.length + 1)
        spannedText.setSpan(operaText.length, ForegroundColorSpan(systemMessageTextColor))
        return spannedText
    }


    @JvmStatic
    private fun setNicknameWithIcons(
            message: ShowIMMessage,
            spannedText: SpannedText,
            insertAt: Int = -1,// >=0是手动额外插入，<0是替换已有的%s
            addColon: Boolean = false// 是否在昵称后添加中文冒号
    ) {
        val context = BaseApplication.getInstance()
        val treasureImageSpan = if (message.wealthLevel > 0)// 财富值
            ImagetextSpan(context, "机会客户",60f) else null
        val rankImageSpan = if (!TextUtils.isEmpty(message.rankTag))// 等级值
            CenterImageSpan2.fromUrl(message.rankTag, 20f, 20f, 16f)
        else null

        if (insertAt >= 0) {
            spannedText.setCursor(insertAt)
                    .insert(REPLACEMENT_STRING, null)
                    .setCursor(insertAt)// 插入%s后光标归位到插入%s前
        } else {
            spannedText.setCursor(0)
            val where = spannedText.indexOf(REPLACEMENT_STRING)
            if (where < 0) return
            spannedText.setCursor(where)
        }
        if (treasureImageSpan != null) {
            spannedText.insert(REPLACEMENT_IMG, treasureImageSpan)
                    .insert(BLANK, null)
        }
        if (rankImageSpan != null) {
            spannedText.insert(REPLACEMENT_IMG, rankImageSpan)
                    .insert(BLANK, null)
        }
        var nickname = message.nickname
        if (addColon) {
            nickname = "$nickname："
        }
        spannedText.replace(REPLACEMENT_STRING, nickname,
                NicknameSpan(nicknameTextColor, message.fromMemberID, message.avatarURL, message.gender))
    }
}