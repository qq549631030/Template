package com.hx.template.presenter.impl;

import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.UserModel;
import com.hx.template.mvpview.impl.PersonalInfoUpdateMvpView;
import com.hx.template.presenter.BasePresenter;
import com.hx.template.presenter.itf.IPersonalInfoUpdatePresenter;

/**
 * Created by huangxiang on 16/8/14.
 */
public class PersonalInfoUpdatePresenter extends BasePresenter<PersonalInfoUpdateMvpView> implements IPersonalInfoUpdatePresenter {
    UserModel userModel;

    public PersonalInfoUpdatePresenter(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void updateInfo() {
        if (isViewAttached()) {
            int infoType = getMvpView().getDataType();
            switch (infoType) {
                case User.INFO_TYPE_NICKNAME:
                    String nicknameNew = getMvpView().getNewData();
                    User user = new User();
                    user.setNickname(nicknameNew);
                    userModel.updateUserInfo(user, new Callback() {
                        @Override
                        public void onSuccess(Object... data) {
                            if (isViewAttached()) {
                                getMvpView().updateSuccess();
                            }
                        }

                        @Override
                        public void onFailure(String errorCode, Object... errorMsg) {
                            if (isViewAttached()) {
                                getMvpView().updateFail(errorCode, (errorMsg != null && errorMsg.length > 0) ? errorMsg[0].toString() : "");
                            }
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    }
}
