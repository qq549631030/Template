/*
 *  Copyright &amp;copy; 2014-2016  All rights reserved.
 *  Licensed under the 深圳中盟燧石科技 License, Version 1.0 (the "License");
 *
 */

package com.hx.template.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.hx.template.R;
import com.hx.template.base.BaseActivity;
import com.hx.template.dagger2.ComponentHolder;
import com.hx.template.domain.usercase.single.user.LoginCase;
import com.hx.template.entity.User;
import com.hx.template.global.FastClickUtils;
import com.hx.template.global.GsonUtils;
import com.hx.template.model.ModelManager;
import com.hx.template.mvp.contract.LoginContract;
import com.hx.template.mvp.presenter.LoginPresenter;
import com.hx.template.utils.StringUtils;
import com.hx.template.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter, LoginContract.View> implements LoginContract.View {

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
    protected LoginPresenter onCreatePresenter() {
        return ComponentHolder.getAppComponent().loginPresenter();
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
        if (!FastClickUtils.isTimeToProcess(view.getId())) {
            return;
        }
        switch (view.getId()) {
            case R.id.forget_password:
                startActivity(new Intent(LoginActivity.this, ResetPwdActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.login:
                presenter.login();
                break;
        }
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
        User.setCurrent(GsonUtils.toJsonObj(user));
//        Realm realm = Realm.getDefaultInstance();
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.createObject(User.class);
//            }
//        });
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
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
        ToastUtils.showToast(this, StringUtils.nullStrToEmpty(errorMsg));
    }

}
