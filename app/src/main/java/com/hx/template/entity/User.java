package com.hx.template.entity;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by huangxiang on 15/10/20.
 */
public class User extends BaseEntity {
    @DatabaseField
    private String username; // 登录名
    @DatabaseField
    private String password; // 登录密码

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
