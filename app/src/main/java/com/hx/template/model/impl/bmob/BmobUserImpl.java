package com.hx.template.model.impl.bmob;

import com.hx.mvp.Callback;
import com.hx.template.entity.BbUser;
import com.hx.template.entity.User;
import com.hx.template.model.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
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
                BmobCallBackDeliver.handleResult(callback, e, bbUser.toUser());
            }
        });
    }

    @Override
    public void login(String username, String password, final Callback callback) {
        BmobUser.loginByAccount(username, password, new LogInListener<BbUser>() {
            @Override
            public void done(BbUser bbUser, BmobException e) {
                BmobCallBackDeliver.handleResult(callback, e, bbUser != null ? bbUser.toUser() : null);
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
                BmobCallBackDeliver.handleResult(callback, e);
            }
        });
    }

    /**
     * 获取用户信息
     *
     * @param fieldValue 查询的字段值
     * @param fieldName  查询的字段名称
     * @param callback   回调监听
     */
    @Override
    public <T> void getUserInfo(T fieldValue, String fieldName, final Callback callback) {
        BmobQuery<BbUser> query = new BmobQuery<BbUser>();
        if ("username".equals(fieldName)) {
            query.addWhereEqualTo("username", fieldValue);
        } else if ("userId".equals(fieldName)) {
            query.addWhereEqualTo("objectId", fieldValue);
        }
        query.findObjects(new FindListener<BbUser>() {
            @Override
            public void done(List<BbUser> list, BmobException e) {
                List<User> userList = new ArrayList<User>();
                if (list != null && list.size() > 0) {
                    BbUser bbUser = list.get(0);
                    BmobCallBackDeliver.handleResult(callback, e, bbUser.toUser());
                } else {
                    BmobCallBackDeliver.handleResult(callback, new BmobException(-1, "not found"));
                }
            }
        });
    }

    /**
     * 查询多条用户信息
     *
     * @param fieldValues 查询条件列表
     * @param fieldName   查询的字段名称
     * @param callback    回调监听
     */
    @Override
    public <T> void getUserListInfo(List<T> fieldValues, String fieldName, final Callback callback) {
        BmobQuery<BbUser> query = new BmobQuery<BbUser>();
        if ("username".equals(fieldName)) {
            query.addWhereContainedIn("username", fieldValues);
        } else if ("userId".equals(fieldName)) {
            query.addWhereContainedIn("objectId", fieldValues);
        }
        query.findObjects(new FindListener<BbUser>() {
            @Override
            public void done(List<BbUser> list, BmobException e) {
                List<User> userList = new ArrayList<User>();
                if (list != null && list.size() > 0) {
                    for (BbUser bbUser : list) {
                        userList.add(bbUser.toUser());
                    }
                }
                BmobCallBackDeliver.handleResult(callback, e, userList);
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
                BmobCallBackDeliver.handleResult(callback, e);
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
                BmobCallBackDeliver.handleResult(callback, e);
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
                BmobCallBackDeliver.handleResult(callback, e);
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
                BmobCallBackDeliver.handleResult(callback, e);
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
