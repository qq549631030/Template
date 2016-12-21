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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hx.easemob.DefaultSDKHelper;
import com.hx.mvp.presenter.Presenter;
import com.hx.mvp.presenter.PresenterFactory;
import com.hx.mvp.presenter.PresenterLoader;
import com.hx.template.CustomApplication;
import com.hx.template.R;
import com.hx.template.base.BaseMvpActivity;
import com.hx.template.dagger2.ComponentHolder;
import com.hx.template.global.FastClickUtils;
import com.hx.template.mvp.contract.RegisterContract;
import com.hx.template.mvp.presenter.RegisterPresenter;
import com.hx.template.utils.StringUtils;
import com.hx.template.utils.ToastUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

public class RegisterActivity extends BaseMvpActivity<RegisterPresenter, RegisterContract.View> implements RegisterContract.View, View.OnClickListener {

    EditText username;

    EditText password;

    EditText confirmPassword;
    private Button register;
    private TextView toLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.register_title);
    }

    @Override
    public Loader<RegisterPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(this, new PresenterFactory() {
            @Override
            public Presenter create() {
                return ComponentHolder.getAppComponent().registerPresenter();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (!FastClickUtils.isTimeToProcess(view.getId())) {
            return;
        }
        switch (view.getId()) {
            case R.id.register:
                presenter.register();
                break;
            case R.id.to_login:
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }

    /**
     * 获取用户名
     *
     * @return
     */
    @Override
    public String getUserName() {
        return username.getText().toString().trim();
    }

    /**
     * 获取密码
     *
     * @return
     */
    @Override
    public String getPassword() {
        return password.getText().toString().trim();
    }

    /**
     * 获取再次输入的密码
     *
     * @return
     */
    @Override
    public String getConfirmPassword() {
        return confirmPassword.getText().toString().trim();
    }

    /**
     * 注册成功
     */
    @Override
    public void registerSuccess() {
//        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//        startActivity(intent);
        DefaultSDKHelper.getInstance().setCurrentUserName(getUserName());
        finish();
    }

    /**
     * 注册失败
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    @Override
    public void registerFail(String errorCode, final String errorMsg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToast(CustomApplication.getInstance(), StringUtils.nullStrToEmpty(errorMsg));
            }
        });
    }

    private void initView() {
        register = (Button) findViewById(R.id.register);
        toLogin = (TextView) findViewById(R.id.to_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);
        register.setOnClickListener(this);
        toLogin.setOnClickListener(this);
    }
}
