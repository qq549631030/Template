package com.hx.template.utils;


import com.hx.template.HttpConfig;

/**
 * Created by huangxiang on 15/8/23 23:49.
 */
public class UrlUtils {
    public static String getRealUrl(String url) {
        if (url == null) {
            return "";
        }
        if (url.startsWith("http")) {
            return url;
        } else {
            return HttpConfig.HOME + url;
        }
    }
}
