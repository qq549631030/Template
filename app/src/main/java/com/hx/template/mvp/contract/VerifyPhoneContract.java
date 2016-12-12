package com.hx.template.mvp.contract;

import com.hx.mvp.presenter.Presenter;
import com.hx.mvp.view.BaseMvpView;
import com.hx.template.mvp.SMSRequestView;
import com.hx.template.mvp.SMSVerifyView;

/**
 * Created by huangx on 2016/8/22.
 */
public interface VerifyPhoneContract {
    interface View extends BaseMvpView, SMSRequestView, SMSVerifyView {

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
