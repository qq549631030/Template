/**
 * Copyright &copy; 2014-2016  All rights reserved.
 * <p/>
 * Licensed under the 深圳中盟燧石科技 License, Version 1.0 (the "License");
 */
package com.hx.template.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hx.template.entity.enums.ErrorCode;
import com.hx.template.entity.enums.adapter.ErrorCodeAdapter;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * @author huangxiang
 * @ClassName: HttpParseUtils
 * @Description: HTTP返回数据解析
 * @date 2015-3-11 下午4:11:51
 */
public class HttpParseUtils {

    /**
     * 使用gson解析
     *
     * @param jsonString
     * @param type       eg:Type type = new TypeToken<HttpResponse.BaseReturn>()
     *                   {}.getType();
     * @return T 返回类型
     * @throws
     * @Title: parseReturn
     * @author huangxiang
     * @Description: 解析任意类型返回
     */
    public static <T> T parseReturn(JSONObject jsonString, Type type) {
        T mReturn = null;
        try {
            Gson gson = new GsonBuilder().enableComplexMapKeySerialization().registerTypeAdapter(ErrorCode.class, new ErrorCodeAdapter()).create();
            mReturn = gson.fromJson(jsonString.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mReturn;
    }

}
