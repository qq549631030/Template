package com.hx.template.presenter.impl;

import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvpview.impl.PersonalInfoUpdateMvpView;
import com.hx.template.presenter.BasePresenter;
import com.hx.template.presenter.itf.IPersonalInfoUpdatePresenter;

import javax.inject.Inject;

/**
 * Created by huangxiang on 16/8/14.
 */
public class PersonalInfoUpdatePresenter extends BasePresenter<PersonalInfoUpdateMvpView> implements IPersonalInfoUpdatePresenter {
    UserModel userModel;

    Callback callback = new Callback() {
        @Override
        public void onSuccess(int taskId, Object... data) {
            if (isViewAttached()) {
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
                String errorMsgStr = (errorMsg != null && errorMsg.length > 0) ? errorMsg[0].toString() : "";
                switch (taskId) {
                    case TaskManager.TASK_ID_UPDATE_USER_INFO:
                        getMvpView().updateFail(errorCode, errorMsgStr);
                        break;
                }
            }
        }
    };
    @Inject
    public PersonalInfoUpdatePresenter(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void onDestroyed() {
        super.onDestroyed();
        callback = null;
    }

    @Override
    public void updateInfo() {
        if (isViewAttached()) {
            int infoType = getMvpView().getDataType();
            switch (infoType) {
                case User.INFO_TYPE_NICKNAME:
                    String nicknameNew = getMvpView().getNewData();
                    User user = new User();
                    user.setNickname(nicknameNew);
                    userModel.updateUserInfo(user, callback);
                    break;
                default:
                    break;
            }
        }
    }
}
