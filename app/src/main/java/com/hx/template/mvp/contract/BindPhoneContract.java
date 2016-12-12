package com.hx.template.mvp.contract;

import com.hx.mvp.presenter.Presenter;
import com.hx.mvp.view.BaseMvpView;
import com.hx.template.mvp.SMSRequestView;
import com.hx.template.mvp.SMSVerifyView;

/**
 * Created by huangx on 2016/8/22.
 */
public interface BindPhoneContract {
    interface View extends BaseMvpView, SMSRequestView, SMSVerifyView {
        /**
         * 绑定成功
         */
        void bindSuccess();

        /**
         * 绑定失败
         *
         * @param errorCode 错误码
         * @param errorMsg  错误信息
         */
        void bindFail(String errorCode, String errorMsg);
    }

    interface MvpPresenter extends Presenter<View> {
        /**
         * 获取短信验证码
         */
        void requestSMSCode();

        /**
         * 绑定手机号码
         */
        void bindPhone(String userId);
    }
}
