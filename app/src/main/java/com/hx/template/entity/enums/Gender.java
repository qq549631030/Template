package com.hx.template.entity.enums;

/**
 * Created by huangxiang on 16/8/14.
 */
public enum Gender {
    MALE("0", "男"),
    Female("1", "女");

    private String code;
    private String value;

    public static Gender getInstanceByCode(String code) {
        for (Gender object : Gender.values()) {
            if (object.getCode().equals(code)) {
                return object;
            }
        }
        return null;
    }

    Gender(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
