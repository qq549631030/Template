package com.hx.template.presenter.impl;

import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.FileModel;
import com.hx.template.model.UserModel;
import com.hx.template.mvpview.impl.PersonalInfoMvpView;
import com.hx.template.presenter.BasePresenter;
import com.hx.template.presenter.itf.IPersonalInfoPresenter;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by huangxiang on 16/8/14.
 */
public class PersonalInfoPresenter extends BasePresenter<PersonalInfoMvpView> implements IPersonalInfoPresenter {
    UserModel userModel;
    FileModel fileModel;

    public PersonalInfoPresenter(UserModel userModel, FileModel fileModel) {
        this.userModel = userModel;
        this.fileModel = fileModel;
    }

    @Override
    public void updateAvatar(File file) {
        if (isViewAttached()) {
            fileModel.uploadFile(file, new Callback() {
                @Override
                public void onSuccess(Object... data) {
                    if (data != null && data.length > 0) {
                        BmobFile bmobFile = (BmobFile) data[0];
                        User user = new User();
                        user.setAvatar(bmobFile);
                        userModel.updateUserInfo(user, new Callback() {
                            @Override
                            public void onSuccess(Object... data) {
                                if (isViewAttached()) {
                                    getMvpView().avatarUpdateSuccess();
                                }
                            }

                            @Override
                            public void onFailure(String errorCode, Object... errorMsg) {
                                if (isViewAttached()) {
                                    getMvpView().avatarUpdateFail(errorCode, (errorMsg != null && errorMsg.length > 0) ? errorMsg[0].toString() : "");
                                }
                            }
                        });
                    }
                }

                @Override
                public void onFailure(String errorCode, Object... errorMsg) {
                    if (isViewAttached()) {
                        getMvpView().avatarUpdateFail(errorCode, (errorMsg != null && errorMsg.length > 0) ? errorMsg[0].toString() : "");
                    }
                }
            });
        }
    }
}
