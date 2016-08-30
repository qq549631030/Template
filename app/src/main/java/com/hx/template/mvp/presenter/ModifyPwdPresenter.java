package com.hx.template.mvp.presenter;

import com.hx.template.domain.usercase.UseCase;
import com.hx.template.domain.usercase.single.user.ModifyPwdCase;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;
import com.hx.template.mvp.contract.ModifyPwdContract;
import com.hx.template.mvp.BasePresenter;
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
        checkViewAttached();
        if (checkInput()) {
            getMvpView().showLoadingProgress("修改中...");
            ModifyPwdCase.RequestValues requestValues = new ModifyPwdCase.RequestValues(getMvpView().getOldPwd(), getMvpView().getNewPwd());
            modifyPwdCase.setRequestValues(requestValues);
            modifyPwdCase.setUseCaseCallback(new UseCase.UseCaseCallback<ModifyPwdCase.ResponseValue>() {
                @Override
                public void onSuccess(ModifyPwdCase.ResponseValue response) {
                    if (isViewAttached()) {
                        getMvpView().hideLoadingProgress();
                        getMvpView().modifySuccess();
                    }
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    if (isViewAttached()) {
                        getMvpView().hideLoadingProgress();
                        getMvpView().modifyFail(errorCode, errorMsg);
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
