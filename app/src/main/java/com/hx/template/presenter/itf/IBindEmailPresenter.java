package com.hx.template.presenter.itf;

/**
 * Created by huangxiang on 16/8/17.
 */
public interface IBindEmailPresenter {
    /**
     * 重新发送验证邮件
     */
    void requestEmailVerify();

    /**
     * 重新设置验证邮箱
     */
    void resetEmail();
}
