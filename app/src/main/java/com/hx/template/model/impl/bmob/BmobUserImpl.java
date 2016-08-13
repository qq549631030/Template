package com.hx.template.model.impl.bmob;

import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.UserModel;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by huangx on 2016/8/12.
 */
public class BmobUserImpl implements UserModel {
    @Override
    public void register(String username, String password, final Callback callback) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    callback.onSuccess(user);
                } else {
                    callback.onFailure(Integer.toString(e.getErrorCode()), e.toString());
                }
            }
        });
    }

    @Override
    public void login(String username, String password, final Callback callback) {
        User.loginByAccount(username, password, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    callback.onSuccess(user);
                } else {
                    callback.onFailure(Integer.toString(e.getErrorCode()), e.toString());
                }
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
        User.updateCurrentUserPassword(oldPwd, newPwd, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    callback.onSuccess();
                } else {
                    callback.onFailure(Integer.toString(e.getErrorCode()), e.toString());
                }
            }
        });
    }

    @Override
    public void logout() {
        User.logOut();
    }
}
