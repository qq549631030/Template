package com.hx.template.mvp.presenter;

import com.hx.template.domain.usercase.UseCase;
import com.hx.template.domain.usercase.single.user.LoginCase;
import com.hx.template.mvp.contract.LoginContract;
import com.hx.template.mvp.BasePresenter;

import javax.inject.Inject;

import cn.huangx.common.utils.StringUtils;

/**
 * Created by huangxiang on 16/3/30.
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.MvpPresenter {

    private final LoginCase loginCase;

    @Inject
    public LoginPresenter(LoginCase loginCase) {
        this.loginCase = loginCase;
    }


    /**
     * 登录
     */
    @Override
    public void login() {
        checkViewAttached();
        if (checkInput()) {
            getMvpView().showLoadingProgress("登录中...");
            LoginCase.RequestValues requestValues = new LoginCase.RequestValues(getMvpView().getUserName(), getMvpView().getPassword());
            loginCase.setRequestValues(requestValues);
            loginCase.setUseCaseCallback(new UseCase.UseCaseCallback<LoginCase.ResponseValue>() {
                @Override
                public void onSuccess(LoginCase.ResponseValue response) {
                    if (isViewAttached()) {
                        getMvpView().hideLoadingProgress();
                        getMvpView().loginSuccess(response.getUser());
                    }
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    if (isViewAttached()) {
                        getMvpView().hideLoadingProgress();
                        getMvpView().loginFail(errorCode, errorMsg);
                    }
                }
            });
            loginCase.run();
        }
    }

    private boolean checkInput() {
        if (StringUtils.isEmpty(getMvpView().getUserName())) {
            getMvpView().showError("用户名不能为空");
            return false;
        }
        if (StringUtils.isEmpty(getMvpView().getPassword())) {
            getMvpView().showError("密码不能为空");
            return false;
        }
        return true;
    }

    @Override
    public boolean cancel(Object... args) {
        return super.cancel(args);
    }
}
