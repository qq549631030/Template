/*
 *  Copyright &amp;copy; 2014-2016  All rights reserved.
 *  Licensed under the 深圳中盟燧石科技 License, Version 1.0 (the "License");
 *
 */

package com.hx.template.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hx.template.BaseActivity;
import com.hx.template.Constant;
import com.hx.template.CustomApplication;
import com.hx.template.R;
import com.hx.template.demo.DemoMainActivity;
import com.hx.template.entity.User;
import com.hx.template.presenter.LoginPresenter;
import com.hx.template.presenter.impl.LoginPresenterImpl;
import com.hx.template.utils.ClickUtils;
import com.hx.template.utils.SecretUtils;
import com.hx.template.utils.SerializeUtil;
import com.hx.template.utils.SharedPreferencesUtil;
import com.hx.template.utils.ToastUtils;
import com.hx.template.view.ILoginView;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements ILoginView {

    @Bind(R.id.username)
    EditText username;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.login)
    Button login;
    @Bind(R.id.forget_password)
    TextView forgetPassword;

    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presenter = new LoginPresenterImpl(this);
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
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
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
                        presenter.login();
                    }
                }
                break;
        }
    }

    private boolean checkInput() {
        if (!(Pattern.matches(Constant.phoneFormat, getUserName())) && (!Pattern.matches(Constant.emailFormat, getPassword()))) {
            username.setError("用户名必须为手机号码或邮箱");
            return false;
        }
        return true;
    }

    @Override
    public String getUserName() {
        return username.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return password.getText().toString().trim();
    }

    @Override
    public void toMainActivity(User user) {
        if (user != null) {
            CustomApplication.currentLoginId = user.getId();
            CustomApplication.currentUser = user;
            try {
                SharedPreferencesUtil.setParam(getApplicationContext(), Constant.pref_userName, getUserName());
                SharedPreferencesUtil.setParam(getApplicationContext(), Constant.pref_password, SecretUtils.encrypt(Constant.SECRET_KEY, getPassword()));
                SharedPreferencesUtil.setParam(getApplicationContext(), Constant.pref_current_user, SerializeUtil.serialize(user));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Intent intent = new Intent(LoginActivity.this, DemoMainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showFailedError(String reason) {
        ToastUtils.showToast(getApplicationContext(), "登录失败：" + reason);
    }
}
