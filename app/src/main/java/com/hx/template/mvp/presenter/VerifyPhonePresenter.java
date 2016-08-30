package com.hx.template.mvp.presenter;

import com.hx.template.Constant;
import com.hx.template.domain.usercase.UseCase;
import com.hx.template.domain.usercase.single.sms.RequestSMSCodeCase;
import com.hx.template.domain.usercase.single.sms.VerifySmsCodeCase;
import com.hx.template.model.Callback;
import com.hx.template.model.SMSModel;
import com.hx.template.model.TaskManager;
import com.hx.template.mvp.BasePresenter;
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
        checkViewAttached();
        if (checkPhone()) {
            getMvpView().showLoadingProgress("正在获取短信验证码...");
            RequestSMSCodeCase.RequestValues requestValues = new RequestSMSCodeCase.RequestValues(getMvpView().getRequestPhoneNumber(), getMvpView().getSMSTemplate());
            requestSMSCodeCase.setRequestValues(requestValues);
            requestSMSCodeCase.setUseCaseCallback(new UseCase.UseCaseCallback<RequestSMSCodeCase.ResponseValue>() {
                @Override
                public void onSuccess(RequestSMSCodeCase.ResponseValue response) {
                    if (isViewAttached()) {
                        getMvpView().hideLoadingProgress();
                        getMvpView().onRequestSuccess(response.getSmsId());
                    }
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    if (isViewAttached()) {
                        getMvpView().hideLoadingProgress();
                        getMvpView().onRequestFail(errorCode, errorMsg);
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
        checkViewAttached();
        if (checkInput()) {
            getMvpView().showLoadingProgress("正在验证短信验证码...");
            VerifySmsCodeCase.RequestValues requestValues = new VerifySmsCodeCase.RequestValues(getMvpView().getVerifyPhoneNumber(), getMvpView().getSMSCode());
            verifySmsCodeCase.setRequestValues(requestValues);
            verifySmsCodeCase.setUseCaseCallback(new UseCase.UseCaseCallback<VerifySmsCodeCase.ResponseValue>() {
                @Override
                public void onSuccess(VerifySmsCodeCase.ResponseValue response) {
                    if (isViewAttached()) {
                        getMvpView().hideLoadingProgress();
                        getMvpView().onVerifySuccess();
                    }
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    if (isViewAttached()) {
                        getMvpView().hideLoadingProgress();
                        getMvpView().onVerifyFail(errorCode, errorMsg);
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
