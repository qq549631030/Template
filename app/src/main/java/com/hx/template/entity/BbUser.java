package com.hx.template.entity;

import com.hx.template.global.GsonUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.helper.GsonUtil;

/**
 * Created by huangx on 2016/8/23.
 */
public class BbUser extends BmobUser {

    private String nickname;

    private String avatar;

    private String gender;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public static BbUser fromUser(User user) {
        String userStr = GsonUtils.toJson(user);
        BbUser bbUser = (BbUser) GsonUtil.toObject(userStr, BbUser.class);
        return bbUser;
    }
}
