package com.hx.template.mvp.contract;

import com.hx.mvp.presenter.Presenter;
import com.hx.mvp.view.BaseMvpView;
import com.hx.template.entity.User;

/**
 * Created by huangx on 2016/8/22.
 */
public interface LoginContract {
    interface View extends BaseMvpView {
        /**
         * 获取用户名
         *
         * @return
         */
        String getUserName();

        /**
         * 设置用户名
         *
         * @param userName
         */
        void setUserName(String userName);

        /**
         * 获取密码
         *
         * @return
         */
        String getPassword();

        /**
         * 设置密码
         *
         * @param password
         */
        void setPassword(String password);

        /**
         * 登录成功
         *
         * @param user 用户
         */
        void loginSuccess(User user);

        /**
         * 登录失败
         *
         * @param errorCode 错误码
         * @param errorMsg  错误信息
         */
        void loginFail(String errorCode, String errorMsg);
    }

    interface MvpPresenter extends Presenter<View> {
        /**
         * 登录
         */
        void login();
    }
}
