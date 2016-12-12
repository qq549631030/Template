package com.hx.mvp.view;

/**
 * Created by huangxiang on 2016/9/24.
 */

public interface ViewState<V extends BaseMvpView> {
    void apply(V view);

    void save(V view);
}
