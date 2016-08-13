package com.hx.template.mvpview.impl;

import com.hx.template.entity.User;
import com.hx.template.mvpview.LoadingView;
import com.hx.template.mvpview.MvpView;
import com.hx.template.mvpview.ShowErrorView;

/**
 * Created by huangx on 2016/8/12.
 */
public interface RegisterMvpView extends MvpView, LoadingView, ShowErrorView {
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
     * 跳转到主页
     *
     * @param user
     */
    void toMainActivity(User user);
}
