package com.hx.template.model.impl.bmob;

import com.hx.mvp.Callback;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by huangxiang on 16/8/20.
 */
public class BmobCallBackDeliver {
    public static void handleResult(Callback callback, BmobException e, Object... data) {
        if (e == null) {
            BmobCallBackDeliver.deliverSuccess(callback, data);
        } else {
            BmobCallBackDeliver.deliverFailure(callback, e);
        }
    }

    public static void deliverSuccess(Callback callback, Object... data) {
        callback.onSuccess(data);
    }

    public static void deliverFailure(Callback callback, BmobException e) {
        callback.onFailure(Integer.toString(e.getErrorCode()), e.toString());
    }
}
