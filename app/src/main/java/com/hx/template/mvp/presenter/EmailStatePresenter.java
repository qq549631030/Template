package com.hx.template.mvp.presenter;

import com.hx.template.Constant;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvp.contract.EmailStateContract;
import com.hx.template.mvp.BasePresenter;
import com.hx.template.utils.StringUtils;

import java.util.regex.Pattern;

/**
 * Created by huangx on 2016/8/23.
 */
public class EmailStatePresenter extends BasePresenter<EmailStateContract.View> implements EmailStateContract.MvpPresenter, Callback {

    UserModel userModel;

    public EmailStatePresenter(UserModel userModel) {
        this.userModel = userModel;
    }

    /**
     * 重新发送验证邮件
     */
    @Override
    public void requestEmailVerify() {
        checkViewAttached();
        if (checkInput()) {
            userModel.requestEmailVerify(getMvpView().getEmail(), this);
        }
    }

    private boolean checkInput() {
        if (StringUtils.isEmpty(getMvpView().getEmail())) {
            getMvpView().showError("邮箱地址不能为空");
            return false;
        }
        if (!Pattern.compile(Constant.emailFormat).matcher(getMvpView().getEmail()).matches()) {
            getMvpView().showError("请输入正确的邮箱地址");
            return false;
        }
        return true;
    }

    @Override
    public void onSuccess(int taskId, Object... data) {
        if (isViewAttached()) {
            getMvpView().hideLoadingProgress();
            switch (taskId) {
                case TaskManager.TASK_ID_REQUEST_EMAIL_VERIFY:
                    getMvpView().onRequestSuccess();
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
                case TaskManager.TASK_ID_REQUEST_EMAIL_VERIFY:
                    getMvpView().onRequestFail(errorCode, errorMsgStr);
                    break;
            }
        }
    }
}
