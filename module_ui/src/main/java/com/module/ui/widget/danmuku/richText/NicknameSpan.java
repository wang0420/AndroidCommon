package com.module.ui.widget.danmuku.richText;

/**
 * 用户昵称Span
 */
public class NicknameSpan extends CustomClickSpan {
    private String mUserId;
    private String mAvatar;
    private int mGender;
    private String mWorkCity;

    public NicknameSpan(int textColor, String userId, String avatar, int gender) {
        super(TYPE_NICKNAME, null, textColor, false);
        mUserId = userId;
        mAvatar = avatar;
        mGender = gender;
    }

    public NicknameSpan(int type, int textColor, String userId, String avatar, int gender) {
        super(type, null, textColor, false);
        mUserId = userId;
        mAvatar = avatar;
        mGender = gender;
    }

    public NicknameSpan(int textColor, String userId, String avatar, int gender, String workCity) {
        super(TYPE_NICKNAME, null, textColor, false);
        mUserId = userId;
        mAvatar = avatar;
        mGender = gender;
        mWorkCity = workCity;
    }

    public String getUserId() {
        return mUserId;
    }

    public int getGender() {
        return mGender;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public String getWorkCity() {
        return mWorkCity;
    }
}
