package com.hx.template.model.impl.bmob;

import com.hx.template.model.Callback;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by huangxiang on 16/8/20.
 */
public class BmobCallBackDeliver {
    public static void handleResult(Callback callback, int taskId, BmobException e, Object... data) {
        if (e == null) {
            BmobCallBackDeliver.deliverSuccess(callback, taskId, data);
        } else {
            BmobCallBackDeliver.deliverFailure(callback, taskId, e);
        }
    }

    public static void deliverSuccess(Callback callback, int taskId, Object... data) {
        callback.onSuccess(taskId, data);
    }

    public static void deliverFailure(Callback callback, int taskId, BmobException e) {
        callback.onFailure(taskId, Integer.toString(e.getErrorCode()), e.toString());
    }
}
