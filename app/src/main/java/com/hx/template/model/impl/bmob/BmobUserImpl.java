package com.hx.template.model.impl.bmob;

import com.hx.template.entity.BbUser;
import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;

import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by huangx on 2016/8/12.
 */
public class BmobUserImpl implements UserModel {
    @Override
    public void register(String username, String password, final Callback callback) {

        BmobUser user = new BmobUser();
        user.setUsername(username);
        user.setPassword(password);
        user.signUp(new SaveListener<BbUser>() {
            @Override
            public void done(BbUser bbUser, BmobException e) {
                BmobCallBackDeliver.handleResult(callback, TaskManager.TASK_ID_REGISTER, e, bbUser.toUser());
            }
        });
    }

    @Override
    public void login(String username, String password, final Callback callback) {
        BmobUser.loginByAccount(username, password, new LogInListener<BbUser>() {
            @Override
            public void done(BbUser bbUser, BmobException e) {
                BmobCallBackDeliver.handleResult(callback, TaskManager.TASK_ID_LOGIN, e, bbUser.toUser());
            }
        });
    }

    /**
     * 修改密码
     *
     * @param oldPwd
     * @param newPwd
     * @param callback
     */
    @Override
    public void modifyPwd(String oldPwd, String newPwd, final Callback callback) {
        BmobUser.updateCurrentUserPassword(oldPwd, newPwd, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                BmobCallBackDeliver.handleResult(callback, TaskManager.TASK_ID_MODIFY_PWD, e);
            }
        });
    }

    /**
     * 获取用户信息
     *
     * @param userId   用户id
     * @param callback 回调监听
     */
    @Override
    public void getUserInfo(String userId, final Callback callback) {
        BmobQuery<BbUser> query = new BmobQuery<BbUser>();
        query.getObject(userId, new QueryListener<BbUser>() {
            @Override
            public void done(BbUser bbUser, BmobException e) {
                BmobCallBackDeliver.handleResult(callback, TaskManager.TASK_ID_GET_USER_INFO, e, bbUser.toUser());
            }
        });
    }

    /**
     * 更新用户信息
     *
     * @param values   要更新的用户信息
     * @param callback 回调监听
     */
    @Override
    public void updateUserInfo(String userId, Map<String, Object> values, final Callback callback) {
        BbUser bbUser = new BbUser();
        for (String key : values.keySet()) {
            bbUser.setValue(key, values.get(key));
        }
        bbUser.update(userId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                BmobCallBackDeliver.handleResult(callback, TaskManager.TASK_ID_UPDATE_USER_INFO, e);
            }
        });
    }

    /**
     * 请求验证Email
     *
     * @param email    要验证有邮箱
     * @param callback 回调监听
     */
    @Override
    public void requestEmailVerify(String email, final Callback callback) {
        BmobUser.requestEmailVerify(email, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                BmobCallBackDeliver.handleResult(callback, TaskManager.TASK_ID_REQUEST_EMAIL_VERIFY, e);
            }
        });
    }

    /**
     * 手机号码重置密码
     *
     * @param code     收到的驗证码
     * @param pwd      新密码
     * @param callback 回调监听
     */
    @Override
    public void resetPasswordBySMSCode(String code, String pwd, final Callback callback) {
        BmobUser.resetPasswordBySMSCode(code, pwd, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                BmobCallBackDeliver.handleResult(callback, TaskManager.TASK_ID_RESET_PASSWORD_BY_SMS_CODE, e);
            }
        });
    }

    /**
     * 邮箱重置密码
     *
     * @param email    绑定的邮箱地址
     * @param callback 回调监听
     */
    @Override
    public void resetPasswordByEmail(String email, final Callback callback) {
        BmobUser.resetPasswordByEmail(email, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                BmobCallBackDeliver.handleResult(callback, TaskManager.TASK_ID_RESET_PASSWORD_BY_EMAIL, e);
            }
        });
    }

    /**
     * 登出
     */
    @Override
    public void logout() {
        BmobUser.logOut();
    }

    /**
     * 取消操作
     *
     * @param args
     * @return
     */
    @Override
    public boolean cancel(Object... args) {
        return false;
    }
}
