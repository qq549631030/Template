package com.hx.template.presenter;

import com.hx.template.mvpview.MvpView;

/**
 * Created by huangx on 2016/5/9.
 */
public interface Presenter<V extends MvpView> {
    void attachView(V mvpView);

    void detachView();

    void onDestroyed();
}
