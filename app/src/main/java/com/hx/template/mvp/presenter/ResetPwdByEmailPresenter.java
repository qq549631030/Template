package com.hx.template.mvp.presenter;

import com.hx.mvp.presenter.BasePresenter;
import com.hx.mvp.usecase.BaseUseCase;
import com.hx.template.Constant;
import com.hx.template.mvp.usecase.single.user.ResetPwdByEmailCase;
import com.hx.template.mvp.contract.ResetPwdByEmailContract;
import com.hx.template.utils.StringUtils;

import java.util.regex.Pattern;

import javax.inject.Inject;

/**
 * Created by huangx on 2016/8/19.
 */
public class ResetPwdByEmailPresenter extends BasePresenter<ResetPwdByEmailContract.View> implements ResetPwdByEmailContract.MvpPresenter {

    private final ResetPwdByEmailCase resetPwdByEmailCase;

    @Inject
    public ResetPwdByEmailPresenter(ResetPwdByEmailCase resetPwdByEmailCase) {
        this.resetPwdByEmailCase = resetPwdByEmailCase;
    }

    /**
     * 邮箱重置密码
     */
    @Override
    public void resetPasswordByEmail() {
        if (!isViewAttached()) {
            return;
        }
        if (checkInput()) {
            getMvpView().showLoadingProgress(true, "正在重置...");
            ResetPwdByEmailCase.RequestValues requestValues = new ResetPwdByEmailCase.RequestValues(getMvpView().getEmail());
            resetPwdByEmailCase.setRequestValues(requestValues);
            resetPwdByEmailCase.setUseCaseCallback(new BaseUseCase.UseCaseCallback<ResetPwdByEmailCase.ResponseValue>() {
                @Override
                public void onSuccess(ResetPwdByEmailCase.ResponseValue response) {
                    if (isViewAttached()) {
                        getMvpView().showLoadingProgress(false);
                        getMvpView().sendSuccess();
                    }
                }

                @Override
                public void onError(String errorCode, Object... errorData) {
                    if (isViewAttached()) {
                        getMvpView().showLoadingProgress(false);
                        getMvpView().sendFail(errorCode, errorData != null && errorData.length > 0 ? errorData[0].toString() : "");
                    }
                }
            });
            resetPwdByEmailCase.run();
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
