package com.hx.template.entity;

import com.j256.ormlite.field.DatabaseField;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by huangxiang on 15/10/20.
 */
public class User extends BmobUser {

    public static final String INFO_TYPE = "info_type";

    public static final int INFO_TYPE_NICKNAME = 1;

    private String nickname;

    private BmobFile avatar;

    private String gender;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public BmobFile getAvatar() {
        return avatar;
    }

    public void setAvatar(BmobFile avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
