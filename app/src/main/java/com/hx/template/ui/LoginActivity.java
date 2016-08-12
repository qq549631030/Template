/*
 *  Copyright &amp;copy; 2014-2016  All rights reserved.
 *  Licensed under the 深圳中盟燧石科技 License, Version 1.0 (the "License");
 *
 */

package com.hx.template.ui;

import android.app.Dialog;
import android.content.DialogInterface;
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
import com.hx.template.base.BaseDialog;
import com.hx.template.base.BaseDialogConfig;
import com.hx.template.base.BaseDialogConfigFactory;
import com.hx.template.demo.DemoMainActivity;
import com.hx.template.entity.User;
import com.hx.template.global.FastClickUtils;
import com.hx.template.model.impl.retrofit.RetrofitUserImpl;
import com.hx.template.presenter.impl.UserPresenter;
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
    UserPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presenter = new UserPresenter(new RetrofitUserImpl());
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
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showLoadingProgress(String msg) {
        BaseDialogConfig dialogConfig = BaseDialogConfigFactory.getDialogConfig(BaseDialogConfigFactory.BaseDialogType.BASE_DIALOG_LOADING, this, msg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    hideLoadingProgress();
            }
        });
        Dialog dialog = BaseDialog.getDialog(this,dialogConfig);
        dialog.show();
    }

    @Override
    public void hideLoadingProgress() {
        BaseDialog.dismissDialog(this);
    }
}
