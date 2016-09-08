package com.hx.template.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

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

    public static User getCurrentUser(Object... data) {
        try {
            JSONObject current = getCurrent();
            if (current == null) {
                return null;
            }
            Gson gson = new Gson();
            Type type = new TypeToken<User>() {
            }.getType();
            return gson.fromJson(current.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
