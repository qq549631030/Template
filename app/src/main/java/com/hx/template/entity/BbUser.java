package com.hx.template.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hx.template.global.GsonUtils;

import org.json.JSONObject;

import java.lang.reflect.Type;

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

    public User toUser() {
        JSONObject current = getCurrentData();
        if (current == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<User>() {
        }.getType();
        return gson.fromJson(current.toString(), type);
    }

    @Override
    public String toString() {
        return "BbUser{" +
                "nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
