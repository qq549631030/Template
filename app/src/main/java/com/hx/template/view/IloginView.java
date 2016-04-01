package com.hx.template.view;

import com.hx.template.entity.User;

/**
 * Created by huangxiang on 16/3/9.
 */
public interface IloginView {
    String getUserName();//获取用户名

    String getPassword();//获取密码

    void toMainActivity(User user);//跳转到主页

    void showFailedError(String reason);//显示错误信息
}
