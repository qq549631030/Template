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
import android.widget.EditText;

import com.hx.template.R;
import com.hx.template.base.BaseActivity;
import com.hx.template.base.BaseMvpActivity;
import com.hx.template.dagger2.ComponentHolder;
import com.hx.template.global.FastClickUtils;
import com.hx.template.mvp.contract.RegisterContract;
import com.hx.template.mvp.presenter.RegisterPresenter;
import com.hx.template.mvp.Presenter;
import com.hx.template.mvp.PresenterFactory;
import com.hx.template.mvp.PresenterLoader;
import com.hx.template.utils.StringUtils;
import com.hx.template.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseMvpActivity<RegisterPresenter, RegisterContract.View> implements RegisterContract.View {

    @Bind(R.id.username)
    EditText username;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.confirm_password)
    EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("注册");
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

    @OnClick({R.id.register, R.id.to_login})
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
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 注册失败
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    @Override
    public void registerFail(String errorCode, String errorMsg) {
        ToastUtils.showToast(this, StringUtils.nullStrToEmpty(errorMsg));
    }
}
