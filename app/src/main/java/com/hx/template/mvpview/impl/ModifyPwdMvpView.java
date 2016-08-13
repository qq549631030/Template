package com.hx.template.mvpview.impl;

import com.hx.template.mvpview.LoadingView;
import com.hx.template.mvpview.MvpView;
import com.hx.template.mvpview.ShowErrorView;

/**
 * Created by huangxiang on 16/8/13.
 */
public interface ModifyPwdMvpView extends LoadingView, MvpView, ShowErrorView {
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
     * 退出
     */
    void exit();
}
