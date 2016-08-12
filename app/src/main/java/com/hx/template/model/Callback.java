package com.hx.template.model;

/**
 * Created by huangx on 2016/8/12.
 */
public interface Callback {
    void onSuccess(Object... data);

    void onFailure(String errorCode, Object... errorMsg);
}
