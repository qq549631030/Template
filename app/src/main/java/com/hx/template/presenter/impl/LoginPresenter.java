package com.hx.template.presenter.impl;

import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.LoginModel;
import com.hx.template.presenter.BasePresenter;
import com.hx.template.presenter.itf.ILoginPresenter;
import com.hx.template.mvpview.LoginMvpView;

/**
 * Created by huangxiang on 16/3/30.
 */
public class LoginPresenter extends BasePresenter<LoginMvpView> implements ILoginPresenter {
    private LoginModel loginModel;

    public LoginPresenter(LoginModel loginModel) {
        this.loginModel = loginModel;
        if (loginModel == null) {
            throw new IllegalArgumentException("loginModel can't be null");
        }
    }

    public void login() {
        if (!isViewAttached()) {
            return;
        }
        getMvpView().showLoadingProgress("登录中...");
        loginModel.login(getMvpView().getUserName(), getMvpView().getPassword(), new Callback() {
            @Override
            public void onSuccess(Object... data) {
                if (!isViewAttached()) {
                    return;
                }
                getMvpView().hideLoadingProgress();
                if (data.length > 0 && data[0] instanceof User) {
                    getMvpView().toMainActivity((User) data[0]);
                }
            }

            @Override
            public void onFailure(String errorCode, Object... errorMsg) {
                if (!isViewAttached()) {
                    return;
                }
                getMvpView().hideLoadingProgress();
                if (errorMsg.length > 0) {
                    getMvpView().showFailedError(errorMsg.toString());
                }
            }
        });
    }
}
