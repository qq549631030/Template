package com.hx.template.mvp.presenter;

import com.hx.template.Constant;
import com.hx.template.domain.usercase.UseCase;
import com.hx.template.domain.usercase.single.user.RequestEmailVerifyCase;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvp.contract.EmailStateContract;
import com.hx.template.mvp.BasePresenter;
import com.hx.template.utils.StringUtils;

import java.util.regex.Pattern;

import javax.inject.Inject;

/**
 * Created by huangx on 2016/8/23.
 */
public class EmailStatePresenter extends BasePresenter<EmailStateContract.View> implements EmailStateContract.MvpPresenter {

    private final RequestEmailVerifyCase requestEmailVerifyCase;

    @Inject
    public EmailStatePresenter(RequestEmailVerifyCase requestEmailVerifyCase) {
        this.requestEmailVerifyCase = requestEmailVerifyCase;
    }

    /**
     * 重新发送验证邮件
     */
    @Override
    public void requestEmailVerify() {
        checkViewAttached();
        if (checkInput()) {
            getMvpView().showLoadingProgress("正在发送验证邮件...");
            RequestEmailVerifyCase.RequestValues requestValues = new RequestEmailVerifyCase.RequestValues(getMvpView().getEmail());
            requestEmailVerifyCase.setRequestValues(requestValues);
            requestEmailVerifyCase.setUseCaseCallback(new UseCase.UseCaseCallback<RequestEmailVerifyCase.ResponseValue>() {
                @Override
                public void onSuccess(RequestEmailVerifyCase.ResponseValue response) {
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
            requestEmailVerifyCase.run();
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
