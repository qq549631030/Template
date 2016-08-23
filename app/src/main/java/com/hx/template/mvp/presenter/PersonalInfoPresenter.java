package com.hx.template.mvp.presenter;

import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.FileModel;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvp.contract.PersonalInfoContract;
import com.hx.template.mvp.BasePresenter;

import java.io.File;

import javax.inject.Inject;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by huangxiang on 16/8/14.
 */
public class PersonalInfoPresenter extends BasePresenter<PersonalInfoContract.View> implements PersonalInfoContract.MvpPresenter, Callback {
    UserModel userModel;
    FileModel fileModel;

    @Inject
    public PersonalInfoPresenter(UserModel userModel, FileModel fileModel) {
        this.userModel = userModel;
        this.fileModel = fileModel;
    }

    @Override
    public void updateAvatar(File file) {
        checkViewAttached();
        if (file != null && file.exists()) {
            getMvpView().showLoadingProgress("正在修改头像...");
            fileModel.uploadFile(file, this);
        } else {
            getMvpView().showError("文件不存在");
        }
    }

    @Override
    public void onSuccess(int taskId, Object... data) {
        if (isViewAttached()) {
            switch (taskId) {
                case TaskManager.TASK_ID_UPLOAD_FILE:
                    if (data != null && data.length > 0) {
                        BmobFile bmobFile = (BmobFile) data[0];
                        User user = new User();
                        user.setAvatar(bmobFile);
                        userModel.updateUserInfo(user, this);
                    }
                    break;

                case TaskManager.TASK_ID_UPDATE_USER_INFO:
                    getMvpView().hideLoadingProgress();
                    getMvpView().avatarUpdateSuccess();
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
                case TaskManager.TASK_ID_UPLOAD_FILE:
                    getMvpView().avatarUpdateFail(errorCode, errorMsgStr);
                    break;
                case TaskManager.TASK_ID_UPDATE_USER_INFO:
                    getMvpView().avatarUpdateFail(errorCode, errorMsgStr);
                    break;
            }
        }
    }
}
