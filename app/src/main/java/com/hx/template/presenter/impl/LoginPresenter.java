package com.hx.template.presenter.impl;

import com.hx.template.entity.User;
import com.hx.template.model.LoginModel;
import com.hx.template.model.impl.RetrofitLoginImpl;
import com.hx.template.presenter.BasePresenter;
import com.hx.template.presenter.ILogin;
import com.hx.template.mvpview.LoginMvpView;

/**
 * Created by huangxiang on 16/3/30.
 */
public class LoginPresenter extends BasePresenter<LoginMvpView> implements ILogin {
    private LoginModel.Model loginModel;

    public LoginPresenter() {
//        loginModel = new LoginModelImpl();
        loginModel = new RetrofitLoginImpl();
    }

    public void login() {
        if (!isViewAttached()) {
            return;
        }
        loginModel.login(getMvpView().getUserName(), getMvpView().getPassword(), new LoginModel.OnLoginListener() {
            @Override
            public void loginSuccess(User user) {
                getMvpView().toMainActivity(user);
            }

            @Override
            public void loginFailed(String reason) {
                getMvpView().showFailedError(reason);
            }
        });
    }
}