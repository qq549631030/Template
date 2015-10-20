package com.hx.template.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by huangxiang on 15/8/13.
 */
public class DeviceUtils {
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        return deviceId;
    }
}
