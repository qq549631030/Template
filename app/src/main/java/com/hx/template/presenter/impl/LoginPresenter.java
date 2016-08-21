package com.hx.template.presenter.impl;

import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvpview.impl.LoginMvpView;
import com.hx.template.presenter.BasePresenter;
import com.hx.template.presenter.itf.IUserPresenter;

import javax.inject.Inject;

import cn.huangx.common.utils.StringUtils;

/**
 * Created by huangxiang on 16/3/30.
 */
public class LoginPresenter extends BasePresenter<LoginMvpView> implements IUserPresenter {
    private UserModel userModel;
    Callback callback = new Callback() {
        @Override
        public void onSuccess(int taskId, Object... data) {
            if (isViewAttached()) {
                switch (taskId) {
                    case TaskManager.TASK_ID_LOGIN:
                        if (data.length > 0 && data[0] instanceof User) {
                            getMvpView().hideLoadingProgress();
                            getMvpView().loginSuccess((User) data[0]);
                        }
                        break;
                }
            }
        }

        @Override
        public void onFailure(int taskId, String errorCode, Object... errorMsg) {
            if (isViewAttached()) {
                String errorMsgStr = (errorMsg != null && errorMsg.length > 0) ? errorMsg[0].toString() : "";
                switch (taskId) {
                    case TaskManager.TASK_ID_LOGIN:
                        getMvpView().hideLoadingProgress();
                        getMvpView().loginFail(errorCode, errorMsgStr);
                        break;
                }
            }
        }
    };

    @Inject
    public LoginPresenter(UserModel userModel) {
        this.userModel = userModel;
        if (userModel == null) {
            throw new IllegalArgumentException("userModel can't be null");
        }
    }

    @Override
    public void onDestroyed() {
        super.onDestroyed();
        callback = null;
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
            getMvpView().showLoadingProgress("登录中...");
            userModel.login(getMvpView().getUserName(), getMvpView().getPassword(), callback);
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
