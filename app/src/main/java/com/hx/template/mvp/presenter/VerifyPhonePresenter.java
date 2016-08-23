package com.hx.template.mvp.presenter;

import com.hx.template.Constant;
import com.hx.template.model.Callback;
import com.hx.template.model.SMSModel;
import com.hx.template.model.TaskManager;
import com.hx.template.mvp.BasePresenter;
import com.hx.template.mvp.contract.VerifyPhoneContract;
import com.hx.template.utils.StringUtils;

import java.util.regex.Pattern;

/**
 * Created by huangx on 2016/8/22.
 */
public class VerifyPhonePresenter extends BasePresenter<VerifyPhoneContract.View> implements VerifyPhoneContract.MvpPresenter, Callback {
    private SMSModel smsModel;

    public VerifyPhonePresenter(SMSModel smsModel) {
        this.smsModel = smsModel;
    }

    /**
     * 获取短信验证码
     */
    @Override
    public void requestSMSCode() {
        checkViewAttached();
        if (checkPhone()) {
            getMvpView().showLoadingProgress("正在获取短信验证码...");
            smsModel.requestSMSCode(getMvpView().getRequestPhoneNumber(), getMvpView().getSMSTemplate(), this);
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
            smsModel.verifySmsCode(getMvpView().getVerifyPhoneNumber(), getMvpView().getSMSCode(), this);
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

    @Override
    public void onSuccess(int taskId, Object... data) {
        if (isViewAttached()) {
            getMvpView().hideLoadingProgress();
            switch (taskId) {
                case TaskManager.TASK_ID_REQUEST_SMS_CODE:
                    getMvpView().onRequestSuccess(data);
                    break;
                case TaskManager.TASK_ID_VERIFY_SMS_CODE:
                    getMvpView().onVerifySuccess(data);
                    break;
            }
        }
    }

    @Override
    public void onFailure(int taskId, String errorCode, Object... errorMsg) {
        if (isViewAttached()) {
            getMvpView().hideLoadingProgress();
            String errorMsgStr = (errorMsg != null && errorMsg.length > 0) ? errorMsg[0].toString() : "";
            switch (taskId) {
                case TaskManager.TASK_ID_REQUEST_SMS_CODE:
                    getMvpView().onRequestFail(errorCode, errorMsgStr);
                    break;
                case TaskManager.TASK_ID_VERIFY_SMS_CODE:
                    getMvpView().onVerifyFail(errorCode, errorMsgStr);
                    break;
            }
        }
    }
}
