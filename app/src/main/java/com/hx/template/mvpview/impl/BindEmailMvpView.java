package com.hx.template.mvpview.impl;

import com.hx.template.entity.User;
import com.hx.template.mvpview.MvpView;

/**
 * Created by huangxiang on 16/8/17.
 */
public interface BindEmailMvpView extends MvpView {
    /**
     * 获取请求的Email
     *
     * @return
     */
    String getEmail();

    /**
     * 请求成功
     */
    void onRequestSuccess();

    /**
     * 请求失败
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    void onRequestFail(String errorCode, String errorMsg);
}
