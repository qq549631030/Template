package com.hx.template.view;

import com.hx.template.entity.User;

/**
 * Created by huangxiang on 16/3/9.
 */
public interface IloginView {
    String getUserName();

    String getPassword();

    void toMainActivity(User user);

    void showFailedError(String reason);
}
