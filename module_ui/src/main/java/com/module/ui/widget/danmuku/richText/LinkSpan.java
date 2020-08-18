package com.module.ui.widget.danmuku.richText;

/**
 * 超链接Span
 *
 * @date 2017/11/24
 */

public class LinkSpan extends CustomClickSpan {
    private String mUrl = "", mTitle = "";
    //跳转页面
    private int mLinkPage;
    private int mRoomActionType;
    private int mMsgType;

    /**
     * @param textColor
     * @param underline
     * @param linkPage
     * @param roomActionType
     * @param url
     * @param title
     */
    public LinkSpan(int textColor, boolean underline, int linkPage, int roomActionType, String url, String title) {
        super(TYPE_LINK, null, textColor, underline);
        mLinkPage = linkPage;
        mRoomActionType = roomActionType;
        mUrl = url;
        mTitle = title;
    }

    public LinkSpan(int textColor, boolean underline, int page, int roomActionType, String url, String title, int msgType) {
        this(textColor, underline, page, roomActionType, url, title);
        mMsgType = msgType;
    }

    public String getUrl() {
        return mUrl;
    }

    public int getPage() {
        return mLinkPage;
    }

    public int getRoomActionType() {
        return mRoomActionType;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getMsgType() {
        return mMsgType;
    }
}
