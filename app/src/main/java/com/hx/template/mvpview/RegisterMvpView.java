package com.hx.template.mvpview;

import com.hx.template.mvpview.itf.LoadingView;

/**
 * Created by huangx on 2016/8/12.
 */
public interface RegisterMvpView extends MvpView, LoadingView {
    String getUserName();

    String getPassword();

}
