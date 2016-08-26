package com.hx.template.mvp.presenter;

import com.hx.template.domain.usercase.UseCase;
import com.hx.template.domain.usercase.user.LoginCase;
import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvp.contract.LoginContract;
import com.hx.template.mvp.BasePresenter;

import javax.inject.Inject;

import cn.huangx.common.utils.StringUtils;

/**
 * Created by huangxiang on 16/3/30.
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.MvpPresenter {

    private final LoginCase loginCase;

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
            loginCase.setUseCaseCallback(new UseCase.UseCaseCallback<LoginCase.ResponseValue>() {
                @Override
                public void onSuccess(LoginCase.ResponseValue response) {
                    if (isViewAttached()) {
                        getMvpView().hideLoadingProgress();
                        getMvpView().loginSuccess(response.getUser());
                    }
                }

                @Override
                public void onError(String errorCode, Object... errorMsg) {
                    if (isViewAttached()) {
                        getMvpView().hideLoadingProgress();
                        getMvpView().loginFail(errorCode, errorMsg.toString());
                    }
                }
            });
            loginCase.executeUseCase(requestValues);
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
}
