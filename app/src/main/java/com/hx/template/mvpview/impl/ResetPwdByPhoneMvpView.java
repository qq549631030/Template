package com.hx.template.mvpview.impl;

import com.hx.template.mvpview.MvpView;
import com.hx.template.mvpview.SMSRequestView;

/**
 * Created by huangx on 2016/8/19.
 */
public interface ResetPwdByPhoneMvpView extends SMSRequestView, MvpView {
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
