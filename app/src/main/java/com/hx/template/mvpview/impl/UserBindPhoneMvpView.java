package com.hx.template.mvpview.impl;

import com.hx.template.mvpview.MvpView;
import com.hx.template.mvpview.SMSRequestView;
import com.hx.template.mvpview.SMSVerifyView;

/**
 * Created by huangxiang on 16/8/13.
 */
public interface UserBindPhoneMvpView extends SMSRequestView, SMSVerifyView, MvpView {
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
