package com.hx.template.mvp;

import com.hx.template.model.Cancelable;

/**
 * Created by huangx on 2016/5/9.
 */
public interface Presenter<V extends MvpView> extends Cancelable{
    void attachView(V mvpView);

    void detachView();
}
