package com.hx.template.mvp.presenter;

import com.hx.mvp.presenter.BasePresenter;
import com.hx.mvp.usecase.BaseUseCase;
import com.hx.template.mvp.usecase.complex.UpdateAvatarCase;
import com.hx.template.mvp.contract.PersonalInfoContract;

import java.io.File;

import javax.inject.Inject;

/**
 * Created by huangxiang on 16/8/14.
 */
public class PersonalInfoPresenter extends BasePresenter<PersonalInfoContract.View> implements PersonalInfoContract.MvpPresenter {
    private final UpdateAvatarCase updateAvatarCase;

    @Inject
    public PersonalInfoPresenter(UpdateAvatarCase updateAvatarCase) {
        this.updateAvatarCase = updateAvatarCase;
    }

    @Override
    public void updateAvatar(String userId, File file) {
        if (!isViewAttached()) {
            return;
        }
        if (checkInput(userId, file)) {
            getMvpView().showLoadingProgress(true, "正在修改头像...");
            UpdateAvatarCase.RequestValues requestValues = new UpdateAvatarCase.RequestValues(userId, file);
            updateAvatarCase.setRequestValues(requestValues);
            updateAvatarCase.setUseCaseCallback(new BaseUseCase.UseCaseCallback<UpdateAvatarCase.ResponseValue>() {
                @Override
                public void onSuccess(UpdateAvatarCase.ResponseValue response) {
                    if (isViewAttached()) {
                        getMvpView().showLoadingProgress(false);
                        getMvpView().avatarUpdateSuccess();
                    }
                }

                @Override
                public void onError(String errorCode, Object... errorData) {
                    if (isViewAttached()) {
                        getMvpView().showLoadingProgress(false);
                        getMvpView().avatarUpdateFail(errorCode, errorData != null && errorData.length > 0 ? errorData[0].toString() : "");
                    }
                }
            });
            updateAvatarCase.run();
        }
    }

    private boolean checkInput(String userId, File file) {
        if (file == null || !file.exists()) {
            getMvpView().showError("文件不存在");
            return false;
        }
        if (userId == null || userId.length() <= 0) {
            getMvpView().showError("用户不存在");
            return false;
        }
        return true;
    }
}
