package com.hx.template.mvpview.impl;

import com.hx.template.mvpview.LoadingView;
import com.hx.template.mvpview.MvpView;
import com.hx.template.mvpview.SMSRequestView;
import com.hx.template.mvpview.SMSVerifyView;
import com.hx.template.mvpview.ShowErrorView;

/**
 * Created by huangxiang on 16/8/13.
 */
public interface UserBindPhoneMvpView extends SMSRequestView, SMSVerifyView, MvpView, LoadingView, ShowErrorView {

    /**
     * 退出
     */
    void exit();
}
