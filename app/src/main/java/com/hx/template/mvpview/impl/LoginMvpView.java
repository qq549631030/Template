package com.hx.template.mvpview.impl;

import com.hx.template.entity.User;
import com.hx.template.mvpview.MvpView;

/**
 * Created by huangxiang on 16/3/9.
 */
public interface LoginMvpView extends MvpView {
    /**
     * 获取用户名
     *
     * @return
     */
    String getUserName();

    /**
     * 获取密码
     *
     * @return
     */
    String getPassword();

    /**
     * 登录成功
     *
     * @param user 用户
     */
    void loginSuccess(User user);

    /**
     * 登录失败
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    void loginFail(String errorCode, String errorMsg);
}
