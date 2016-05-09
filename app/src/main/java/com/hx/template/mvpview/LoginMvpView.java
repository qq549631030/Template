package com.hx.template.mvpview;

import com.hx.template.entity.User;

/**
 * Created by huangxiang on 16/3/9.
 */
public interface LoginMvpView extends LoadingView, MvpView {
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

    /**
     * 显示错误信息
     *
     * @param reason
     */
    void showFailedError(String reason);
}
