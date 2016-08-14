package com.hx.template.presenter.impl;

import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.UserModel;
import com.hx.template.mvpview.impl.RegisterMvpView;
import com.hx.template.presenter.BasePresenter;
import com.hx.template.presenter.itf.IRegisterPresenter;

/**
 * Created by huangxiang on 16/8/12.
 */
public class RegisterPresenter extends BasePresenter<RegisterMvpView> implements IRegisterPresenter {
    private UserModel userModel;

    public RegisterPresenter(UserModel userModel) {
        this.userModel = userModel;
        if (userModel == null) {
            throw new IllegalArgumentException("userModel can't be null");
        }
    }

    /**
     * 注册
     */
    @Override
    public void register() {
        if (!isViewAttached()) {
            return;
        }
        userModel.register(getMvpView().getUserName(), getMvpView().getPassword(), new Callback() {
            @Override
            public void onSuccess(Object... data) {
                if (isViewAttached()) {
                    getMvpView().registerSuccess();
                }
            }

            @Override
            public void onFailure(String errorCode, Object... errorMsg) {
                if (isViewAttached()) {
                    getMvpView().registerFail(errorCode, (errorMsg != null && errorMsg.length > 0) ? errorMsg[0].toString() : "");
                }
            }
        });
    }
}
