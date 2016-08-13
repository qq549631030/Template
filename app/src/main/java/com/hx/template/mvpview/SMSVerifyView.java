package com.hx.template.mvpview;

/**
 * Created by huangxiang on 16/8/13.
 */
public interface SMSVerifyView {
    /**
     * 获取手机号码
     *
     * @return
     */
    String getVerifyPhoneNumber();

    /**
     * 获取验证码
     *
     * @return
     */
    String getSMSCode();

    /**
     * 验证成功
     *
     * @param data 返回信息
     */
    void onVerifySuccess(Object... data);

    /**
     * 验证失败
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    void onVerifyFail(String errorCode, Object... errorMsg);
}
