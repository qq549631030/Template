package com.hx.template.mvpview.impl;

import com.hx.template.mvpview.MvpView;

/**
 * Created by huangxiang on 16/8/14.
 */
public interface PersonalInfoMvpView extends MvpView {
    /**
     * 头像修改成功
     */
    void avatarUpdateSuccess();

    /**
     * 头像修改失败
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    void avatarUpdateFail(String errorCode, String errorMsg);
}
