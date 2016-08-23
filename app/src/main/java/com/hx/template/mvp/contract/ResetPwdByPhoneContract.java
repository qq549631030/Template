package com.hx.template.mvp.contract;

import com.hx.template.mvp.MvpView;
import com.hx.template.mvp.SMSRequestView;
import com.hx.template.mvp.Presenter;

/**
 * Created by huangx on 2016/8/22.
 */
public interface ResetPwdByPhoneContract {
    interface View extends MvpView, SMSRequestView {
        /**
         * 获取验证码
         *
         * @return
         */
        String getSMSCode();

        /**
         * 获取密码
         *
         * @return
         */
        String getPassword();

        /**
         * 获取再次确认密码
         *
         * @return
         */
        String getConfirmPassword();


        /**
         * 重置成功
         */
        void resetSuccess();

        /**
         * 重置失败
         *
         * @param errorCode 错误码
         * @param errorMsg  错误信息
         */
        void resetFail(String errorCode, String errorMsg);
    }

    interface MvpPresenter extends Presenter<View> {
        /**
         * 获取短信验证码
         */
        void requestSMSCode();

        /**
         * 手机号码重置密码
         */
        void resetPasswordBySMSCode();
    }
}
