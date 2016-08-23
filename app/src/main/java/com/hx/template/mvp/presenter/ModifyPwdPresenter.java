package com.hx.template.mvp.presenter;

import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvp.contract.ModifyPwdContract;
import com.hx.template.mvp.BasePresenter;
import com.hx.template.utils.StringUtils;

import javax.inject.Inject;

/**
 * Created by huangxiang on 16/8/13.
 */
public class ModifyPwdPresenter extends BasePresenter<ModifyPwdContract.View> implements ModifyPwdContract.MvpPresenter, Callback {

    private UserModel userModel;

    @Inject
    public ModifyPwdPresenter(UserModel userModel) {
        this.userModel = userModel;
    }

    /**
     * 修改密码
     */
    @Override
    public void modifyPwd() {
        checkViewAttached();
        if (checkInput()) {
            getMvpView().showLoadingProgress("修改中...");
            userModel.modifyPwd(getMvpView().getOldPwd(), getMvpView().getNewPwd(), this);
        }
    }


    private boolean checkInput() {
        if (StringUtils.isEmpty(getMvpView().getOldPwd())) {
            getMvpView().showError("旧密码不能为空");
            return false;
        }
        if (StringUtils.isEmpty(getMvpView().getNewPwd())) {
            getMvpView().showError("新密码不能为空");
            return false;
        }
        if (!getMvpView().getNewPwd().equals(getMvpView().getConfirmPwd())) {
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
                case TaskManager.TASK_ID_MODIFY_PWD:
                    getMvpView().modifySuccess();
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
                case TaskManager.TASK_ID_MODIFY_PWD:
                    getMvpView().modifyFail(errorCode, errorMsgStr);
                    break;
            }
        }
    }
}
