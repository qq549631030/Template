/*
 *  Copyright &amp;copy; 2014-2016  All rights reserved.
 *  Licensed under the 深圳中盟燧石科技 License, Version 1.0 (the "License");
 *
 */

package com.hx.template.http;

import android.util.Log;

import com.hx.template.BuildConfig;

/**
 * Created by huangxiang on 15/12/16.
 */
public class HXLog {
    private static String TAG = "HXLog";

    public static boolean DEBUG = BuildConfig.DEBUG;

    public static void setTag(String tag) {
        HXLog.TAG = tag;
    }

    public static void v(String message) {
        if (DEBUG) {
            Log.v(TAG, message);
        }
    }

    public static void i(String message) {
        if (DEBUG) {
            Log.i(TAG, message);
        }
    }

    public static void w(String message) {
        if (DEBUG) {
            Log.w(TAG, message);
        }
    }

    public static void d(String message) {
        if (DEBUG) {
            Log.d(TAG, message);
        }
    }

    public static void e(String message) {
        if (DEBUG) {
            Log.e(TAG, message);
        }
    }
}
