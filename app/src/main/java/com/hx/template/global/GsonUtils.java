package com.hx.template.global;

import com.google.gson.Gson;

/**
 * Created by huangx on 2016/5/18.
 */
public class GsonUtils {
    public static String toJson(Object source) {
        Gson gson = new Gson();
        return gson.toJson(source);
    }
}
