package com.hx.template.presenter.itf;

/**
 * Created by huangxiang on 16/8/13.
 */
public interface IUserBindPhonePresenter {
    /**
     * 获取短信验证码
     */
    void requestSMSCode();

    /**
     * 验证短信验证码
     */
    void verifySmsCode();

    /**
     * 绑定手机号码
     */
    void bindPhone();
}
