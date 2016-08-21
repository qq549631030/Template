package com.hx.template.presenter.impl;

import com.hx.template.model.Callback;
import com.hx.template.model.SMSModel;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvpview.impl.ResetPwdByPhoneMvpView;
import com.hx.template.presenter.BasePresenter;
import com.hx.template.presenter.itf.IResetPwdByPhonePresenter;

import javax.inject.Inject;

/**
 * Created by huangx on 2016/8/19.
 */
public class ResetPwdByPhonePresenter extends BasePresenter<ResetPwdByPhoneMvpView> implements IResetPwdByPhonePresenter {
    SMSModel smsModel;
    UserModel userModel;

    Callback callback = new Callback() {
        @Override
        public void onSuccess(int taskId, Object... data) {
            if (isViewAttached()) {
                switch (taskId) {
                    case TaskManager.TASK_ID_REQUEST_SMS_CODE:
                        getMvpView().onRequestSuccess(data);
                        break;
                    case TaskManager.TASK_ID_RESET_PASSWORD_BY_SMS_CODE:
                        getMvpView().resetSuccess();
                        break;
                }
            }
        }

        @Override
        public void onFailure(int taskId, String errorCode, Object... errorMsg) {
            if (isViewAttached()) {
                String errorMsgStr = (errorMsg != null && errorMsg.length > 0) ? errorMsg[0].toString() : "";
                switch (taskId) {
                    case TaskManager.TASK_ID_REQUEST_SMS_CODE:
                        getMvpView().onRequestFail(errorCode, errorMsgStr);
                        break;
                    case TaskManager.TASK_ID_RESET_PASSWORD_BY_SMS_CODE:
                        getMvpView().resetFail(errorCode, errorMsgStr);

                        break;
                }
            }
        }
    };
    @Inject
    public ResetPwdByPhonePresenter(SMSModel smsModel, UserModel userModel) {
        this.smsModel = smsModel;
        this.userModel = userModel;
    }

    @Override
    public void onDestroyed() {
        super.onDestroyed();
        callback = null;
    }

    /**
     * 获取短信验证码
     */
    @Override
    public void requestSMSCode() {
        if (isViewAttached()) {
            smsModel.requestSMSCode(getMvpView().getRequestPhoneNumber(), getMvpView().getSMSTemplate(), callback);
        }
    }

    /**
     * 手机号码重置密码
     */
    @Override
    public void resetPasswordBySMSCode() {
        if (isViewAttached()) {
            userModel.resetPasswordBySMSCode(getMvpView().getSMSCode(), getMvpView().getPassword(), callback);
        }
    }
}
