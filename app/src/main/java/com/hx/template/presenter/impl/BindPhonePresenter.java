package com.hx.template.presenter.impl;

import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.SMSModel;
import com.hx.template.model.UserModel;
import com.hx.template.mvpview.impl.BindPhoneMvpView;
import com.hx.template.mvpview.impl.VerifyPhoneMvpView;
import com.hx.template.presenter.BasePresenter;
import com.hx.template.presenter.itf.IBindPhonePresenter;

/**
 * Created by huangxiang on 16/8/13.
 */
public class BindPhonePresenter extends BasePresenter<VerifyPhoneMvpView> implements IBindPhonePresenter {
    private SMSModel smsModel;
    private UserModel userModel;

    public BindPhonePresenter(SMSModel smsModel, UserModel userModel) {
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
     * 验证短信验证码
     */
    @Override
    public void verifySmsCode() {
        if (isViewAttached()) {
            smsModel.verifySmsCode(getMvpView().getVerifyPhoneNumber(), getMvpView().getSMSCode(), new Callback() {
                @Override
                public void onSuccess(Object... data) {
                    if (isViewAttached()) {
                        getMvpView().onVerifySuccess(data);
                    }
                }

                @Override
                public void onFailure(String errorCode, Object... errorMsg) {
                    if (isViewAttached()) {
                        getMvpView().onVerifyFail(errorCode, (errorMsg != null && errorMsg.length > 0) ? errorMsg[0].toString() : "");
                    }
                }
            });
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
                userModel.updateUserInfo(user, new Callback() {
                    @Override
                    public void onSuccess(Object... data) {
                        if (isViewAttached()) {
                            if (getMvpView() instanceof BindPhoneMvpView) {
                                BindPhoneMvpView view = (BindPhoneMvpView) getMvpView();
                                view.bindSuccess();
                            }
                        }
                    }

                    @Override
                    public void onFailure(String errorCode, Object... errorMsg) {
                        if (isViewAttached()) {
                            if (getMvpView() instanceof BindPhoneMvpView) {
                                BindPhoneMvpView view = (BindPhoneMvpView) getMvpView();
                                view.bindFail(errorCode, (errorMsg != null && errorMsg.length > 0) ? errorMsg[0].toString() : "");
                            }
                        }
                    }
                });
            }
        }
    }
}
