package com.hx.template.http.impl;

/**
 * Created by huangxiang on 15/10/20.
 */
public class HttpParams {
    /**
     * 分页参数
     */
    public static class Page {
        public static String pageNo = "pageNo";// 分页号
        public static String pageSize = "pageSize";// 分页大小
    }

    /**
     * 登录参数
     */
    public static class Login {
        public static String deviceNo = "deviceNo";// 设备唯一ID
        public static String userName = "userName";// 用户名
        public static String password = "password";// 密码
    }
}
