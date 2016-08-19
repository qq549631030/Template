package com.hx.template.presenter.itf;

/**
 * Created by huangx on 2016/8/19.
 */
public interface IResetPwdByPhonePresenter {
    /**
     * 获取短信验证码
     */
    void requestSMSCode();

    /**
     * 手机号码重置密码
     */
    void resetPasswordBySMSCode();
}
