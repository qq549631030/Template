package com.hx.template.presenter.impl;

import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.UserModel;
import com.hx.template.presenter.BasePresenter;
import com.hx.template.presenter.itf.IUserPresenter;
import com.hx.template.mvpview.impl.LoginMvpView;

/**
 * Created by huangxiang on 16/3/30.
 */
public class LoginPresenter extends BasePresenter<LoginMvpView> implements IUserPresenter {
    private UserModel userModel;

    public LoginPresenter(UserModel userModel) {
        this.userModel = userModel;
        if (userModel == null) {
            throw new IllegalArgumentException("userModel can't be null");
        }
    }

    /**
     * 登录
     */
    @Override
    public void login() {
        if (!isViewAttached()) {
            return;
        }
        userModel.login(getMvpView().getUserName(), getMvpView().getPassword(), new Callback() {
            @Override
            public void onSuccess(Object... data) {
                if (isViewAttached()) {
                    if (data.length > 0 && data[0] instanceof User) {
                        getMvpView().loginSuccess((User) data[0]);
                    }
                }
            }

            @Override
            public void onFailure(String errorCode, Object... errorMsg) {
                if (isViewAttached()) {
                    getMvpView().loginFail(errorCode, (errorMsg != null && errorMsg.length > 0) ? errorMsg[0].toString() : "");
                }
            }
        });
    }
}
