package com.hx.template.model.impl.bmob;

import com.hx.template.model.Callback;
import com.hx.template.model.SMSModel;
import com.hx.template.model.TaskManager;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by huangxiang on 16/8/13.
 */
public class BmobSMSModel implements SMSModel {
    /**
     * 请求短信验证码
     *
     * @param phoneNumber 手机号码
     * @param template    短信模板
     * @param callback    监听回调
     */
    @Override
    public void requestSMSCode(String phoneNumber, String template, final Callback callback) {
        BmobSMS.requestSMSCode(phoneNumber, template, new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                BmobCallBackDeliver.handleResult(callback, TaskManager.TASK_ID_REQUEST_SMS_CODE, e, integer);
            }
        });
    }

    /**
     * 验证验证码
     *
     * @param phoneNumber 手机号码
     * @param smsCode     短信验证码
     * @param callback    监听回调
     */
    @Override
    public void verifySmsCode(String phoneNumber, String smsCode, final Callback callback) {
        BmobSMS.verifySmsCode(phoneNumber, smsCode, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                BmobCallBackDeliver.handleResult(callback, TaskManager.TASK_ID_VERIFY_SMS_CODE, e);
            }
        });
    }
}
