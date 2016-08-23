package com.hx.template.mvp.presenter;

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
public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.MvpPresenter, Callback {

    private UserModel userModel;

    @Inject
    public RegisterPresenter(UserModel userModel) {
        this.userModel = userModel;
        if (userModel == null) {
            throw new IllegalArgumentException("userModel can't be null");
        }
    }

    /**
     * 注册
     */
    @Override
    public void register() {
        checkViewAttached();
        if (checkInput()) {
            getMvpView().showLoadingProgress("注册中...");
            userModel.register(getMvpView().getUserName(), getMvpView().getPassword(), this);
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

    @Override
    public void onSuccess(int taskId, Object... data) {
        if (isViewAttached()) {
            getMvpView().hideLoadingProgress();
            switch (taskId) {
                case TaskManager.TASK_ID_REGISTER:
                    getMvpView().registerSuccess();
                    break;
            }
        }
    }

    @Override
    public void onFailure(int taskId, String errorCode, Object... errorMsg) {
        if (isViewAttached()) {
            getMvpView().hideLoadingProgress();
            String errorMsgStr = (errorMsg != null && errorMsg.length > 0) ? errorMsg[0].toString() : "";
            switch (taskId) {
                case TaskManager.TASK_ID_REGISTER:
                    getMvpView().registerFail(errorCode, errorMsgStr);
                    break;
            }
        }
    }
}
