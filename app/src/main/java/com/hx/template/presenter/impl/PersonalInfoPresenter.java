package com.hx.template.presenter.impl;

import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.FileModel;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvpview.impl.PersonalInfoMvpView;
import com.hx.template.presenter.BasePresenter;
import com.hx.template.presenter.itf.IPersonalInfoPresenter;

import java.io.File;

import javax.inject.Inject;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by huangxiang on 16/8/14.
 */
public class PersonalInfoPresenter extends BasePresenter<PersonalInfoMvpView> implements IPersonalInfoPresenter {
    UserModel userModel;
    FileModel fileModel;

    Callback callback = new Callback() {
        @Override
        public void onSuccess(int taskId, Object... data) {
            if (isViewAttached()) {
                switch (taskId) {
                    case TaskManager.TASK_ID_UPLOAD_FILE:
                        if (data != null && data.length > 0) {
                            BmobFile bmobFile = (BmobFile) data[0];
                            User user = new User();
                            user.setAvatar(bmobFile);
                            userModel.updateUserInfo(user, callback);
                        }
                        break;

                    case TaskManager.TASK_ID_UPDATE_USER_INFO:
                        getMvpView().avatarUpdateSuccess();
                        break;
                }
            }
        }

        @Override
        public void onFailure(int taskId, String errorCode, Object... errorMsg) {
            if (isViewAttached()) {
                String errorMsgStr = (errorMsg != null && errorMsg.length > 0) ? errorMsg[0].toString() : "";
                switch (taskId) {
                    case TaskManager.TASK_ID_UPLOAD_FILE:
                        getMvpView().avatarUpdateFail(errorCode, errorMsgStr);
                        break;
                    case TaskManager.TASK_ID_UPDATE_USER_INFO:
                        getMvpView().avatarUpdateFail(errorCode, errorMsgStr);
                        break;
                }
            }
        }
    };
    @Inject
    public PersonalInfoPresenter(UserModel userModel, FileModel fileModel) {
        this.userModel = userModel;
        this.fileModel = fileModel;
    }

    @Override
    public void onDestroyed() {
        super.onDestroyed();
        callback = null;
    }

    @Override
    public void updateAvatar(File file) {
        if (isViewAttached()) {
            fileModel.uploadFile(file, callback);
        }
    }
}
