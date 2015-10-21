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

    public static String emailFormat = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    public static String phoneFormat = "^13[0-9]{9}$|14[0-9]{9}$|15[0-9]{9}$|17[0-9]{9}$|18[0-9]{9}$";// 手机号码正则表达式

    public static String SECRET_KEY = "templatekey";// 密码加密密钥

    /**
     * SharedPreference存储字段名
     */
    public static String pref_isFirst = "isFirst";// 是否第一次使用
    public static String pref_autoLogin = "autoLogin";// 是否自动登录

    public static String pref_remember_account = "remember_account";// 是否记住用户名
    public static String pref_remember_pwd = "remember_pwd";// 是否记住密码
    public static String pref_userName = "userName";// 登录名
    public static String pref_password = "password";// 密码
    public static String pref_current_user = "current_user";// 当前用户

    public static String crop_image_name = "crop.jpg";
    public static String camera_image_name = "camera.jpg";
    public static String photo_image_name = "photo.jpg";
    public static String logo_image_name = "logo.jpg";

    /**
     * keystore
     */
    public static String KEY_STORE_MD5 = "FA:FA:B3:05:B9:69:59:40:48:66:97:6C:ED:C9:68:03";
    public static String KEY_STORE_SHA1 = "E8:3D:AD:C6:14:01:C7:57:2B:62:21:75:32:E2:26:45:D0:CF:9D:46";
    public static String KEY_STORE_SHA256 = "C4:AD:25:EB:F9:AE:07:86:5E:ED:E9:57:D2:52:66:CF:80:0B:76:E9:9B:8D:7D:B9:AA:C8:F7:BA:1A:3C:89:7B";
    /**
     * 商户PID
     */
    public static final String PARTNER = "2088811486464185";
    /**
     * 商户收款账号
     */
    public static final String SELLER = "zhuweijie@chinasilex.com";
    /**
     * 服务器异步通知地址
     */
    public static final String NOTIFY_URL = "http://www.nxsmall.com/pay/alipay/notify";

    /**
     * 应用RSA私钥
     */
    public static final String APP_RSA_PRIVATE = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAL3fSc3TYVme/TnJDiPh31G8f1qBTus2YjhN4JXsE3DgNGW3v74d4lMRSqqgSZqwlGoldql70qaFXK6ExRtWM4dj0FrgZrcRiGI5fs8+e8QsCxcEE/7dwwPpS0A6RGTgImAQAm44RF5JMu6OPeEQDTL6kgFIzxFxpHAdSS3n4NxxAgMBAAECgYEAl1k1a2vjXZzuHPG248NYT78BhRMt5TAket0Y+GiF1qPhIHOn09PeZHiUHA5InZXDuBjEzbR15WbIyhPrSi8cQp7dGn0H5ZrNleyO7LWunrc5zkjbnDsBX2x7LhY7mpso9vqkF6BMrrRtRLp9w+M0zqHQrlQuRbFhxcjZ6RhjPwECQQDxAO1NuVeNqO5+XhRp+Etz6c12bAOtdiNgh21RB15cs/HSZFu65FfUMHcnIkqHROl8iHbJSgblFkvObjXVRWY9AkEAya/cyIgJzXtSg9OwOPnsa3v6RTzaEX96JQ6mrrLyLDmS1tl+b2XA+5ONtfks1iC4uEwbr0lTJ+wJ6T2sojBmRQJBALhgNywRMXRE20B+BSwVMtRY+EEy3ZVlHieJNA6bjotpdwW4lZzjeHXzJhUgXNgj1mTZmdbg9Wlpp1Eka7bfwxkCQQCpVW4ZL56LVy9AX0tlivBkjgLifuTYLB9xXYu0walyWCRhHtp4zQXqM0iXsE1kurRciAn2canPqu3nVlDLG7fBAkEA2ic9baFuRkHs7fCCONJeDtRVgjthTc9w0LQ9wt0RakFBJEii+W5auQx5HakamSew+D/2F8IpGLQGfImXyMC1LA==";
    /**
     * 应用RSA公钥
     */
    public static final String APP_RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC930nN02FZnv05yQ4j4d9RvH9agU7rNmI4TeCV7BNw4DRlt7++HeJTEUqqoEmasJRqJXape9KmhVyuhMUbVjOHY9Ba4Ga3EYhiOX7PPnvELAsXBBP+3cMD6UtAOkRk4CJgEAJuOEReSTLujj3hEA0y+pIBSM8RcaRwHUkt5+DccQIDAQAB";

    /**
     * 支付宝RSA公钥
     */
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";

}
