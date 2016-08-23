package com.hx.template.mvp.contract;

import com.hx.template.mvp.MvpView;
import com.hx.template.mvp.Presenter;

import java.io.File;

/**
 * Created by huangx on 2016/8/22.
 */
public interface PersonalInfoContract {
    interface View extends MvpView {
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
         * @param file
         */
        void updateAvatar(File file);
    }
}
