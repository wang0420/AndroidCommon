package com.module.ui.widget.danmuku;

public interface ShowOnDanmakuClickListener extends BaseOnDanmakuClickListener {
    //进场动画点击
    default void onEnterViewClick(String memberId) {
    }
}
