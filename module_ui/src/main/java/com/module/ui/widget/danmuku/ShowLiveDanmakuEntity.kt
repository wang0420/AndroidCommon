package com.module.ui.widget.danmuku

import com.module.ui.widget.danmuku.richText.SpannedText
import java.io.Serializable

data class ShowLiveDanmakuEntity(

        var type: Int,//消息类型
        var formatted: SpannedText,//格式化后内容
        var isSendFailure: Boolean = false,
        var showUserId: Long? = 0
) : Serializable