package com.hx.template.qrcode.decode.zbar;

import java.util.concurrent.CountDownLatch;


import android.os.Handler;
import android.os.Looper;

import com.hx.template.qrcode.activity.CaptureActivity;
import com.hx.template.qrcode.decode.DecodeThread;

/**
 * huangx
 * Zbar解码线程
 */
public class ZbarDecodeThread extends DecodeThread {

   public ZbarDecodeThread(CaptureActivity activity) {
        super(activity);
    }

    @Override
    protected Handler initHandler(CaptureActivity activity) {
        return new ZbarDecodeHandler(activity);
    }
}
