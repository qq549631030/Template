/*
 *  Copyright &amp;copy; 2014-2016  All rights reserved.
 *  Licensed under the 深圳中盟燧石科技 License, Version 1.0 (the "License");
 *
 */

package com.hx.template.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hx.template.base.BaseActivity;
import com.hx.template.Constant;
import com.hx.template.HttpConfig;
import com.hx.template.R;
import com.hx.template.entity.User;
import com.hx.template.entity.enums.ErrorCode;
import com.hx.template.global.FastClickUtils;
import com.hx.template.http.volley.HttpListener;
import com.hx.template.http.volley.HttpPostUtils;
import com.hx.template.http.HttpParams;
import com.hx.template.http.HttpParseUtils;
import com.hx.template.http.HttpReturn;
import com.hx.template.utils.DeviceUtils;
import com.hx.template.utils.SharedPreferencesUtil;
import com.hx.template.utils.ToastUtils;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.username)
    EditText username;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.register)
    TextView register;
    @Bind(R.id.to_login)
    TextView toLogin;

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mProgressDialog = new ProgressDialog(this);
    }

    @OnClick({ R.id.register, R.id.to_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                if (checkInput()) {
                    if (FastClickUtils.isTimeToProcess(R.id.register)) {
                        register(username.getText().toString().trim(), password.getText().toString().trim(), DeviceUtils.getDeviceId(RegisterActivity.this), null);
                    }
                }
                break;
            case R.id.to_login:
                if (FastClickUtils.isTimeToProcess(R.id.to_login)) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                break;
        }
    }

    private boolean checkPhone() {
        if (TextUtils.isEmpty(username.getText().toString().trim())) {
            ToastUtils.showToast(getApplicationContext(), "手机号码不能为空");
            return false;
        }
        if (!(Pattern.matches(Constant.phoneFormat, username.getText().toString().trim()))) {
            ToastUtils.showToast(getApplicationContext(), "手机号码有误");
            return false;
        }
        return true;
    }

    private boolean checkInput() {
        if (!checkPhone()) {
            return false;
        }
        return true;
    }


    private void register(String userName, String password, String deviceId, String authCode) {
        mProgressDialog.setMessage("注册中...");
        mProgressDialog.show();
        final Map<String, String> params = new HashMap<String, String>();
        params.put(HttpParams.Register.userName, userName);
        params.put(HttpParams.Register.password, password);
        params.put(HttpParams.Register.deviceNo, deviceId);
        params.put(HttpParams.Register.authCode, authCode);
        HttpPostUtils.doLazyFromPostRequest(RegisterActivity.this, HttpConfig.REGISTER_URL, params, new HttpListener() {

            @Override
            public void onPass(JSONObject jsonObject) {
                Type type = new TypeToken<HttpReturn.RegisterReturn>() {
                }.getType();
                HttpReturn.RegisterReturn mReturn = HttpParseUtils.parseReturn(jsonObject, type);
                if (mReturn != null) {
                    if (mReturn.getStatus() == 1) {
                        User user = mReturn.getData();
                        if (user != null) {
                            registerSuccess(params);
                        } else {
                            mProgressDialog.dismiss();
                        }
                    } else {
                        mProgressDialog.dismiss();
                        ErrorCode code = mReturn.getCode();
                        if (code != null) {
                            ToastUtils.showToast(getApplicationContext(), getResources().getString(mReturn.getCode().getRes()));
                        } else {
                            String msg = mReturn.getMsg();
                            if (TextUtils.isEmpty(msg)) {
                                ToastUtils.showToast(getApplicationContext(), getResources().getString(R.string.error_unknow));
                            } else {
                                ToastUtils.showToast(getApplicationContext(), msg);
                            }
                        }
                    }
                } else {
                    mProgressDialog.dismiss();
                    ToastUtils.showToast(getApplicationContext(), getResources().getString(R.string.error_unknow));
                }
            }

            @Override
            public void onError(String ErrorMsg, int errorCode) {
                mProgressDialog.dismiss();
            }
        }, true);
    }

    private void registerSuccess(Map<String, String> params) {
        SharedPreferencesUtil.setParam(getApplicationContext(), Constant.pref_userName, params.get(HttpParams.Login.userName));
        ToastUtils.showToast(getApplicationContext(), "注册成功");
        finish();
    }
}
