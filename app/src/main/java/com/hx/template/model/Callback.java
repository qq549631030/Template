package com.hx.template.model;

/**
 * Created by huangx on 2016/8/12.
 */
public interface Callback {
    void onSuccess(int taskId, Object... data);

    void onFailure(int taskId, String errorCode, Object... errorMsg);
}
