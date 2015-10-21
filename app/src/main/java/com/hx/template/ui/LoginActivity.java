/*
 *  Copyright &amp;copy; 2014-2016  All rights reserved.
 *  Licensed under the 深圳中盟燧石科技 License, Version 1.0 (the "License");
 *
 */

package com.hx.template.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hx.template.BaseActivity;
import com.hx.template.Constant;
import com.hx.template.CustomApplication;
import com.hx.template.HttpConfig;
import com.hx.template.R;
import com.hx.template.demo.DemoMainActivity;
import com.hx.template.entity.User;
import com.hx.template.entity.enums.ErrorCode;
import com.hx.template.http.HttpListener;
import com.hx.template.http.HttpPostUtils;
import com.hx.template.http.impl.HttpParams;
import com.hx.template.http.impl.HttpParseUtils;
import com.hx.template.http.impl.HttpReturn;
import com.hx.template.utils.ClickUtils;
import com.hx.template.utils.SecretUtils;
import com.hx.template.utils.SerializeUtil;
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

public class LoginActivity extends BaseActivity {

    @Bind(R.id.username)
    EditText username;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.login)
    Button login;
    @Bind(R.id.forget_password)
    TextView forgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.register:
                //TODO implement
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.forget_password, R.id.login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forget_password:
                if (ClickUtils.notFastClick()) {
                    //TODO implement
                }
                break;
            case R.id.login:
                if (checkInput()) {
                    if (ClickUtils.notFastClick()) {
                        login(username.getText().toString().trim(), password.getText().toString().trim());
                    }
                }
                break;
        }
    }

    private boolean checkInput() {
        if (!(Pattern.matches(Constant.phoneFormat, username.getText().toString().trim())) && (!Pattern.matches(Constant.emailFormat, username.getText().toString().trim()))) {
            username.setError("用户名必须为手机号码或邮箱");
            return false;
        }
        return true;
    }

    private void login(String userName, String password) {
        mProgressDialog.setMessage("登录中...");
        mProgressDialog.show();
        final Map<String, String> params = new HashMap<String, String>();
        if (!TextUtils.isEmpty(userName)) {
            params.put(HttpParams.Login.userName, userName);
        }
        if (!TextUtils.isEmpty(password)) {
            params.put(HttpParams.Login.password, password);
        }

        HttpPostUtils.doLazyFromPostRequest(LoginActivity.this, HttpConfig.LOGIN_URL, params, new HttpListener() {

            @Override
            public void onPass(JSONObject jsonObject) {
                Type type = new TypeToken<HttpReturn.LoginReturn>() {
                }.getType();
                HttpReturn.LoginReturn mReturn = HttpParseUtils.parseReturn(jsonObject, type);
                if (mReturn != null) {
                    if (mReturn.getStatus() == 1) {
                        User user = mReturn.getData();
                        mProgressDialog.dismiss();
                        loginSuccess(params, user);
                    } else {
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
                        mProgressDialog.dismiss();
                    }
                } else {
                    ToastUtils.showToast(getApplicationContext(), getResources().getString(R.string.error_unknow));
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onError(String ErrorMsg, int errorCode) {
                mProgressDialog.dismiss();
            }
        }, true);
    }

    private void loginSuccess(Map<String, String> params, User user) {
        if (user != null) {
            CustomApplication.currentLoginId = user.getId();
            CustomApplication.currentUser = user;
            try {
                SharedPreferencesUtil.setParam(getApplicationContext(), Constant.pref_userName, params.get(HttpParams.Login.userName));
                SharedPreferencesUtil.setParam(getApplicationContext(), Constant.pref_password, SecretUtils.encrypt(Constant.SECRET_KEY, params.get(HttpParams.Login.password)));
                SharedPreferencesUtil.setParam(getApplicationContext(), Constant.pref_current_user, SerializeUtil.serialize(user));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        SharedPreferencesUtil.setParam(getApplicationContext(), Constant.pref_autoLogin, true);
        Intent intent = new Intent(LoginActivity.this, DemoMainActivity.class);
        startActivity(intent);
        finish();
    }
}
