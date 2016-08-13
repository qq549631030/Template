package com.hx.template.presenter.impl;

import com.hx.template.model.Callback;
import com.hx.template.model.UserModel;
import com.hx.template.mvpview.impl.ModifyPwdMvpView;
import com.hx.template.presenter.BasePresenter;
import com.hx.template.presenter.itf.IModifyPwdPresenter;

/**
 * Created by huangxiang on 16/8/13.
 */
public class ModifyPwdPresenter extends BasePresenter<ModifyPwdMvpView> implements IModifyPwdPresenter {
    private UserModel userModel;

    public ModifyPwdPresenter(UserModel userModel) {
        this.userModel = userModel;
    }

    /**
     * 修改密码
     */
    @Override
    public void modifyPwd() {
        if (!isViewAttached()) {
            return;
        }
        getMvpView().showLoadingProgress("修改中...");
        userModel.modifyPwd(getMvpView().getOldPwd(), getMvpView().getNewPwd(), new Callback() {
            @Override
            public void onSuccess(Object... data) {
                if (isViewAttached()) {
                    getMvpView().exit();
                }
            }

            @Override
            public void onFailure(String errorCode, Object... errorMsg) {
                if (isViewAttached()) {
                    if (errorMsg != null && errorMsg.length > 0) {
                        getMvpView().showFailedError(errorMsg[0].toString());
                    }
                }
            }
        });
    }
}
