package com.hx.template.mvp.presenter;

import com.hx.mvp.presenter.BasePresenter;
import com.hx.mvp.usecase.BaseUseCase;
import com.hx.template.Constant;
import com.hx.template.mvp.usecase.single.sms.RequestSMSCodeCase;
import com.hx.template.mvp.usecase.single.sms.VerifySmsCodeCase;
import com.hx.template.mvp.contract.VerifyPhoneContract;
import com.hx.template.utils.StringUtils;

import java.util.regex.Pattern;

import javax.inject.Inject;

/**
 * Created by huangx on 2016/8/22.
 */
public class VerifyPhonePresenter extends BasePresenter<VerifyPhoneContract.View> implements VerifyPhoneContract.MvpPresenter {
    private final RequestSMSCodeCase requestSMSCodeCase;
    private final VerifySmsCodeCase verifySmsCodeCase;

    @Inject
    public VerifyPhonePresenter(RequestSMSCodeCase requestSMSCodeCase, VerifySmsCodeCase verifySmsCodeCase) {
        this.requestSMSCodeCase = requestSMSCodeCase;
        this.verifySmsCodeCase = verifySmsCodeCase;
    }


    /**
     * 获取短信验证码
     */
    @Override
    public void requestSMSCode() {
        if (!isViewAttached()) {
            return;
        }
        if (checkPhone()) {
            getMvpView().showLoadingProgress(true, "正在获取短信验证码...");
            RequestSMSCodeCase.RequestValues requestValues = new RequestSMSCodeCase.RequestValues(getMvpView().getRequestPhoneNumber(), getMvpView().getSMSTemplate());
            requestSMSCodeCase.setRequestValues(requestValues);
            requestSMSCodeCase.setUseCaseCallback(new BaseUseCase.UseCaseCallback<RequestSMSCodeCase.ResponseValue>() {
                @Override
                public void onSuccess(RequestSMSCodeCase.ResponseValue response) {
                    if (isViewAttached()) {
                        getMvpView().showLoadingProgress(false);
                        getMvpView().onRequestSuccess(response.getSmsId());
                    }
                }

                @Override
                public void onError(String errorCode, Object... errorData) {
                    if (isViewAttached()) {
                        getMvpView().showLoadingProgress(false);
                        getMvpView().onRequestFail(errorCode, errorData != null && errorData.length > 0 ? errorData[0].toString() : "");
                    }
                }
            });
            requestSMSCodeCase.run();
        }
    }

    /**
     * 验证短信验证码
     */
    @Override
    public void verifySmsCode() {
        if (!isViewAttached()) {
            return;
        }
        if (checkInput()) {
            getMvpView().showLoadingProgress(true, "正在验证短信验证码...");
            VerifySmsCodeCase.RequestValues requestValues = new VerifySmsCodeCase.RequestValues(getMvpView().getVerifyPhoneNumber(), getMvpView().getSMSCode());
            verifySmsCodeCase.setRequestValues(requestValues);
            verifySmsCodeCase.setUseCaseCallback(new BaseUseCase.UseCaseCallback<VerifySmsCodeCase.ResponseValue>() {
                @Override
                public void onSuccess(VerifySmsCodeCase.ResponseValue response) {
                    if (isViewAttached()) {
                        getMvpView().showLoadingProgress(false);
                        getMvpView().onVerifySuccess();
                    }
                }

                @Override
                public void onError(String errorCode, Object... errorData) {
                    if (isViewAttached()) {
                        getMvpView().showLoadingProgress(false);
                        getMvpView().onVerifyFail(errorCode, errorData != null && errorData.length > 0 ? errorData[0].toString() : "");
                    }
                }
            });
            verifySmsCodeCase.run();
        }
    }


    private boolean checkPhone() {
        if (StringUtils.isEmpty(getMvpView().getRequestPhoneNumber())) {
            getMvpView().showError("手机号码不能为空");
            return false;
        }
        if (!(Pattern.matches(Constant.phoneFormat, getMvpView().getRequestPhoneNumber()))) {
            getMvpView().showError("手机号码有误");
            return false;
        }
        return true;
    }

    private boolean checkInput() {
        if (!checkPhone()) {
            return false;
        }
        if (StringUtils.isEmpty(getMvpView().getSMSCode())) {
            getMvpView().showError("验证码不能为空");
            return false;
        }
        return true;
    }
}
