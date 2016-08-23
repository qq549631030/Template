package com.hx.template.mvp.contract;

import com.hx.template.mvp.MvpView;
import com.hx.template.mvp.SMSRequestView;
import com.hx.template.mvp.SMSVerifyView;
import com.hx.template.mvp.Presenter;

/**
 * Created by huangx on 2016/8/22.
 */
public interface VerifyPhoneContract {
    interface View extends MvpView, SMSRequestView, SMSVerifyView {

    }

    interface MvpPresenter extends Presenter<View> {
        /**
         * 获取短信验证码
         */
        void requestSMSCode();

        /**
         * 验证短信验证码
         */
        void verifySmsCode();
    }
}
