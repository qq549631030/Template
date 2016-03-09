package com.hx.template.model;

import com.hx.template.http.HttpListener;

/**
 * Created by huangxiang on 16/3/9.
 */
public interface ILogin {
    void login(String username, String password, OnLoginListener listener);
}
