package com.hx.template.mvp.contract;

import com.hx.template.mvp.MvpView;
import com.hx.template.mvp.Presenter;

/**
 * Created by huangx on 2016/8/22.
 */
public interface RegisterContract {
    interface View extends MvpView {
        /**
         * 获取用户名
         *
         * @return
         */
        String getUserName();

        /**
         * 获取密码
         *
         * @return
         */
        String getPassword();


        /**
         * 获取再次输入的密码
         *
         * @return
         */
        String getConfirmPassword();

        /**
         * 注册成功
         */
        void registerSuccess();

        /**
         * 注册失败
         *
         * @param errorCode 错误码
         * @param errorMsg  错误信息
         */
        void registerFail(String errorCode, String errorMsg);
    }

    interface MvpPresenter extends Presenter<View> {
        /**
         * 注册
         */
        void register();
    }
}
