package com.hx.template.mvpview.impl;

import com.hx.template.mvpview.MvpView;

/**
 * Created by huangxiang on 16/8/13.
 */
public interface ModifyPwdMvpView extends MvpView {
    /**
     * 获取旧密码
     *
     * @return
     */
    String getOldPwd();

    /**
     * 获取新密码
     *
     * @return
     */
    String getNewPwd();

    /**
     * 修改成功
     */
    void modifySuccess();

    /**
     * 修改失败
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    void modifyFail(String errorCode, String errorMsg);
}
