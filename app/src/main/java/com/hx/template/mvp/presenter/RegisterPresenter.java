package com.hx.template.mvp.presenter;

import com.hx.template.domain.usercase.UseCase;
import com.hx.template.domain.usercase.user.RegisterCase;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvp.contract.RegisterContract;
import com.hx.template.mvp.BasePresenter;

import javax.inject.Inject;

import cn.huangx.common.utils.StringUtils;

/**
 * Created by huangxiang on 16/8/12.
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.MvpPresenter {

    private final RegisterCase registerCase;

    @Inject
    public RegisterPresenter(RegisterCase registerCase) {
        this.registerCase = registerCase;
    }

    /**
     * 注册
     */
    @Override
    public void register() {
        checkViewAttached();
        if (checkInput()) {
            getMvpView().showLoadingProgress("注册中...");
            RegisterCase.RequestValues requestValues = new RegisterCase.RequestValues(getMvpView().getUserName(), getMvpView().getPassword());
            registerCase.setRequestValues(requestValues);
            registerCase.setUseCaseCallback(new UseCase.UseCaseCallback<RegisterCase.ResponseValue>() {
                @Override
                public void onSuccess(RegisterCase.ResponseValue response) {
                    if (isViewAttached()) {
                        getMvpView().hideLoadingProgress();
                        getMvpView().registerSuccess();
                    }
                }

                @Override
                public void onError(String errorCode, Object... errorMsg) {
                    if (isViewAttached()) {
                        getMvpView().hideLoadingProgress();
                        getMvpView().registerFail(errorCode, errorMsg.toString());
                    }
                }
            });
            registerCase.run();
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
        if (!getMvpView().getPassword().equals(getMvpView().getConfirmPassword())) {
            getMvpView().showError("两次输入的密码不一致");
            return false;
        }
        return true;
    }
}
