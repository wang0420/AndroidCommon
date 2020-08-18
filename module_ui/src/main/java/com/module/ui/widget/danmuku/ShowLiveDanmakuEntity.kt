package com.module.ui.widget.danmuku

import java.io.Serializable

data class ShowLiveDanmakuEntity(var template: String? = null,// 文字模板
                                 var isSendFailure: Boolean = false,
                                 var type: Int,//消息类型
                                 var showUserId: Long? = 0
) : Serializable