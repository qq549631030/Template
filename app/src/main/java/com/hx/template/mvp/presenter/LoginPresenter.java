package com.hx.template.mvp.presenter;

import com.hx.mvp.presenter.BasePresenter;
import com.hx.mvp.usecase.BaseUseCase;
import com.hx.template.entity.User;
import com.hx.template.mvp.usecase.single.im.IMLoginCase;
import com.hx.template.mvp.usecase.single.user.LoginCase;
import com.hx.template.mvp.contract.LoginContract;

import javax.inject.Inject;

import cn.huangx.common.utils.StringUtils;

/**
 * Created by huangxiang on 16/3/30.
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.MvpPresenter {

    private final LoginCase loginCase;
    private final IMLoginCase imLoginCase;

    @Inject
    public LoginPresenter(LoginCase loginCase, IMLoginCase imLoginCase) {
        this.loginCase = loginCase;
        this.imLoginCase = imLoginCase;
    }

    /**
     * 登录
     */
    @Override
    public void login() {
        if (!isViewAttached()) {
            return;
        }
        if (checkInput()) {
            getMvpView().showLoadingProgress(true, "登录中...");
            LoginCase.RequestValues requestValues = new LoginCase.RequestValues(getMvpView().getUserName(), getMvpView().getPassword());
            loginCase.setRequestValues(requestValues);
            loginCase.setUseCaseCallback(new BaseUseCase.UseCaseCallback<LoginCase.ResponseValue>() {
                @Override
                public void onSuccess(LoginCase.ResponseValue response) {
                    loginIm(getMvpView().getUserName(), getMvpView().getPassword(), response.getUser());
                }

                @Override
                public void onError(String errorCode, Object... errorData) {
                    if (isViewAttached()) {
                        getMvpView().showLoadingProgress(false);
                        getMvpView().loginFail(errorCode, errorData != null && errorData.length > 0 ? errorData[0].toString() : "");
                    }
                }
            });
            loginCase.run();
        }
    }

    private void loginIm(String username, String password, final User user) {
        if (!isViewAttached()) {
            return;
        }
        IMLoginCase.RequestValues requestValues = new IMLoginCase.RequestValues(username, password);
        imLoginCase.setRequestValues(requestValues);
        imLoginCase.setUseCaseCallback(new BaseUseCase.UseCaseCallback<IMLoginCase.ResponseValue>() {
            @Override
            public void onSuccess(IMLoginCase.ResponseValue response) {
                if (isViewAttached()) {
                    getMvpView().showLoadingProgress(false);
                    getMvpView().loginSuccess(user);
                }
            }

            @Override
            public void onError(String errorCode, Object... errorData) {
                if (isViewAttached()) {
                    getMvpView().showLoadingProgress(false);
                    getMvpView().loginFail(errorCode, errorData != null && errorData.length > 0 ? errorData[0].toString() : "");
                }
            }
        });
        imLoginCase.run();
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
