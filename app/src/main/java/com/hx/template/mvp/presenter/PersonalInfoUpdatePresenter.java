package com.hx.template.mvp.presenter;

import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvp.contract.PersonalInfoUpdateContract;
import com.hx.template.mvp.BasePresenter;

import java.util.HashMap;
import java.util.Map;

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
                Map<String, Object> values = new HashMap<>();
                values.put("nickname", nicknameNew);
                getMvpView().showLoadingProgress("修改中...");
                userModel.updateUserInfo(values, this);
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
