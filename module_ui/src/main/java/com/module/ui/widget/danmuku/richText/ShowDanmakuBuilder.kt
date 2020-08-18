package com.module.ui.widget.danmuku.richText

import android.graphics.Color
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import com.android.common.BaseApplication
import com.module.ui.widget.danmuku.ShowIMMessage


object ShowDanmakuBuilder {
    const val REPLACEMENT_STRING = "%s"

    const val REPLACEMENT_IMG = "%img"

    const val REPLACEMENT_LINK = "%link"

    const val REPLACEMENT_TEXT = "%txt"

    const val SYSTEM_MESSAGE = "系统通知："

    const val BLANK = " "
    var normalMessageTextColor = Color.WHITE
    var nicknameTextColor = Color.parseColor("#FFff3a32")
    var systemMessageTextColor = Color.parseColor("#FF8CE7FF")
    var linkHighLightTextColor = Color.WHITE

    /**
     * 系统通知弹幕
     */
    @JvmStatic
    fun buildSystemDanmaku(message: ShowIMMessage): SpannedText {
        val spannedText = SpannedText(message.content)
                .setSpan(Int.MAX_VALUE, ForegroundColorSpan(systemMessageTextColor))
        if (message.msgExt != null) {
            val linkContent = message.msgExt["linkContent"] as? String?
            if (!linkContent.isNullOrEmpty()) {
                val linkUrl = message.msgExt["linkUrl"] as? String? ?: ""
                spannedText.setCursor(0)
                        .replace(REPLACEMENT_LINK, linkContent,
                                LinkSpan(linkHighLightTextColor, true, 0, 0, linkUrl, ""))
            }
        }
        return spannedText
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

    @JvmStatic
    private fun setNicknameWithIcons(
            message: ShowIMMessage,
            spannedText: SpannedText,
            insertAt: Int = -1,// >=0是手动额外插入，<0是替换已有的%s
            addColon: Boolean = true// 是否在昵称后添加中文冒号
    ) {
        val context = BaseApplication.getInstance()
        val treasureImageSpan = if (message.wealthLevel > 0)// 财富值
            TreasureImageSpan(context, message.wealthLevel) else null

        val rankImageSpan = if (!TextUtils.isEmpty(message.avatarURL))// 等级值
            CenterImageSpan2.fromUrl(message.avatarURL, 20f, 20f, 16f) else null

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