package com.hx.template.model;

import com.hx.mvp.Callback;

/**
 * Created by huangxiang on 16/8/13.
 */
public interface SMSModel extends BaseModel{
    /**
     * 请求短信验证码
     *
     * @param phoneNumber 手机号码
     * @param template    短信模板
     * @param callback    监听回调
     */
    void requestSMSCode(String phoneNumber, String template, Callback callback);

    /**
     * 验证验证码
     *
     * @param phoneNumber 手机号码
     * @param smsCode     短信验证码
     * @param callback    监听回调
     */
    void verifySmsCode(String phoneNumber, String smsCode, Callback callback);
}
