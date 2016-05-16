package com.hx.template.presenter.impl;

import com.hx.template.entity.User;
import com.hx.template.model.LoginModel;
import com.hx.template.presenter.BasePresenter;
import com.hx.template.presenter.itf.ILoginPresenter;
import com.hx.template.mvpview.LoginMvpView;

/**
 * Created by huangxiang on 16/3/30.
 */
public class LoginPresenter extends BasePresenter<LoginMvpView> implements ILoginPresenter {
    private LoginModel.Model loginModel;

    public LoginPresenter(LoginModel.Model loginModel) {
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
        loginModel.login(getMvpView().getUserName(), getMvpView().getPassword(), new LoginModel.OnLoginListener() {
            @Override
            public void loginSuccess(User user) {
                if (!isViewAttached()) {
                    return;
                }
                getMvpView().hideLoadingProgress();
                getMvpView().toMainActivity(user);
            }

            @Override
            public void loginFailed(String reason) {
                if (!isViewAttached()) {
                    return;
                }
                getMvpView().hideLoadingProgress();
                getMvpView().showFailedError(reason);
            }
        });
    }
}
