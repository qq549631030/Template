/**
 * Copyright &copy; 2014-2016  All rights reserved.
 * <p/>
 * Licensed under the 深圳中盟燧石科技 License, Version 1.0 (the "License");
 */
package com.hx.template.entity.enums;

/**
 * @author huangxiang
 * @ClassName: ErrorCode
 * @Description:错误代码
 * @date 2015-3-17 下午2:16:55
 */
public enum ErrorCode {
    ;
    private String id;
    private int res;

    ErrorCode() {
    }

    ErrorCode(String id, int res) {
        this.id = id;
        this.res = res;
    }

    public static ErrorCode getInstance(String id) {
        for (ErrorCode object : ErrorCode.values()) {
            if (id.equals(object.getId())) {
                return object;
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

}
