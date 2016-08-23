package com.hx.template.mvp.presenter;

import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvp.contract.PersonalInfoUpdateContract;
import com.hx.template.mvp.BasePresenter;

import javax.inject.Inject;

/**
 * Created by huangxiang on 16/8/14.
 */
public class PersonalInfoUpdatePresenter extends BasePresenter<PersonalInfoUpdateContract.View> implements PersonalInfoUpdateContract.MvpPresenter, Callback {

    UserModel userModel;

    @Inject
    public PersonalInfoUpdatePresenter(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void updateInfo() {
        checkViewAttached();
        int infoType = getMvpView().getDataType();
        switch (infoType) {
            case User.INFO_TYPE_NICKNAME:
                String nicknameNew = getMvpView().getNewData();
                User user = new User();
                user.setNickname(nicknameNew);
                getMvpView().showError("修改中...");
                userModel.updateUserInfo(user, this);
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(int taskId, Object... data) {
        if (isViewAttached()) {
            getMvpView().hideLoadingProgress();
            switch (taskId) {
                case TaskManager.TASK_ID_UPDATE_USER_INFO:
                    getMvpView().updateSuccess();
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
                    getMvpView().updateFail(errorCode, errorMsgStr);
                    break;
            }
        }
    }
}
