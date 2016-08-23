package com.hx.template.mvp;

import com.hx.template.mvp.MvpView;

/**
 * Created by huangx on 2016/5/9.
 */
public interface Presenter<V extends MvpView> {
    void attachView(V mvpView);

    void detachView();

    void onDestroyed();
}
