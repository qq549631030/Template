package com.hx.template.presenter.impl;

import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvpview.impl.ResetPwdByEmailMvpView;
import com.hx.template.presenter.BasePresenter;
import com.hx.template.presenter.itf.IResetPwdByEmailPresenter;

import javax.inject.Inject;

/**
 * Created by huangx on 2016/8/19.
 */
public class ResetPwdByEmailPresenter extends BasePresenter<ResetPwdByEmailMvpView> implements IResetPwdByEmailPresenter {

    UserModel userModel;

    Callback callback = new Callback() {
        @Override
        public void onSuccess(int taskId, Object... data) {
            if (isViewAttached()) {
                switch (taskId) {
                    case TaskManager.TASK_ID_RESET_PASSWORD_BY_EMAIL:
                        getMvpView().sendSuccess();
                        break;
                }
            }
        }

        @Override
        public void onFailure(int taskId, String errorCode, Object... errorMsg) {
            if (isViewAttached()) {
                String errorMsgStr = (errorMsg != null && errorMsg.length > 0) ? errorMsg[0].toString() : "";
                switch (taskId) {
                    case TaskManager.TASK_ID_RESET_PASSWORD_BY_EMAIL:
                        getMvpView().sendFail(errorCode, errorMsgStr);
                        break;
                }
            }
        }
    };
    @Inject
    public ResetPwdByEmailPresenter(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void onDestroyed() {
        super.onDestroyed();
        callback = null;
    }

    /**
     * 邮箱重置密码
     */
    @Override
    public void resetPasswordByEmail() {
        if (isViewAttached()) {
            userModel.resetPasswordByEmail(getMvpView().getEmail(), callback);
        }
    }
}
