package com.hx.mvp.presenter;

/**
 * Created by huangxiang on 16/8/19.
 */
public interface PresenterFactory<T extends Presenter> {
    T create();
}
