/*
 *  Copyright &amp;copy; 2014-2016  All rights reserved.
 *  Licensed under the 深圳中盟燧石科技 License, Version 1.0 (the "License");
 *
 */

package com.hx.template.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.hx.template.base.BaseActivity;
import com.hx.template.Constant;
import com.hx.template.CustomApplication;
import com.hx.template.R;
import com.hx.template.base.BaseDialog;
import com.hx.template.base.BaseDialogConfig;
import com.hx.template.base.BaseDialogConfigFactory;
import com.hx.template.demo.DemoMainActivity;
import com.hx.template.entity.User;
import com.hx.template.global.FastClickUtils;
import com.hx.template.model.impl.bmob.BmobUserImpl;
import com.hx.template.presenter.impl.LoginPresenter;
import com.hx.template.utils.ToastUtils;
import com.hx.template.mvpview.impl.LoginMvpView;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginMvpView {

    @Bind(R.id.username)
    EditText username;
    @Bind(R.id.password)
    EditText password;

    ProgressDialog mProgressDialog;

    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("登录");
        mProgressDialog = new ProgressDialog(this);
        presenter = new LoginPresenter(new BmobUserImpl());
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
                if (FastClickUtils.isTimeToProcess(R.id.forget_password)) {
                    //TODO implement
                }
                break;
            case R.id.login:
                if (checkInput()) {
                    if (FastClickUtils.isTimeToProcess(R.id.login)) {
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
        presenter.detachView();
        super.onDestroy();
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
