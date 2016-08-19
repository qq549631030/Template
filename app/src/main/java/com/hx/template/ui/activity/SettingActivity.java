/*
 *  Copyright &amp;copy; 2014-2016  All rights reserved.
 *  Licensed under the 深圳中盟燧石科技 License, Version 1.0 (the "License");
 *
 */

package com.hx.template.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hx.template.CustomApplication;
import com.hx.template.R;
import com.hx.template.base.BaseActivity;
import com.hx.template.entity.User;
import com.hx.template.event.UserInfoUpdateEvent;
import com.hx.template.global.FastClickUtils;
import com.hx.template.global.GlobalActivityManager;
import com.hx.template.model.impl.bmob.BmobUserImpl;
import com.hx.template.utils.DataCleanManager;
import com.hx.template.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @Bind(R.id.cache_size)
    TextView cacheSize;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.bind_phone)
    TextView bindPhone;
    @Bind(R.id.bind_email)
    TextView bindEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("设置");
        EventBus.getDefault().register(this);
        refreshViews();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserInfoUpdateEvent event) {
        refreshViews();
    }

    @OnClick({R.id.clean_cache_layout, R.id.bind_phone_layout, R.id.bind_email_layout, R.id.logout, R.id.modify_pwd})
    public void onClick(View view) {
        if (!FastClickUtils.isTimeToProcess(view.getId())) {
            return;
        }
        switch (view.getId()) {
            case R.id.clean_cache_layout:
                new AlertDialog.Builder(SettingActivity.this).setMessage("确认要清除缓存吗?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cleanCache();
                        refreshViews();
                    }
                }).setNegativeButton("取消", null).show();
                break;
            case R.id.modify_pwd:
                startActivity(new Intent(SettingActivity.this, ModifyPwdActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.bind_phone_layout:
                startActivity(new Intent(SettingActivity.this, BindPhoneActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.bind_email_layout:
                startActivity(new Intent(SettingActivity.this, BindEmailActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.logout:
                new AlertDialog.Builder(SettingActivity.this).setMessage("确认要退出登录吗?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BmobUserImpl bmobUser = new BmobUserImpl();
                        bmobUser.logout();
                        CustomApplication.stopSyncUserInfo();
                        startActivity(new Intent(SettingActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        GlobalActivityManager.finishAll();
                    }
                }).setNegativeButton("取消", null).show();
                break;
        }
    }

    private void refreshViews() {
        try {
            long size = 0;
            File cacheInternal = getCacheDir();
            size += DataCleanManager.getFolderSize(cacheInternal);
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File cacheExternal = getExternalCacheDir();
                size += DataCleanManager.getFolderSize(cacheExternal);
            }
            String sizeString = DataCleanManager.getFormatSize(size);
            cacheSize.setText(StringUtils.nullStrToEmpty(sizeString));
        } catch (Exception e) {
            e.printStackTrace();
        }
        User currentUser = User.getCurrentUser(User.class);
        if (currentUser != null) {
            String mobile = currentUser.getMobilePhoneNumber();
            boolean mobileVerified = currentUser.getMobilePhoneNumberVerified() != null ? currentUser.getMobilePhoneNumberVerified().booleanValue() : false;
            if (!TextUtils.isEmpty(mobile)) {
                if (mobileVerified) {
                    bindPhone.setText(StringUtils.nullStrToEmpty(currentUser.getMobilePhoneNumber()) + "(已验证)");
                } else {
                    bindPhone.setText(StringUtils.nullStrToEmpty(currentUser.getMobilePhoneNumber()) + "(未验证)");
                }
            } else {
                bindPhone.setText("未绑定");
            }
            String emailAddr = currentUser.getEmail();
            boolean emailVerified = currentUser.getEmailVerified() != null ? currentUser.getEmailVerified().booleanValue() : false;
            if (!TextUtils.isEmpty(emailAddr)) {
                if (emailVerified) {
                    bindEmail.setText(StringUtils.nullStrToEmpty(emailAddr) + "(已验证)");
                } else {
                    bindEmail.setText(StringUtils.nullStrToEmpty(emailAddr) + "(未验证)");
                }
            } else {
                bindEmail.setText("未设置");
            }
        }
    }

    /**
     * 清除缓存
     */
    private void cleanCache() {
        File cacheInternal = getCacheDir();
        DataCleanManager.deleteFolderFile(cacheInternal.getAbsolutePath(), false);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File cacheExternal = getExternalCacheDir();
            if (cacheExternal.exists()) {
                DataCleanManager.deleteFolderFile(cacheExternal.getAbsolutePath(), false);
            }
        }
    }

}
