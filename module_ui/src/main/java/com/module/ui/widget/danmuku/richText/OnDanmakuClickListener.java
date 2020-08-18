package com.module.ui.widget.danmuku.richText;

/**
 * 弹幕消息点击监听器

 * @date 2017/5/16
 */

public interface OnDanmakuClickListener {
    void onNicknameClick(int type, String memberId, int gender);

    void onLinkClick(int page, int roomActionType, String url, String title, int type);

    void onCustomClick(int type, Object data);
}
