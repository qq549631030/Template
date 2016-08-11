/**
 * Copyright &copy; 2014-2016  All rights reserved.
 * <p/>
 * Licensed under the 深圳中盟燧石科技 License, Version 1.0 (the "License");
 */
package com.hx.template;

/**
 * @author huangxiang
 * @ClassName: Constant
 * @Description: 常量
 * @date 2015-3-17 下午2:37:01
 */
public class Constant {

    public static final boolean DEBUG = true;

    public static final String PROJECT_NAME = "template";// 密码加密密钥

    public static final String emailFormat = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    public static final String phoneFormat = "^13[0-9]{9}$|14[0-9]{9}$|15[0-9]{9}$|17[0-9]{9}$|18[0-9]{9}$";// 手机号码正则表达式

    public static final String SECRET_KEY = "templatekey";// 密码加密密钥

    /**
     * SharedPreference存储字段名
     */
    public static final String pref_isFirst = "isFirst";// 是否第一次使用
    public static final String pref_autoLogin = "autoLogin";// 是否自动登录

    public static final String pref_remember_account = "remember_account";// 是否记住用户名
    public static final String pref_remember_pwd = "remember_pwd";// 是否记住密码
    public static final String pref_userName = "userName";// 登录名
    public static final String pref_password = "password";// 密码
    public static final String pref_current_user = "current_user";// 当前用户

    public static final String crop_image_name = "crop.jpg";
    public static final String camera_image_name = "camera.jpg";
    public static final String photo_image_name = "photo.jpg";
    public static final String logo_image_name = "logo.jpg";

    /**
     * keystore
     */
    public static final String KEY_STORE_MD5 = "FA:FA:B3:05:B9:69:59:40:48:66:97:6C:ED:C9:68:03";
    public static final String KEY_STORE_SHA1 = "E8:3D:AD:C6:14:01:C7:57:2B:62:21:75:32:E2:26:45:D0:CF:9D:46";
    public static final String KEY_STORE_SHA256 = "C4:AD:25:EB:F9:AE:07:86:5E:ED:E9:57:D2:52:66:CF:80:0B:76:E9:9B:8D:7D:B9:AA:C8:F7:BA:1A:3C:89:7B";
    /**
     * 商户PID
     */
    public static final String PARTNER = "2088502836518781";
    /**
     * 商户收款账号
     */
    public static final String SELLER = "15870679047";
    /**
     * 服务器异步通知地址
     */
    public static final String NOTIFY_URL = "http://www.nxsmall.com/pay/alipay/notify";

    /**
     * 应用RSA私钥
     */
    public static final String APP_RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOC60vpDioHlQu4SRSNMhMDzeHbb25O/2PkePPCMyTF9fjiacX0SUJnWx5/d2zb1/CBVbCZtxPW6aKD7+UHje8iYMcAgJOyGHqPV8IQ1NCIkOubvfvT2seR7Y5Mf/JzqLYP1BKr2S5TS6TswoI+gdeYJgFOW1wO7sfpWtFsNpz0bAgMBAAECgYAFWk3WKi+tYmGSvBqreZIb6nCvHBOB1Gr9nAvFiwTrydpszLjxSrNixqvgke87wuYrev1leO3dEICXfQj12c5D8HUcQ9jj45UWqvHvcHVDZ6YezSknoXaE+THAKOv5Rr/bZ/F/wu9KkJaq2tAW/L00x60Yoy/ZK1NCTT++VLUf0QJBAPiRg7r6RIGPrkxmxNjJ5KL8DOOi3IuBt4N3PsEMYmkl9B32Cgq/BqqJB2vVtzsXU/y3eaifmtEaHbUyjvLqtakCQQDnctqo30LA/sOtMMxAlY7cq4IY/JngF/8YmpnLpZPDlzK45a16Dd4LT2bbbuAhxU/QdC1b2d91Q8rV+pF7v48jAkAWMggtPgO3/fh2YgbZxM1hsFgGS5id7GtSledIna577SOrOTkUVJJnz+yVuHOwFxksy8VVRUsxuL9GWslg74xhAkEAl2/2FGKoqeDIJcz1CBkTdd2ebTzybccluXKGyNHZAjQvEv3s6RyjuaDFezRr4Iza8VjiyOOgq3ilAidGA8NOIwJBAJ9t3IefSbQ08CybfOEZEGvPEghKpV0CSAfX6/EBiLKwe5Jc+qYRdFQdZsa3F2pwSkoP69gZKz2pomybgUe92hg=";
    /**
     * 应用RSA公钥
     */
    public static final String APP_RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDgutL6Q4qB5ULuEkUjTITA83h229uTv9j5HjzwjMkxfX44mnF9ElCZ1sef3ds29fwgVWwmbcT1umig+/lB43vImDHAICTshh6j1fCENTQiJDrm73709rHke2OTH/yc6i2D9QSq9kuU0uk7MKCPoHXmCYBTltcDu7H6VrRbDac9GwIDAQAB";

    /**
     * 支付宝RSA公钥
     */
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";

}
