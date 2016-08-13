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
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hx.template.Constant;
import com.hx.template.CustomApplication;
import com.hx.template.R;
import com.hx.template.base.BaseActivity;
import com.hx.template.demo.DemoMainActivity;
import com.hx.template.entity.User;
import com.hx.template.global.FastClickUtils;
import com.hx.template.model.impl.bmob.BmobUserImpl;
import com.hx.template.mvpview.impl.RegisterMvpView;
import com.hx.template.presenter.impl.RegisterPresenter;
import com.hx.template.utils.ToastUtils;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements RegisterMvpView {

    @Bind(R.id.username)
    EditText username;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.confirm_password)
    EditText confirmPassword;
    @Bind(R.id.register)
    TextView register;
    @Bind(R.id.to_login)
    TextView toLogin;

    ProgressDialog mProgressDialog;

    RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("注册");
        mProgressDialog = new ProgressDialog(this);
        presenter = new RegisterPresenter(new BmobUserImpl());
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @OnClick({R.id.register, R.id.to_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                if (checkInput()) {
                    if (FastClickUtils.isTimeToProcess(R.id.register)) {
                        presenter.register();
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

    private boolean checkInput() {
        if (TextUtils.isEmpty(getUserName())) {
            ToastUtils.showToast(this, "用户名不能为空");
            return false;
        }
        if (TextUtils.isEmpty(getPassword())) {
            ToastUtils.showToast(this, "密码不能为空");
            return false;
        }
        if (getUserName().equals(confirmPassword.getText().toString().trim())) {
            ToastUtils.showToast(this, "两次输入的密码不一致");
            return false;
        }
        return true;
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
     * 跳转到主页
     *
     * @param user
     */
    @Override
    public void toMainActivity(User user) {
        CustomApplication.saveLoginInfo(user, getPassword());
        Intent intent = new Intent(RegisterActivity.this, DemoMainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 显示错误信息
     *
     * @param reason
     */
    @Override
    public void showFailedError(String reason) {
        ToastUtils.showToast(getApplicationContext(), "注册失败：" + reason);
    }

    @Override
    public void showLoadingProgress(String msg) {
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    @Override
    public void hideLoadingProgress() {
        mProgressDialog.dismiss();
    }
}
