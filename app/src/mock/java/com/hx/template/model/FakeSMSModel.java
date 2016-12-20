package com.hx.template.model;

import android.os.Handler;

import com.hx.mvp.Callback;
import com.hx.template.model.SMSModel;
import com.hx.template.model.TaskManager;

/**
 * Created by huangxiang on 16/8/13.
 */
public class FakeSMSModel implements SMSModel {

    private Handler handler;

    public FakeSMSModel() {
        handler = new Handler();
    }

    /**
     * 请求短信验证码
     *
     * @param phoneNumber 手机号码
     * @param template    短信模板
     * @param callback    监听回调
     */
    @Override
    public void requestSMSCode(String phoneNumber, String template, final Callback callback) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(TaskManager.TASK_ID_REQUEST_SMS_CODE, 100);
            }
        }, 2000);
    }

    /**
     * 验证验证码
     *
     * @param phoneNumber 手机号码
     * @param smsCode     短信验证码
     * @param callback    监听回调
     */
    @Override
    public void verifySmsCode(String phoneNumber, final String smsCode, final Callback callback) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if ("666666".equals(smsCode)) {
                    callback.onSuccess();
                } else {
                    callback.onFailure("1012", "验证码错误");
                }
            }
        }, 2000);
    }

    @Override
    public boolean cancel(Object... args) {
        handler.removeCallbacksAndMessages(null);
        return true;
    }
}
