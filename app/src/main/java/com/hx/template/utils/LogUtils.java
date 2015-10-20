package com.hx.template.utils;

import android.util.Log;

/**
 * 
 * @author Huang Xiang
 * 
 */
public class LogUtils {
	private static boolean DEBUG = true;

	public static void e(String TAG, String message) {
		if (DEBUG) {
			Log.e(TAG, message);
		}
	}

	public static void d(String TAG, String message) {
		if (DEBUG) {
			Log.d(TAG, message);
		}
	}

	public static void v(String TAG, String message) {
		if (DEBUG) {
			Log.v(TAG, message);
		}
	}

	public static void i(String TAG, String message) {
		if (DEBUG) {
			Log.i(TAG, message);
		}
	}

	public static void w(String TAG, String message) {
		if (DEBUG) {
			Log.w(TAG, message);
		}
	}
}
