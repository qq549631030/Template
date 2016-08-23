package com.hx.template.mvp.presenter;

import com.hx.template.Constant;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvp.contract.ResetPwdByEmailContract;
import com.hx.template.mvp.BasePresenter;
import com.hx.template.utils.StringUtils;

import java.util.regex.Pattern;

import javax.inject.Inject;

/**
 * Created by huangx on 2016/8/19.
 */
public class ResetPwdByEmailPresenter extends BasePresenter<ResetPwdByEmailContract.View> implements ResetPwdByEmailContract.MvpPresenter, Callback {

    UserModel userModel;

    @Inject
    public ResetPwdByEmailPresenter(UserModel userModel) {
        this.userModel = userModel;
    }

    /**
     * 邮箱重置密码
     */
    @Override
    public void resetPasswordByEmail() {
        checkViewAttached();
        if (checkInput()) {
            getMvpView().showLoadingProgress("正在重置...");
            userModel.resetPasswordByEmail(getMvpView().getEmail(), this);
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
                case TaskManager.TASK_ID_RESET_PASSWORD_BY_EMAIL:
                    getMvpView().sendSuccess();
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
                case TaskManager.TASK_ID_RESET_PASSWORD_BY_EMAIL:
                    getMvpView().sendFail(errorCode, errorMsgStr);
                    break;
            }
        }
    }
}
