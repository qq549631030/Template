package com.hx.template.mvp;

/**
 * Created by huangxiang on 2016/9/24.
 */

public interface ViewState<V extends MvpView> {
    void apply(V view);

    void save(V view);
}
