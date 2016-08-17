package com.hx.template.presenter.impl;

import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.UserModel;
import com.hx.template.mvpview.impl.BindEmailMvpView;
import com.hx.template.presenter.BasePresenter;
import com.hx.template.presenter.itf.IBindEmailPresenter;

/**
 * Created by huangxiang on 16/8/17.
 */
public class BindEmailPresenter extends BasePresenter<BindEmailMvpView> implements IBindEmailPresenter {
    UserModel userModel;

    public BindEmailPresenter(UserModel userModel) {
        this.userModel = userModel;
    }

    /**
     * 重新发送验证邮件
     */
    @Override
    public void requestEmailVerify() {
        if (isViewAttached()) {
            userModel.requestEmailVerify(getMvpView().getEmail(), new Callback() {
                @Override
                public void onSuccess(Object... data) {
                    if (isViewAttached()) {
                        getMvpView().onRequestSuccess();
                    }
                }

                @Override
                public void onFailure(String errorCode, Object... errorMsg) {
                    if (isViewAttached()) {
                        getMvpView().onRequestFail(errorCode, (errorMsg != null && errorMsg.length > 0) ? errorMsg[0].toString() : "");
                    }
                }
            });
        }
    }

    /**
     * 重新设置验证邮箱
     */
    @Override
    public void resetEmail() {
        if (isViewAttached()) {
            User user = new User();
            user.setEmail(getMvpView().getEmail());
            userModel.updateUserInfo(user, new Callback() {
                @Override
                public void onSuccess(Object... data) {
                    if (isViewAttached()) {
                        getMvpView().onRequestSuccess();
                    }
                }

                @Override
                public void onFailure(String errorCode, Object... errorMsg) {
                    if (isViewAttached()) {
                        getMvpView().onRequestFail(errorCode, (errorMsg != null && errorMsg.length > 0) ? errorMsg[0].toString() : "");
                    }
                }
            });
        }
    }
}
