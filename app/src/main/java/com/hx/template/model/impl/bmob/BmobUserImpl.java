package com.hx.template.model.impl.bmob;

import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.UserModel;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by huangx on 2016/8/12.
 */
public class BmobUserImpl implements UserModel {
    @Override
    public void login(String username, String password, Callback callback) {
        User.loginByAccount(username, password, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
              
            }
        });
    }
}
