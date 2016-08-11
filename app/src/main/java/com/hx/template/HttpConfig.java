/**
 * Copyright &amp;copy; 2014-2016  All rights reserved.
 * <p/>
 * Licensed under the 深圳中盟燧石科技 License, Version 1.0 (the "License");
 */
package com.hx.template;

/**
 * @author huangxiang
 */
public class HttpConfig {

    //    public static String HOME = "http://120.24.153.133";
    public static final String HOME = "http://www.mianfei88.com";
//    public static String HOME = "http://wo510751575.vicp.net:56619";

    public static final String PATH = "/admin/api/";

    public static final String BASE_URL = HOME + PATH;
    /**
     * 短信验证码(验)
     */
    public static final String MEMBER_SENDAUTHCODE_URL = BASE_URL + "member/sendAuthCode";
    /**
     * 校验验证码(验)
     */
    public static final String MEMBER_VALIDAUTHCODE = BASE_URL + "member/validAuthCode";

    /**
     * 登录(验)
     */
    public static final String LOGIN_URL = BASE_URL + "member/login";

    /**
     * 注册(验)
     */
    public static final String REGISTER_URL = BASE_URL + "member/register";
}
