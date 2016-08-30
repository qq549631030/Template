package com.hx.template.mvp.presenter;

import com.hx.template.Constant;
import com.hx.template.domain.usercase.UseCase;
import com.hx.template.domain.usercase.complex.BindPhoneCase;
import com.hx.template.domain.usercase.single.sms.RequestSMSCodeCase;
import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.SMSModel;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvp.contract.BindPhoneContract;
import com.hx.template.mvp.BasePresenter;
import com.hx.template.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.inject.Inject;

/**
 * Created by huangxiang on 16/8/13.
 */
public class BindPhonePresenter extends BasePresenter<BindPhoneContract.View> implements BindPhoneContract.MvpPresenter {

    private final RequestSMSCodeCase requestSMSCodeCase;
    private final BindPhoneCase bindPhoneCase;

    @Inject
    public BindPhonePresenter(RequestSMSCodeCase requestSMSCodeCase, BindPhoneCase bindPhoneCase) {
        this.requestSMSCodeCase = requestSMSCodeCase;
        this.bindPhoneCase = bindPhoneCase;
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
     * 绑定手机号码
     *
     * @param userId
     */
    @Override
    public void bindPhone(String userId) {
        checkViewAttached();
        if (checkInput()) {
            getMvpView().showLoadingProgress("正在绑定...");
            BindPhoneCase.RequestValues requestValues = new BindPhoneCase.RequestValues(getMvpView().getVerifyPhoneNumber(), getMvpView().getSMSCode(), userId);
            bindPhoneCase.setRequestValues(requestValues);
            bindPhoneCase.setUseCaseCallback(new UseCase.UseCaseCallback<BindPhoneCase.ResponseValue>() {
                @Override
                public void onSuccess(BindPhoneCase.ResponseValue response) {
                    if (isViewAttached()) {
                        getMvpView().hideLoadingProgress();
                        getMvpView().bindSuccess();
                    }
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    if (isViewAttached()) {
                        getMvpView().hideLoadingProgress();
                        getMvpView().bindFail(errorCode, errorMsg);
                    }
                }
            });
            bindPhoneCase.run();
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
