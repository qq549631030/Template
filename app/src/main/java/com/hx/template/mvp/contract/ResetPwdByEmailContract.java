package com.hx.template.mvp.contract;

import com.hx.template.mvp.MvpView;
import com.hx.template.mvp.Presenter;

/**
 * Created by huangx on 2016/8/22.
 */
public interface ResetPwdByEmailContract {
    interface View extends MvpView {
        /**
         * 获取邮箱
         *
         * @return
         */
        String getEmail();

        /**
         * 邮件发送成功
         */
        void sendSuccess();

        /**
         * 邮件发送失败
         *
         * @param errorCode 错误码
         * @param errorMsg  错误信息
         */
        void sendFail(String errorCode, String errorMsg);
    }

    interface MvpPresenter extends Presenter<View> {
        /**
         * 邮箱重置密码
         */
        void resetPasswordByEmail();
    }
}
