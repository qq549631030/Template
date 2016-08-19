package com.hx.template.presenter.impl;

import com.hx.template.model.Callback;
import com.hx.template.model.SMSModel;
import com.hx.template.model.UserModel;
import com.hx.template.mvpview.impl.ResetPwdByPhoneMvpView;
import com.hx.template.presenter.BasePresenter;
import com.hx.template.presenter.itf.IResetPwdByPhonePresenter;

/**
 * Created by huangx on 2016/8/19.
 */
public class ResetPwdByPhonePresenter extends BasePresenter<ResetPwdByPhoneMvpView> implements IResetPwdByPhonePresenter {
    SMSModel smsModel;
    UserModel userModel;

    public ResetPwdByPhonePresenter(SMSModel smsModel, UserModel userModel) {
        this.smsModel = smsModel;
        this.userModel = userModel;
    }

    /**
     * 获取短信验证码
     */
    @Override
    public void requestSMSCode() {
        if (isViewAttached()) {
            smsModel.requestSMSCode(getMvpView().getRequestPhoneNumber(), getMvpView().getSMSTemplate(), new Callback() {
                @Override
                public void onSuccess(Object... data) {
                    if (isViewAttached()) {
                        getMvpView().onRequestSuccess(data);
                    }
                }

                @Override
                public void onFailure(String errorCode, Object... errorMsg) {
                    if (isViewAttached()) {
                        getMvpView().onRequestFail(errorCode, (errorMsg != null && errorMsg.length > 0) ? errorMsg[0].toString() : "");
                    }
                }
            });
        }
    }

    /**
     * 手机号码重置密码
     */
    @Override
    public void resetPasswordBySMSCode() {
        if (isViewAttached()) {
            userModel.resetPasswordBySMSCode(getMvpView().getSMSCode(), getMvpView().getPassword(), new Callback() {
                @Override
                public void onSuccess(Object... data) {
                    if (isViewAttached()) {
                        getMvpView().resetSuccess();
                    }
                }

                @Override
                public void onFailure(String errorCode, Object... errorMsg) {
                    if (isViewAttached()) {
                        getMvpView().resetFail(errorCode, (errorMsg != null && errorMsg.length > 0) ? errorMsg[0].toString() : "");
                    }
                }
            });
        }
    }
}
