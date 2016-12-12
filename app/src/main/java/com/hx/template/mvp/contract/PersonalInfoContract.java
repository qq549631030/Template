package com.hx.template.mvp.contract;

import com.hx.mvp.presenter.Presenter;
import com.hx.mvp.view.BaseMvpView;

import java.io.File;

/**
 * Created by huangx on 2016/8/22.
 */
public interface PersonalInfoContract {
    interface View extends BaseMvpView {
        /**
         * 头像修改成功
         */
        void avatarUpdateSuccess();

        /**
         * 头像修改失败
         *
         * @param errorCode 错误码
         * @param errorMsg  错误信息
         */
        void avatarUpdateFail(String errorCode, String errorMsg);
    }

    interface MvpPresenter extends Presenter<View> {
        /**
         * 修改头像
         *
         * @param userId
         * @param file
         */
        void updateAvatar(String userId, File file);
    }
}
