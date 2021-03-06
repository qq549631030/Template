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

import com.hx.easemob.DefaultSDKHelper;
import com.hx.easemob.db.DemoDBManager;
import com.hx.mvp.view.ViewState;
import com.hx.template.CustomApplication;
import com.hx.template.CustomSDKHelper;
import com.hx.template.R;
import com.hx.template.base.BaseMvpActivity;
import com.hx.template.dagger2.ComponentHolder;
import com.hx.template.entity.User;
import com.hx.template.global.FastClickUtils;
import com.hx.template.global.GsonUtils;
import com.hx.template.mvp.contract.LoginContract;
import com.hx.template.mvp.presenter.LoginPresenter;
import com.hx.template.utils.StringUtils;
import com.hx.template.utils.ToastUtils;
import com.hyphenate.chat.EMClient;

public class LoginActivity extends BaseMvpActivity<LoginPresenter, LoginContract.View> implements LoginContract.View, View.OnClickListener {

    EditText username;

    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.forget_password).setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("登录");
    }

    @Override
    protected LoginPresenter onCreatePresenter() {
        return ComponentHolder.getAppComponent().loginPresenter();
    }

    @Override
    protected ViewState<LoginContract.View> onCreateViewState() {
        return new ViewState<LoginContract.View>() {
            private String username;
            private String password;

            @Override
            public void apply(LoginContract.View view) {
                view.setUserName(username);
                view.setPassword(password);
            }

            @Override
            public void save(LoginContract.View view) {
                username = view.getUserName();
                password = view.getPassword();
            }
        };
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

    @Override
    public void onClick(View view) {
        if (!FastClickUtils.isTimeToProcess(view.getId())) {
            return;
        }
        switch (view.getId()) {
            case R.id.forget_password:
                startActivity(new Intent(LoginActivity.this, ResetPwdActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.login:
                // After logout，the DemoDB may still be accessed due to async callback, so the DemoDB will be re-opened again.
                // close it before login to make sure DemoDB not overlap
                DemoDBManager.getInstance().closeDB();
                // reset current user name before login
                CustomSDKHelper.getInstance().setCurrentUserName(getUserName());
                presenter.login();
                break;
        }
    }

    @Override
    public String getUserName() {
        return username.getText().toString().trim();
    }

    @Override
    public void setUserName(String userName) {
        this.username.setText(userName);
    }

    @Override
    public String getPassword() {
        return password.getText().toString().trim();
    }

    @Override
    public void setPassword(String password) {
        this.password.setText(password);
    }

    /**
     * 登录成功
     *
     * @param user 用户
     */
    @Override
    public void loginSuccess(final User user) {

        User.setCurrent(GsonUtils.toJsonObj(user));
//        Realm realm = Realm.getDefaultInstance();
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.createObject(User.class);
//            }
//        });
        ((CustomSDKHelper) CustomSDKHelper.getInstance()).getUserProfileManager().setCurrentUserNick(user.getNickname());
        ((CustomSDKHelper) CustomSDKHelper.getInstance()).getUserProfileManager().setCurrentUserAvatar(user.getAvatar());
        new Thread(new Runnable() {
            @Override
            public void run() {
                EMClient.getInstance().updateCurrentUserNick(user.getNickname());
            }
        }).start();
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
    public void loginFail(String errorCode, final String errorMsg) {
        ToastUtils.showToast(CustomApplication.getInstance(), StringUtils.nullStrToEmpty(errorMsg));
    }
}
