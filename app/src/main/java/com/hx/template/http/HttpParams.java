package com.hx.template.http;

/**
 * Created by huangxiang on 15/10/20.
 */
public class HttpParams {
    /**
     * 分页参数
     */
    public static class Page {
        public static final String pageNo = "pageNo";// 分页号
        public static final String pageSize = "pageSize";// 分页大小
    }

    /**
     * 获取手机验证码
     */
    public static class SendCode {
        public static final String mobile = "mobile";// 手机号码
    }

    /**
     * 注册参数
     */
    public static class Register {
        public static final String userName = "userName";// 用户名
        public static final String password = "password";// 密码
        public static final String openId = "openId";// 第三方openId
        public static final String deviceNo = "deviceNo";// 设备唯一ID
        public static final String authCode = "authCode";// 验证码
    }

    /**
     * 登录参数
     */
    public static class Login {
        public static final String deviceNo = "deviceNo";// 设备唯一ID
        public static final String userName = "userName";// 用户名
        public static final String password = "password";// 密码
    }

    /**
     * 重置密码参数
     *
     * @author huangxiang
     */
    public static class ResetPwd {
        public static final String userName = "userName";// 用户名
        public static final String password = "password";// 密码
        public static final String authCode = "authCode";// 验证码
    }

    /**
     * 修改密码参数
     *
     * @author huangxiang
     */
    public static class ModifyPwd {
        public static final String oldPassword = "oldPassword";// 旧密码
        public static final String newPassword = "newPassword";// 新密码
    }
}
