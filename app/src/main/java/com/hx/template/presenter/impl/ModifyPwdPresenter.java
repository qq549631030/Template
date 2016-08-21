package com.hx.template.presenter.impl;

import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvpview.impl.ModifyPwdMvpView;
import com.hx.template.presenter.BasePresenter;
import com.hx.template.presenter.itf.IModifyPwdPresenter;

import javax.inject.Inject;

/**
 * Created by huangxiang on 16/8/13.
 */
public class ModifyPwdPresenter extends BasePresenter<ModifyPwdMvpView> implements IModifyPwdPresenter {
    private UserModel userModel;
    Callback callback = new Callback() {
        @Override
        public void onSuccess(int taskId, Object... data) {
            if (isViewAttached()) {
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
                String errorMsgStr = (errorMsg != null && errorMsg.length > 0) ? errorMsg[0].toString() : "";
                switch (taskId) {
                    case TaskManager.TASK_ID_MODIFY_PWD:
                        getMvpView().modifyFail(errorCode, errorMsgStr);
                        break;
                }
            }
        }
    };
    @Inject
    public ModifyPwdPresenter(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void onDestroyed() {
        super.onDestroyed();
        callback = null;
    }

    /**
     * 修改密码
     */
    @Override
    public void modifyPwd() {
        if (!isViewAttached()) {
            return;
        }
        userModel.modifyPwd(getMvpView().getOldPwd(), getMvpView().getNewPwd(), callback);
    }
}
