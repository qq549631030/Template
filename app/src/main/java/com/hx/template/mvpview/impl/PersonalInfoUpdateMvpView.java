package com.hx.template.mvpview.impl;

import com.hx.template.mvpview.MvpView;

/**
 * Created by huangxiang on 16/8/14.
 */
public interface PersonalInfoUpdateMvpView extends MvpView {
    String getNewData();

    int getDataType();

    /**
     * 修改成功
     */
    void updateSuccess();

    /**
     * 修改失败
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    void updateFail(String errorCode, String errorMsg);
}
