package com.hx.template.mvp.presenter;

import com.hx.template.Constant;
import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvp.contract.BindEmailContract;
import com.hx.template.mvp.BasePresenter;
import com.hx.template.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.inject.Inject;

/**
 * Created by huangxiang on 16/8/17.
 */
public class BindEmailPresenter extends BasePresenter<BindEmailContract.View> implements BindEmailContract.MvpPresenter, Callback {
    UserModel userModel;

    @Inject
    public BindEmailPresenter(UserModel userModel) {
        this.userModel = userModel;
    }

    /**
     * 重新设置验证邮箱
     */
    @Override
    public void resetEmail() {
        checkViewAttached();
        if (checkInput()) {
            Map<String, Object> values = new HashMap<>();
            values.put("email", getMvpView().getEmail());
            getMvpView().showLoadingProgress("正在发送验证邮件...");
            userModel.updateUserInfo(values, this);
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
                case TaskManager.TASK_ID_UPDATE_USER_INFO:
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
                case TaskManager.TASK_ID_UPDATE_USER_INFO:
                    getMvpView().onRequestFail(errorCode, errorMsgStr);
                    break;
            }
        }
    }
}
