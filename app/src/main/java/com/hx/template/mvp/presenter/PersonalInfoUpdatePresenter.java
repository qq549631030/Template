package com.hx.template.mvp.presenter;

import com.hx.template.domain.usercase.UseCase;
import com.hx.template.domain.usercase.single.user.UpdateUserInfoCase;
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
public class PersonalInfoUpdatePresenter extends BasePresenter<PersonalInfoUpdateContract.View> implements PersonalInfoUpdateContract.MvpPresenter {

    private final UpdateUserInfoCase updateUserInfoCase;

    @Inject
    public PersonalInfoUpdatePresenter(UpdateUserInfoCase updateUserInfoCase) {
        this.updateUserInfoCase = updateUserInfoCase;
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
                UpdateUserInfoCase.RequestValues requestValues = new UpdateUserInfoCase.RequestValues(getMvpView().getUserId(), values);
                updateUserInfoCase.setRequestValues(requestValues);
                updateUserInfoCase.setUseCaseCallback(new UseCase.UseCaseCallback<UpdateUserInfoCase.ResponseValue>() {
                    @Override
                    public void onSuccess(UpdateUserInfoCase.ResponseValue response) {
                        if (isViewAttached()) {
                            getMvpView().hideLoadingProgress();
                            getMvpView().updateSuccess();
                        }
                    }

                    @Override
                    public void onError(String errorCode, String errorMsg) {
                        if (isViewAttached()) {
                            getMvpView().hideLoadingProgress();
                            getMvpView().updateFail(errorCode, errorMsg);
                        }
                    }
                });
                updateUserInfoCase.run();
                break;
            default:
                break;
        }
    }
}
