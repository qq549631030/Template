package com.hx.template.mvp.presenter;

import com.hx.mvp.presenter.BasePresenter;
import com.hx.mvp.usecase.BaseUseCase;
import com.hx.template.mvp.usecase.single.user.UpdateUserInfoCase;
import com.hx.template.entity.User;
import com.hx.template.mvp.contract.PersonalInfoUpdateContract;

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
        if (!isViewAttached()) {
            return;
        }
        int infoType = getMvpView().getDataType();
        switch (infoType) {
            case User.INFO_TYPE_NICKNAME:
                String nicknameNew = getMvpView().getNewData();
                Map<String, Object> values = new HashMap<>();
                values.put("nickname", nicknameNew);
                getMvpView().showLoadingProgress(true, "修改中...");
                UpdateUserInfoCase.RequestValues requestValues = new UpdateUserInfoCase.RequestValues(getMvpView().getUserId(), values);
                updateUserInfoCase.setRequestValues(requestValues);
                updateUserInfoCase.setUseCaseCallback(new BaseUseCase.UseCaseCallback<UpdateUserInfoCase.ResponseValue>() {
                    @Override
                    public void onSuccess(UpdateUserInfoCase.ResponseValue response) {
                        if (isViewAttached()) {
                            getMvpView().showLoadingProgress(false);
                            getMvpView().updateSuccess();
                        }
                    }

                    @Override
                    public void onError(String errorCode, Object... errorData) {
                        if (isViewAttached()) {
                            getMvpView().showLoadingProgress(false);
                            getMvpView().updateFail(errorCode, errorData != null && errorData.length > 0 ? errorData[0].toString() : "");
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
