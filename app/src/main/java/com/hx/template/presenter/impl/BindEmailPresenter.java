package com.hx.template.presenter.impl;

import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvpview.impl.BindEmailMvpView;
import com.hx.template.presenter.BasePresenter;
import com.hx.template.presenter.itf.IBindEmailPresenter;

import javax.inject.Inject;

/**
 * Created by huangxiang on 16/8/17.
 */
public class BindEmailPresenter extends BasePresenter<BindEmailMvpView> implements IBindEmailPresenter {
    UserModel userModel;

    Callback callback = new Callback() {
        @Override
        public void onSuccess(int taskId, Object... data) {
            if (isViewAttached()) {
                switch (taskId) {
                    case TaskManager.TASK_ID_REQUEST_EMAIL_VERIFY:
                        getMvpView().onRequestSuccess();
                        break;
                    case TaskManager.TASK_ID_UPDATE_USER_INFO:
                        getMvpView().onRequestSuccess();
                        break;
                }
            }
        }

        @Override
        public void onFailure(int taskId, String errorCode, Object... errorMsg) {
            if (isViewAttached()) {
                String errorMsgStr = (errorMsg != null && errorMsg.length > 0) ? errorMsg[0].toString() : "";
                switch (taskId) {
                    case TaskManager.TASK_ID_REQUEST_EMAIL_VERIFY:
                        getMvpView().onRequestFail(errorCode, errorMsgStr);
                        break;
                    case TaskManager.TASK_ID_UPDATE_USER_INFO:
                        getMvpView().onRequestFail(errorCode, errorMsgStr);
                        break;
                }
            }
        }
    };
    @Inject
    public BindEmailPresenter(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void onDestroyed() {
        super.onDestroyed();
        callback = null;
    }

    /**
     * 重新发送验证邮件
     */
    @Override
    public void requestEmailVerify() {
        if (isViewAttached()) {
            userModel.requestEmailVerify(getMvpView().getEmail(), callback);
        }
    }

    /**
     * 重新设置验证邮箱
     */
    @Override
    public void resetEmail() {
        if (isViewAttached()) {
            User user = new User();
            user.setEmail(getMvpView().getEmail());
            userModel.updateUserInfo(user, callback);
        }
    }
}
