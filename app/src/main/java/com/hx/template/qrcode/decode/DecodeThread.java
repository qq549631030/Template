package com.hx.template.qrcode.decode;

import android.os.Handler;
import android.os.Looper;

import com.hx.template.qrcode.activity.CaptureActivity;

import java.util.concurrent.CountDownLatch;

/**
 * Created by huangx on 2016/3/30.
 * 解码线程
 */
public abstract class DecodeThread extends Thread {
    protected CaptureActivity activity;
    private Handler handler;
    private final CountDownLatch handlerInitLatch;

    public DecodeThread(CaptureActivity activity) {
        this.activity = activity;
        handlerInitLatch = new CountDownLatch(1);
    }

    public Handler getHandler() {
        try {
            handlerInitLatch.await();
        } catch (InterruptedException ie) {
            // continue?
        }
        return handler;
    }

    @Override
    public void run() {
        Looper.prepare();
        handler = initHandler(activity);
        handlerInitLatch.countDown();
        Looper.loop();
    }

    protected abstract Handler initHandler(CaptureActivity activity);
}
