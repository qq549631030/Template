package com.hx.template.mvpview.impl;

import com.hx.template.mvpview.LoadingView;
import com.hx.template.mvpview.MvpView;

/**
 * Created by huangx on 2016/8/12.
 */
public interface RegisterMvpView extends MvpView, LoadingView {
    String getUserName();

    String getPassword();

}
