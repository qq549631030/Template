package com.hx.template.mvp.presenter;

import com.hx.template.domain.usercase.UseCase;
import com.hx.template.domain.usercase.complex.UpdateAvatarCase;
import com.hx.template.mvp.BasePresenter;
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
        checkViewAttached();
        if (checkInput(userId, file)) {
            getMvpView().showLoadingProgress("正在修改头像...");
            UpdateAvatarCase.RequestValues requestValues = new UpdateAvatarCase.RequestValues(userId, file);
            updateAvatarCase.setRequestValues(requestValues);
            updateAvatarCase.setUseCaseCallback(new UseCase.UseCaseCallback<UpdateAvatarCase.ResponseValue>() {
                @Override
                public void onSuccess(UpdateAvatarCase.ResponseValue response) {
                    if (!isViewAttached()) {
                        getMvpView().hideLoadingProgress();
                        getMvpView().avatarUpdateSuccess();
                    }
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    if (!isViewAttached()) {
                        getMvpView().hideLoadingProgress();
                        getMvpView().avatarUpdateFail(errorCode, errorMsg);
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
