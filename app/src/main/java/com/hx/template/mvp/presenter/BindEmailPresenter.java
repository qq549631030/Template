package com.hx.template.mvp.presenter;

import com.hx.mvp.presenter.BasePresenter;
import com.hx.mvp.usecase.BaseUseCase;
import com.hx.template.Constant;
import com.hx.template.mvp.usecase.single.user.UpdateUserInfoCase;
import com.hx.template.mvp.contract.BindEmailContract;
import com.hx.template.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.inject.Inject;

/**
 * Created by huangxiang on 16/8/17.
 */
public class BindEmailPresenter extends BasePresenter<BindEmailContract.View> implements BindEmailContract.MvpPresenter {
    private final UpdateUserInfoCase updateUserInfoCase;

    @Inject
    public BindEmailPresenter(UpdateUserInfoCase updateUserInfoCase) {
        this.updateUserInfoCase = updateUserInfoCase;
    }

    /**
     * 重新设置验证邮箱
     */
    @Override
    public void resetEmail(String userId) {
        if (!isViewAttached()) {
            return;
        }
        if (checkInput()) {
            Map<String, Object> values = new HashMap<>();
            values.put("email", getMvpView().getEmail());
            getMvpView().showLoadingProgress(true, "正在发送验证邮件...");
            UpdateUserInfoCase.RequestValues requestValues = new UpdateUserInfoCase.RequestValues(userId, values);
            updateUserInfoCase.setRequestValues(requestValues);
            updateUserInfoCase.setUseCaseCallback(new BaseUseCase.UseCaseCallback<UpdateUserInfoCase.ResponseValue>() {
                @Override
                public void onSuccess(UpdateUserInfoCase.ResponseValue response) {
                    if (isViewAttached()) {
                        getMvpView().showLoadingProgress(false);
                        getMvpView().onRequestSuccess();
                    }
                }

                @Override
                public void onError(String errorCode, Object... errorData) {
                    if (isViewAttached()) {
                        getMvpView().showLoadingProgress(false);
                        getMvpView().onRequestFail(errorCode, errorData != null && errorData.length > 0 ? errorData[0].toString() : "");
                    }
                }
            });
            updateUserInfoCase.run();
        }
    }

    private boolean checkInput() {
        if (StringUtils.isEmpty(getMvpView().getEmail())) {
            getMvpView().showError("邮箱地址不能为空");
            return false;
        }
        if (!Pattern.compile(Constant.emailFormat).matcher(getMvpView().getEmail()).matches()) {
            getMvpView().showError("请输入正确的邮箱地址");
            return false;
        }
        return true;
    }
}
