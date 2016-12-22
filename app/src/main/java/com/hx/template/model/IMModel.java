package com.hx.template.model;

import com.hx.mvp.Callback;

/**
 * 功能说明：IM相关Model
 * 作者：huangx on 2016/12/20 14:43
 * 邮箱：huangx@pycredit.cn
 */

public interface IMModel extends BaseModel {
    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @param callback 回调监听
     */
    void register(String username, String password, Callback callback);

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @param callback 回调监听
     */
    void login(String username, String password, Callback callback);

    /**
     * 登出
     */
    void logout(Callback callback);

    /**
     * 添加联系人
     *
     * @param username  用户名
     * @param inviteMsg 申请信息
     * @param callback  回调监听
     */
    void addContact(String username, String inviteMsg, Callback callback);
}
