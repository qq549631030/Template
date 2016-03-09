package com.hx.template.model;

import com.hx.template.entity.User;

/**
 * Created by huangxiang on 16/3/9.
 */
public interface OnLoginListener {
    void loginSuccess(User user);

    void loginFailed(String reason);
}
