package com.module.ui.widget.danmuku;

import java.util.HashMap;
import java.util.Map;

public class ShowIMMessage {
    public String rankTag= "https://photo.zastatic.com/images/photo/265871/1063483581/27916038324508680.png";//排行标签图标地址

    public Map<String, Object> msgExt=new HashMap<>();// msgext
    /**
     * 发送方ID
     */
    public String fromMemberID = "";
    public int type;

    public int wealthLevel=23;//财富值
    /**
     * 发送方工作地
     */
    public String workCityString;

    public String nickname = "";
    public String avatarURL = "";
    public int gender;
    public int age;
    public boolean isZhenaiMail; // 是否为珍心会员
    public int zhenxinValue; //珍心值
    public int livePrivilegeFlagBit;// 获得的珍心值特权组成的比特位
    public float giftScore;// 观众给该主播送过多少礼物（当次直播）
    public int flowerSentCount;// 观众消耗的礼物

    public String content;

    public int getWealthLevel() {
        return wealthLevel;
    }

    public void setWealthLevel(int wealthLevel) {
        this.wealthLevel = wealthLevel;
    }

    public String getFromMemberID() {
        return fromMemberID;
    }

    public void setFromMemberID(String fromMemberID) {
        this.fromMemberID = fromMemberID;
    }

    public String getWorkCityString() {
        return workCityString;
    }

    public void setWorkCityString(String workCityString) {
        this.workCityString = workCityString;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isZhenaiMail() {
        return isZhenaiMail;
    }

    public void setZhenaiMail(boolean zhenaiMail) {
        isZhenaiMail = zhenaiMail;
    }

    public int getZhenxinValue() {
        return zhenxinValue;
    }

    public void setZhenxinValue(int zhenxinValue) {
        this.zhenxinValue = zhenxinValue;
    }

    public int getLivePrivilegeFlagBit() {
        return livePrivilegeFlagBit;
    }

    public void setLivePrivilegeFlagBit(int livePrivilegeFlagBit) {
        this.livePrivilegeFlagBit = livePrivilegeFlagBit;
    }

    public float getGiftScore() {
        return giftScore;
    }

    public void setGiftScore(float giftScore) {
        this.giftScore = giftScore;
    }

    public int getFlowerSentCount() {
        return flowerSentCount;
    }

    public void setFlowerSentCount(int flowerSentCount) {
        this.flowerSentCount = flowerSentCount;
    }
}