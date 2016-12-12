package com.hx.template.mvp.presenter;

import com.hx.mvp.presenter.BasePresenter;
import com.hx.mvp.usecase.BaseUseCase;
import com.hx.template.mvp.usecase.single.user.ModifyPwdCase;
import com.hx.template.mvp.contract.ModifyPwdContract;
import com.hx.template.utils.StringUtils;

import javax.inject.Inject;

/**
 * Created by huangxiang on 16/8/13.
 */
public class ModifyPwdPresenter extends BasePresenter<ModifyPwdContract.View> implements ModifyPwdContract.MvpPresenter {

    private final ModifyPwdCase modifyPwdCase;

    @Inject
    public ModifyPwdPresenter(ModifyPwdCase modifyPwdCase) {
        this.modifyPwdCase = modifyPwdCase;
    }

    /**
     * 修改密码
     */
    @Override
    public void modifyPwd() {
        if (!isViewAttached()) {
            return;
        }
        if (checkInput()) {
            getMvpView().showLoadingProgress(true, "修改中...");
            ModifyPwdCase.RequestValues requestValues = new ModifyPwdCase.RequestValues(getMvpView().getOldPwd(), getMvpView().getNewPwd());
            modifyPwdCase.setRequestValues(requestValues);
            modifyPwdCase.setUseCaseCallback(new BaseUseCase.UseCaseCallback<ModifyPwdCase.ResponseValue>() {
                @Override
                public void onSuccess(ModifyPwdCase.ResponseValue response) {
                    if (isViewAttached()) {
                        getMvpView().showLoadingProgress(false);
                        getMvpView().modifySuccess();
                    }
                }

                @Override
                public void onError(String errorCode, Object... errorData) {
                    if (isViewAttached()) {
                        getMvpView().showLoadingProgress(false);
                        getMvpView().modifyFail(errorCode, errorData != null && errorData.length > 0 ? errorData[0].toString() : "");
                    }
                }
            });
            modifyPwdCase.run();
        }
    }

    private boolean checkInput() {
        if (StringUtils.isEmpty(getMvpView().getOldPwd())) {
            getMvpView().showError("旧密码不能为空");
            return false;
        }
        if (StringUtils.isEmpty(getMvpView().getNewPwd())) {
            getMvpView().showError("新密码不能为空");
            return false;
        }
        if (!getMvpView().getNewPwd().equals(getMvpView().getConfirmPwd())) {
            getMvpView().showError("两次输入的密码不一致");
            return false;
        }
        return true;
    }
}
