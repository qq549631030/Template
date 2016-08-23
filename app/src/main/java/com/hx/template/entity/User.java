package com.hx.template.entity;

import com.hx.template.global.GsonUtils;

import java.lang.reflect.Field;

import cn.bmob.v3.helper.GsonUtil;

/**
 * Created by huangxiang on 15/10/20.
 */
public class User extends BaseUser {

    public static final String INFO_TYPE = "info_type";

    public static final int INFO_TYPE_NICKNAME = 1;

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

    public void setValue(String fieldName, Object value) {
        Field[] fields = User.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                field.setAccessible(true);
                try {
                    field.set(this, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static User fromBbUser(BbUser bbUser) {
        String userStr = GsonUtils.toJson(bbUser);
        User user = (User) GsonUtil.toObject(userStr, User.class);
        return user;
    }
}
