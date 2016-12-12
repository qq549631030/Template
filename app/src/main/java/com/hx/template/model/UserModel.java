package com.hx.template.model;

import com.hx.mvp.Callback;
import com.hx.template.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by huangxiang on 16/3/9.
 */
public interface UserModel extends BaseModel {
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
     * 修改密码
     *
     * @param oldPwd   旧密码
     * @param newPwd   新密码
     * @param callback 回调监听
     */
    void modifyPwd(String oldPwd, String newPwd, Callback callback);

    /**
     * 获取用户信息
     *
     * @param fieldValue 查询的字段值
     * @param fieldName  查询的字段名称(必须唯一)
     * @param callback   回调监听
     * @param <T>
     */
    <T> void getUserInfo(T fieldValue, String fieldName, Callback callback);

    /**
     * 查询多条用户信息
     *
     * @param fieldValues 查询条件列表值
     * @param fieldName   查询的字段名称
     * @param callback    回调监听
     */
    <T> void getUserListInfo(List<T> fieldValues, String fieldName, Callback callback);

    /**
     * 更新用户信息
     *
     * @param userId   用户id
     * @param values   要更新的用户信息
     * @param callback 回调监听
     */
    void updateUserInfo(String userId, Map<String, Object> values, Callback callback);

    /**
     * 请求验证Email
     *
     * @param email    要验证有邮箱
     * @param callback 回调监听
     */
    void requestEmailVerify(String email, Callback callback);

    /**
     * 手机号码重置密码
     *
     * @param code     收到的驗证码
     * @param pwd      新密码
     * @param callback 回调监听
     */
    void resetPasswordBySMSCode(String code, String pwd, Callback callback);

    /**
     * 邮箱重置密码
     *
     * @param email    绑定的邮箱地址
     * @param callback 回调监听
     */
    void resetPasswordByEmail(String email, Callback callback);

    /**
     * 登出
     */
    void logout();
}
