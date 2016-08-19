package com.hx.template.mvpview.impl;

import com.hx.template.mvpview.MvpView;
import com.hx.template.mvpview.SMSRequestView;

/**
 * Created by huangx on 2016/8/19.
 */
public interface ResetPwdByEmailMvpView extends MvpView {
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
