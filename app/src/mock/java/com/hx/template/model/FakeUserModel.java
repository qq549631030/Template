package com.hx.template.model;

import android.os.Handler;

import com.hx.template.entity.BbUser;
import com.hx.template.entity.User;
import com.hx.template.global.GsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huangx on 2016/8/23.
 */
public class FakeUserModel implements UserModel {

    private static List<User> userList = new ArrayList<>();

    private static User currentUser;

    private Handler handler;

    static {
        User user = new User();
        user.setObjectId("0");
        user.setUsername("15870679047");
        user.setPassword("123456");
        userList.add(user);
    }

    public FakeUserModel() {
        handler = new Handler();
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @param callback 回调监听
     */
    @Override
    public void register(String username, String password, Callback callback) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                callback.onFailure(TaskManager.TASK_ID_REGISTER, "1001", "用户名已存在");
            }
        }
        User user = new User();
        user.setObjectId(String.valueOf(userList.size()));
        user.setUsername(username);
        user.setPassword(password);
        userList.add(user);
        callback.onSuccess(TaskManager.TASK_ID_REGISTER, user);
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @param callback 回调监听
     */
    @Override
    public void login(final String username, final String password, final Callback callback) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (User user : userList) {
                    if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                        currentUser = user;
                        callback.onSuccess(TaskManager.TASK_ID_LOGIN, user);
                        return;
                    }
                }
                callback.onFailure(TaskManager.TASK_ID_LOGIN, "1002", "用户名或密码错误");
            }
        }, 2000);

    }

    /**
     * 修改密码
     *
     * @param oldPwd   旧密码
     * @param newPwd   新密码
     * @param callback 回调监听
     */
    @Override
    public void modifyPwd(final String oldPwd, final String newPwd, final Callback callback) {
        if (currentUser != null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (currentUser.getPassword().equals(oldPwd)) {
                        currentUser.setPassword(newPwd);
                        callback.onSuccess(TaskManager.TASK_ID_MODIFY_PWD);
                    } else {
                        callback.onFailure(TaskManager.TASK_ID_MODIFY_PWD, "1004", "原密码不正确");
                    }
                }
            }, 2000);
        } else {
            callback.onFailure(TaskManager.TASK_ID_MODIFY_PWD, "1003", "用户未登录");
        }
    }

    /**
     * 获取用户信息
     *
     * @param userId   用户id
     * @param callback 回调监听
     */
    @Override
    public void getUserInfo(final String userId, final Callback callback) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (User user : userList) {
                    if (user.getObjectId().equals(userId)) {
                        callback.onSuccess(TaskManager.TASK_ID_GET_USER_INFO, user);
                        return;
                    }
                }
                callback.onFailure(TaskManager.TASK_ID_GET_USER_INFO, "1005", "用户不存在");
            }
        }, 2000);
    }

    /**
     * 更新用户信息
     *
     * @param values   要更新的用户信息
     * @param callback 回调监听
     */
    @Override
    public void updateUserInfo(String userId, final Map<String, Object> values, final Callback callback) {
        if (currentUser != null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (String key : values.keySet()) {
                        currentUser.setValue(key, values.get(key));
                    }
                    User.setCurrent(GsonUtils.toJsonObj(currentUser));
                    callback.onSuccess(TaskManager.TASK_ID_UPDATE_USER_INFO);
                }
            }, 2000);
        } else {
            callback.onFailure(TaskManager.TASK_ID_UPDATE_USER_INFO, "1003", "用户未登录");
        }
    }

    /**
     * 请求验证Email
     *
     * @param email    要验证有邮箱
     * @param callback 回调监听
     */
    @Override
    public void requestEmailVerify(final String email, final Callback callback) {
        if (currentUser != null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    currentUser.setEmail(email);
                    currentUser.setEmailVerified(Boolean.FALSE);
                    User.setCurrent(GsonUtils.toJsonObj(currentUser));
                    callback.onSuccess(TaskManager.TASK_ID_REQUEST_EMAIL_VERIFY);
                }
            }, 2000);
        } else {
            callback.onFailure(TaskManager.TASK_ID_REQUEST_EMAIL_VERIFY, "1003", "用户未登录");
        }
    }

    /**
     * 手机号码重置密码
     *
     * @param code     收到的驗证码
     * @param pwd      新密码
     * @param callback 回调监听
     */
    @Override
    public void resetPasswordBySMSCode(final String code, final String pwd, final Callback callback) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if ("666666".equals(code)) {
                    currentUser.setPassword(pwd);
                    User.setCurrent(GsonUtils.toJsonObj(currentUser));
                    callback.onSuccess(TaskManager.TASK_ID_RESET_PASSWORD_BY_SMS_CODE);
                } else {
                    callback.onFailure(TaskManager.TASK_ID_RESET_PASSWORD_BY_SMS_CODE, "1011", "验证码错误");
                }
            }
        }, 2000);
    }

    /**
     * 邮箱重置密码
     *
     * @param email    绑定的邮箱地址
     * @param callback 回调监听
     */
    @Override
    public void resetPasswordByEmail(String email, final Callback callback) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(TaskManager.TASK_ID_RESET_PASSWORD_BY_EMAIL);
            }
        }, 2000);
    }

    /**
     * 登出
     */
    @Override
    public void logout() {
        currentUser = null;
        User.setCurrent(null);
    }
}
