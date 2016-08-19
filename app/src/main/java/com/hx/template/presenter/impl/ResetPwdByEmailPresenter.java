package com.hx.template.presenter.impl;

import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.UserModel;
import com.hx.template.mvpview.impl.ResetPwdByEmailMvpView;
import com.hx.template.presenter.BasePresenter;
import com.hx.template.presenter.itf.IResetPwdByEmailPresenter;

/**
 * Created by huangx on 2016/8/19.
 */
public class ResetPwdByEmailPresenter extends BasePresenter<ResetPwdByEmailMvpView> implements IResetPwdByEmailPresenter {

    UserModel userModel;

    public ResetPwdByEmailPresenter(UserModel userModel) {
        this.userModel = userModel;
    }

    /**
     * 邮箱重置密码
     */
    @Override
    public void resetPasswordByEmail() {
        if (isViewAttached()) {
            userModel.resetPasswordByEmail(getMvpView().getEmail(), new Callback() {
                @Override
                public void onSuccess(Object... data) {
                    if (isViewAttached()) {
                        getMvpView().sendSuccess();
                    }
                }

                @Override
                public void onFailure(String errorCode, Object... errorMsg) {
                    if (isViewAttached()) {
                        getMvpView().sendFail(errorCode, (errorMsg != null && errorMsg.length > 0) ? errorMsg[0].toString() : "");
                    }
                }
            });
        }
    }
}
