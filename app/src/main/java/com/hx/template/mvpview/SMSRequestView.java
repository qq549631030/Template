package com.hx.template.mvpview;

/**
 * Created by huangxiang on 16/8/13.
 */
public interface SMSRequestView {
    /**
     * 获取手机号码
     *
     * @return
     */
    String getRequestPhoneNumber();

    /**
     * 获取短信验证模板
     *
     * @return
     */
    String getSMSTemplate();

    /**
     * 获取验证码成功
     *
     * @param data 返回信息
     */
    void onRequestSuccess(Object... data);

    /**
     * 获取验证码失败
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    void onRequestFail(String errorCode, String errorMsg);
}
