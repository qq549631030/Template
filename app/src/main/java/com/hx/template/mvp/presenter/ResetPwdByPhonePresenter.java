package com.hx.template.mvp.presenter;

import com.hx.template.Constant;
import com.hx.template.domain.usercase.UseCase;
import com.hx.template.domain.usercase.single.sms.RequestSMSCodeCase;
import com.hx.template.domain.usercase.single.user.ResetPwdBySMSCodeCase;
import com.hx.template.model.Callback;
import com.hx.template.model.SMSModel;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvp.contract.ResetPwdByPhoneContract;
import com.hx.template.mvp.BasePresenter;
import com.hx.template.utils.StringUtils;

import java.util.regex.Pattern;

import javax.inject.Inject;

/**
 * Created by huangx on 2016/8/19.
 */
public class ResetPwdByPhonePresenter extends BasePresenter<ResetPwdByPhoneContract.View> implements ResetPwdByPhoneContract.MvpPresenter {
    private final RequestSMSCodeCase requestSMSCodeCase;
    private final ResetPwdBySMSCodeCase resetPwdBySMSCodeCase;

    @Inject
    public ResetPwdByPhonePresenter(RequestSMSCodeCase requestSMSCodeCase, ResetPwdBySMSCodeCase resetPwdBySMSCodeCase) {
        this.requestSMSCodeCase = requestSMSCodeCase;
        this.resetPwdBySMSCodeCase = resetPwdBySMSCodeCase;
    }

    /**
     * 获取短信验证码
     */
    @Override
    public void requestSMSCode() {
        checkViewAttached();
        if (checkPhone()) {
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
     * 手机号码重置密码
     */
    @Override
    public void resetPasswordBySMSCode() {
        checkViewAttached();
        if (checkInput()) {
            ResetPwdBySMSCodeCase.RequestValues requestValues = new ResetPwdBySMSCodeCase.RequestValues(getMvpView().getSMSCode(), getMvpView().getPassword());
            resetPwdBySMSCodeCase.setRequestValues(requestValues);
            resetPwdBySMSCodeCase.setUseCaseCallback(new UseCase.UseCaseCallback<ResetPwdBySMSCodeCase.ResponseValue>() {
                @Override
                public void onSuccess(ResetPwdBySMSCodeCase.ResponseValue response) {
                    if (isViewAttached()) {
                        getMvpView().hideLoadingProgress();
                        getMvpView().resetSuccess();
                    }
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    if (isViewAttached()) {
                        getMvpView().hideLoadingProgress();
                        getMvpView().resetFail(errorCode, errorMsg);
                    }
                }
            });
            resetPwdBySMSCodeCase.run();
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
