package com.hx.template.presenter.impl;

import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.SMSModel;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvpview.impl.BindPhoneMvpView;
import com.hx.template.mvpview.impl.VerifyPhoneMvpView;
import com.hx.template.presenter.BasePresenter;
import com.hx.template.presenter.Presenter;
import com.hx.template.presenter.itf.IBindPhonePresenter;

import javax.inject.Inject;

/**
 * Created by huangxiang on 16/8/13.
 */
public class BindPhonePresenter extends BasePresenter<VerifyPhoneMvpView> implements IBindPhonePresenter {
    private SMSModel smsModel;
    private UserModel userModel;

    Callback callback = new Callback() {
        @Override
        public void onSuccess(int taskId, Object... data) {
            if (isViewAttached()) {
                switch (taskId) {
                    case TaskManager.TASK_ID_REQUEST_SMS_CODE:
                        getMvpView().onRequestSuccess(data);
                        break;
                    case TaskManager.TASK_ID_VERIFY_SMS_CODE:
                        getMvpView().onVerifySuccess(data);
                        break;
                    case TaskManager.TASK_ID_UPDATE_USER_INFO:
                        if (getMvpView() instanceof BindPhoneMvpView) {
                            BindPhoneMvpView view = (BindPhoneMvpView) getMvpView();
                            view.bindSuccess();
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
                    case TaskManager.TASK_ID_REQUEST_SMS_CODE:
                        getMvpView().onRequestFail(errorCode, errorMsgStr);
                        break;
                    case TaskManager.TASK_ID_VERIFY_SMS_CODE:
                        getMvpView().onVerifyFail(errorCode, errorMsgStr);
                        break;
                    case TaskManager.TASK_ID_UPDATE_USER_INFO:
                        if (getMvpView() instanceof BindPhoneMvpView) {
                            BindPhoneMvpView view = (BindPhoneMvpView) getMvpView();
                            view.bindFail(errorCode, errorMsgStr);
                        }
                        break;
                }
            }
        }
    };
    @Inject
    public BindPhonePresenter(SMSModel smsModel, UserModel userModel) {
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
     * 验证短信验证码
     */
    @Override
    public void verifySmsCode() {
        if (isViewAttached()) {
            smsModel.verifySmsCode(getMvpView().getVerifyPhoneNumber(), getMvpView().getSMSCode(), callback);
        }
    }

    /**
     * 绑定手机号码
     */
    @Override
    public void bindPhone() {
        if (isViewAttached()) {
            if (getMvpView() instanceof BindPhoneMvpView) {
                User user = new User();
                user.setMobilePhoneNumber(getMvpView().getVerifyPhoneNumber());
                user.setMobilePhoneNumberVerified(true);
                userModel.updateUserInfo(user, callback);
            }
        }
    }
}
