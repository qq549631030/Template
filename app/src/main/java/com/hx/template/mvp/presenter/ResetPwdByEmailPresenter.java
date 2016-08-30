package com.hx.template.mvp.presenter;

import com.hx.template.Constant;
import com.hx.template.domain.usercase.UseCase;
import com.hx.template.domain.usercase.single.user.ResetPwdByEmailCase;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvp.contract.ResetPwdByEmailContract;
import com.hx.template.mvp.BasePresenter;
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
        checkViewAttached();
        if (checkInput()) {
            getMvpView().showLoadingProgress("正在重置...");
            ResetPwdByEmailCase.RequestValues requestValues = new ResetPwdByEmailCase.RequestValues(getMvpView().getEmail());
            resetPwdByEmailCase.setRequestValues(requestValues);
            resetPwdByEmailCase.setUseCaseCallback(new UseCase.UseCaseCallback<ResetPwdByEmailCase.ResponseValue>() {
                @Override
                public void onSuccess(ResetPwdByEmailCase.ResponseValue response) {
                    if (isViewAttached()) {
                        getMvpView().sendSuccess();
                    }
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    if (isViewAttached()) {
                        getMvpView().sendFail(errorCode, errorMsg);
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
