/**
 *
 */
package com.hx.template.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;

/**
 * @author hehh
 */
public class SharedPreferencesUtil {

    private static final String FILE_NAME = "share_date";

    public static void setParam(Context context, String key, Object object) {
        setParam(context, FILE_NAME, key, object);
    }

    /**
     * @param context
     * @param key
     * @param object
     */
    public static void setParam(Context context, String sharedFileName, String key, Object object) {
        if (object == null) {
            return;
        }
        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(sharedFileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }

        editor.commit();
    }

    public static Object getParam(Context context, String key,
                                  Object defaultObject) {
        return getParam(context, FILE_NAME, key, defaultObject);
    }

    /**
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getParam(Context context, String sharedFileName, String key,
                                  Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(sharedFileName,
                Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

}
