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
     * 获取手机验证码
     */
    public static class SendCode {
        public static String mobile = "mobile";// 手机号码
    }

    /**
     * 注册参数
     */
    public static class Register {
        public static String userName = "userName";// 用户名
        public static String password = "password";// 密码
        public static String openId = "openId";// 第三方openId
        public static String deviceNo = "deviceNo";// 设备唯一ID
        public static String authCode = "authCode";// 验证码
    }

    /**
     * 登录参数
     */
    public static class Login {
        public static String deviceNo = "deviceNo";// 设备唯一ID
        public static String userName = "userName";// 用户名
        public static String password = "password";// 密码
    }

    /**
     * 重置密码参数
     *
     * @author huangxiang
     */
    public static class ResetPwd {
        public static String userName = "userName";// 用户名
        public static String password = "password";// 密码
        public static String authCode = "authCode";// 验证码
    }

    /**
     * 修改密码参数
     *
     * @author huangxiang
     */
    public static class ModifyPwd {
        public static String oldPassword = "oldPassword";// 旧密码
        public static String newPassword = "newPassword";// 新密码
    }
}
