package com.module.ui.widget.danmuku;

/**
 * 弹幕消息点击监听器
 *

 */

public interface BaseOnDanmakuClickListener {
    void onNicknameClick(int type, String memberId, int gender);

    void onLinkClick(int page, int roomActionType, String url, String title, int type);

    void onCustomClick(int type, Object data);
}
