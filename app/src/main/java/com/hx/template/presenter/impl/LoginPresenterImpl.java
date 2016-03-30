package com.hx.template.presenter.impl;

import com.hx.template.entity.User;
import com.hx.template.model.LoginModel;
import com.hx.template.model.impl.LoginModelImpl;
import com.hx.template.presenter.LoginPresenter;
import com.hx.template.view.ILoginView;

/**
 * Created by huangxiang on 16/3/30.
 */
public class LoginPresenterImpl implements LoginPresenter {
    private LoginModel.Model loginModel;
    private ILoginView loginView;

    public LoginPresenterImpl(ILoginView loginView) {
        this.loginView = loginView;
        loginModel = new LoginModelImpl();
    }

    public void login() {
        loginModel.login(loginView.getUserName(), loginView.getPassword(), new LoginModel.OnLoginListener() {
            @Override
            public void loginSuccess(User user) {
                loginView.toMainActivity(user);
            }

            @Override
            public void loginFailed(String reason) {
                loginView.showFailedError(reason);
            }
        });
    }
}
