/*
 *  Copyright &amp;copy; 2014-2016  All rights reserved.
 *  Licensed under the 深圳中盟燧石科技 License, Version 1.0 (the "License");
 *
 */

package com.hx.template.global;

import android.util.Log;

import com.hx.template.BuildConfig;

/**
 * Created by huangxiang on 15/12/16.
 */
public class HXLog {
    private static String TAG = "HXLog";

    public static final boolean DEBUG = BuildConfig.DEBUG;

    public static void setTag(String tag) {
        HXLog.TAG = tag;
    }

    public static void v(String format, Object... params) {
        if (DEBUG) {
            String message = String.format(format, params);
            Log.v(TAG, message);
        }
    }

    public static void i(String format, Object... params) {
        if (DEBUG) {
            String message = String.format(format, params);
            Log.i(TAG, message);
        }
    }

    public static void w(String format, Object... params) {
        if (DEBUG) {
            String message = String.format(format, params);
            Log.w(TAG, message);
        }
    }

    public static void d(String format, Object... params) {
        if (DEBUG) {
            String message = String.format(format, params);
            Log.d(TAG, message);
        }
    }

    public static void e(String format, Object... params) {
        if (DEBUG) {
            String message = String.format(format, params);
            Log.e(TAG, message);
        }
    }
    public static void e(Throwable e) {
        if (DEBUG) {
           e.printStackTrace();
        }
    }
    
}
