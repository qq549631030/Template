/*
 *  Copyright &amp;copy; 2014-2016  All rights reserved.
 *  Licensed under the 深圳中盟燧石科技 License, Version 1.0 (the "License");
 *
 */

package com.hx.template.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.hx.template.base.BaseActivity;
import com.hx.template.Constant;
import com.hx.template.CustomApplication;
import com.hx.template.R;
import com.hx.template.demo.DemoMainActivity;
import com.hx.template.entity.User;
import com.hx.template.presenter.impl.LoginPresenter;
import com.hx.template.utils.ClickUtils;
import com.hx.template.utils.ToastUtils;
import com.hx.template.mvpview.LoginMvpView;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginMvpView {

    @Bind(R.id.username)
    EditText username;
    @Bind(R.id.password)
    EditText password;
    ProgressDialog progressDialog;
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog(this);
        presenter = new LoginPresenter();
        presenter.attachView(this);
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
            ToastUtils.showToast(getApplicationContext(), "用户名必须为手机号码或邮箱");
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
        CustomApplication.saveLoginInfo(user, getPassword());
        Intent intent = new Intent(LoginActivity.this, DemoMainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showFailedError(String reason) {
        ToastUtils.showToast(getApplicationContext(), "登录失败：" + reason);
    }

    @Override
    protected void onDestroy() {
        progressDialog = null;
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showLoadingProgress(String msg) {
        if (progressDialog != null) {
            progressDialog.setMessage(msg);
            progressDialog.show();
        }
    }

    @Override
    public void hideLoadingProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
