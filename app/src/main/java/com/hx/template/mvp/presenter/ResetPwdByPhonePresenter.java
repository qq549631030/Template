package com.hx.template.mvp.presenter;

import com.hx.template.Constant;
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
public class ResetPwdByPhonePresenter extends BasePresenter<ResetPwdByPhoneContract.View> implements ResetPwdByPhoneContract.MvpPresenter, Callback {
    SMSModel smsModel;
    UserModel userModel;

    @Inject
    public ResetPwdByPhonePresenter(SMSModel smsModel, UserModel userModel) {
        this.smsModel = smsModel;
        this.userModel = userModel;
    }

    /**
     * 获取短信验证码
     */
    @Override
    public void requestSMSCode() {
        checkViewAttached();
        if (checkPhone()) {
            smsModel.requestSMSCode(getMvpView().getRequestPhoneNumber(), getMvpView().getSMSTemplate(), this);
        }
    }

    /**
     * 手机号码重置密码
     */
    @Override
    public void resetPasswordBySMSCode() {
        checkViewAttached();
        if (checkInput()) {
            userModel.resetPasswordBySMSCode(getMvpView().getSMSCode(), getMvpView().getPassword(), this);
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

    @Override
    public void onSuccess(int taskId, Object... data) {
        if (isViewAttached()) {
            getMvpView().hideLoadingProgress();
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
            getMvpView().hideLoadingProgress();
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
}
