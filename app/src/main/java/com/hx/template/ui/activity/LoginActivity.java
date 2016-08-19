/*
 *  Copyright &amp;copy; 2014-2016  All rights reserved.
 *  Licensed under the 深圳中盟燧石科技 License, Version 1.0 (the "License");
 *
 */

package com.hx.template.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.hx.template.CustomApplication;
import com.hx.template.R;
import com.hx.template.base.BaseActivity;
import com.hx.template.entity.User;
import com.hx.template.global.FastClickUtils;
import com.hx.template.model.impl.bmob.BmobUserImpl;
import com.hx.template.mvpview.impl.LoginMvpView;
import com.hx.template.presenter.PresenterFactory;
import com.hx.template.presenter.PresenterLoader;
import com.hx.template.presenter.impl.LoginPresenter;
import com.hx.template.utils.StringUtils;
import com.hx.template.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter, LoginMvpView> implements LoginMvpView {

    @Bind(R.id.username)
    EditText username;
    @Bind(R.id.password)
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("登录");
    }

    @Override
    public Loader<LoginPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader(this, new PresenterFactory<LoginPresenter>() {
            @Override
            public LoginPresenter create() {
                return new LoginPresenter(new BmobUserImpl());
            }
        });
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
                if (FastClickUtils.isTimeToProcess(R.id.forget_password)) {
                    startActivity(new Intent(LoginActivity.this, ResetPwdActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
                break;
            case R.id.login:
                if (checkInput()) {
                    if (FastClickUtils.isTimeToProcess(R.id.login)) {
                        showLoadingProgress("登录中...");
                        presenter.login();
                    }
                }
                break;
        }
    }

    private boolean checkInput() {
        if (TextUtils.isEmpty(getUserName())) {
            ToastUtils.showToast(this, "用户名不能为空");
            return false;
        }
        if (TextUtils.isEmpty(getPassword())) {
            ToastUtils.showToast(this, "密码不能为空");
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

    /**
     * 登录成功
     *
     * @param user 用户
     */
    @Override
    public void loginSuccess(User user) {
        hideLoadingProgress();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        CustomApplication.startSyncUserInfo();
        finish();
    }

    /**
     * 登录失败
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    @Override
    public void loginFail(String errorCode, String errorMsg) {
        hideLoadingProgress();
        ToastUtils.showToast(this, StringUtils.nullStrToEmpty(errorMsg));
    }

}
