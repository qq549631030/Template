package com.hx.template.presenter.impl;

import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvpview.impl.RegisterMvpView;
import com.hx.template.presenter.BasePresenter;
import com.hx.template.presenter.itf.IRegisterPresenter;

import javax.inject.Inject;

import cn.huangx.common.utils.StringUtils;

/**
 * Created by huangxiang on 16/8/12.
 */
public class RegisterPresenter extends BasePresenter<RegisterMvpView> implements IRegisterPresenter {
    private UserModel userModel;

    Callback callback = new Callback() {
        @Override
        public void onSuccess(int taskId, Object... data) {
            if (isViewAttached()) {
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
                String errorMsgStr = (errorMsg != null && errorMsg.length > 0) ? errorMsg[0].toString() : "";
                switch (taskId) {
                    case TaskManager.TASK_ID_REGISTER:
                        getMvpView().registerFail(errorCode, errorMsgStr);
                        break;
                }
            }
        }
    };

    @Inject
    public RegisterPresenter(UserModel userModel) {
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
     * 注册
     */
    @Override
    public void register() {
        if (!isViewAttached()) {
            return;
        }
        if (checkInput()) {
            userModel.register(getMvpView().getUserName(), getMvpView().getPassword(), callback);
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
        if (getMvpView().getPassword().equals(getMvpView().getConfirmPassword())) {
            getMvpView().showError("两次输入的密码不一致");
            return false;
        }
        return true;
    }
}
