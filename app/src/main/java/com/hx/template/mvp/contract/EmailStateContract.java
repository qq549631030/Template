package com.hx.template.mvp.contract;

import com.hx.template.mvp.MvpView;
import com.hx.template.mvp.Presenter;

/**
 * Created by huangx on 2016/8/23.
 */
public interface EmailStateContract {
    interface View extends MvpView {
        /**
         * 获取请求的Email
         *
         * @return
         */
        String getEmail();

        /**
         * 请求成功
         */
        void onRequestSuccess();

        /**
         * 请求失败
         *
         * @param errorCode 错误码
         * @param errorMsg  错误信息
         */
        void onRequestFail(String errorCode, String errorMsg);
    }

    interface MvpPresenter extends Presenter<View> {
        /**
         * 重新发送验证邮件
         */
        void requestEmailVerify();
    }
}
