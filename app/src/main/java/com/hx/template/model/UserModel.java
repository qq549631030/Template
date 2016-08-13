package com.hx.template.model;

import com.hx.template.entity.User;

/**
 * Created by huangxiang on 16/3/9.
 */
public interface UserModel {
    /**
     * 注册
     *
     * @param username
     * @param password
     * @param callback
     */
    void register(String username, String password, Callback callback);

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param callback
     */
    void login(String username, String password, Callback callback);

    /**
     * 修改密码
     *
     * @param oldPwd
     * @param newPwd
     * @param callback
     */
    void modifyPwd(String oldPwd, String newPwd, Callback callback);

    /**
     * 更新用户信息
     *
     * @param user     要更新的用户信息
     * @param callback 回调监听
     */
    void updateUserInfo(User user, Callback callback);

    /**
     * 登出
     */
    void logout();
}
