package com.hx.template.entity;

import org.json.JSONObject;

/**
 * Created by huangx on 2016/8/23.
 */
public class BaseUser extends BaseEntity {
    private String username;
    private String password;
    private String email;
    private Boolean emailVerified;
    private String sessionToken;
    private String mobilePhoneNumber;
    private Boolean mobilePhoneNumberVerified;
    static JSONObject current;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public Boolean getMobilePhoneNumberVerified() {
        return mobilePhoneNumberVerified;
    }

    public void setMobilePhoneNumberVerified(Boolean mobilePhoneNumberVerified) {
        this.mobilePhoneNumberVerified = mobilePhoneNumberVerified;
    }

    public static JSONObject getCurrent() {
        return current;
    }

    public static void setCurrent(JSONObject current) {
        BaseUser.current = current;
    }

}
