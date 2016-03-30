package com.hx.template.qrcode.decode;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.hx.template.R;
import com.hx.template.qrcode.activity.CaptureActivity;

/**
 * Created by huangx on 2016/3/30.
 * 描述: 接受消息后解码
 */
public abstract class DecodeHandler extends Handler {
    protected CaptureActivity activity = null;

    public DecodeHandler(CaptureActivity activity) {
        this.activity = activity;
    }

    @Override
    public void handleMessage(Message message) {
        switch (message.what) {
            case R.id.decode:
                decode((byte[]) message.obj, message.arg1, message.arg2);
                break;
            case R.id.quit:
                Looper.myLooper().quit();
                break;
        }
    }

    public abstract void decode(byte[] data, int width, int height);
}
