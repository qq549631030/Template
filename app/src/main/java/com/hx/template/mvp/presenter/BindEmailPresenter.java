package com.hx.template.mvp.presenter;

import com.hx.template.Constant;
import com.hx.template.domain.usercase.UseCase;
import com.hx.template.domain.usercase.single.user.UpdateUserInfoCase;
import com.hx.template.mvp.contract.BindEmailContract;
import com.hx.template.mvp.BasePresenter;
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
        checkViewAttached();
        if (checkInput()) {
            Map<String, Object> values = new HashMap<>();
            values.put("email", getMvpView().getEmail());
            getMvpView().showLoadingProgress("正在发送验证邮件...");
            UpdateUserInfoCase.RequestValues requestValues = new UpdateUserInfoCase.RequestValues(userId, values);
            updateUserInfoCase.setRequestValues(requestValues);
            updateUserInfoCase.setUseCaseCallback(new UseCase.UseCaseCallback<UpdateUserInfoCase.ResponseValue>() {
                @Override
                public void onSuccess(UpdateUserInfoCase.ResponseValue response) {
                    if (isViewAttached()) {
                        getMvpView().hideLoadingProgress();
                        getMvpView().onRequestSuccess();
                    }
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    if (isViewAttached()) {
                        getMvpView().hideLoadingProgress();
                        getMvpView().onRequestFail(errorCode, errorMsg);
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
