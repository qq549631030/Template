/*
 *  Copyright &amp;copy; 2014-2016  All rights reserved.
 *  Licensed under the 深圳中盟燧石科技 License, Version 1.0 (the "License");
 *
 */

package com.hx.template.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.hx.template.BaseActivity;
import com.hx.template.R;
import com.hx.template.utils.DataCleanManager;
import com.hx.template.utils.StringUtils;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @Bind(R.id.cache_size)
    TextView cacheSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        refreshViews();
    }

    @OnClick({R.id.clean_cache_layout})
    public void onClick(View view) {
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
    }

    private void cleanCache() {
        File cacheInternal = getCacheDir();
        DataCleanManager.deleteFolderFile(cacheInternal.getAbsolutePath(),false);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File cacheExternal = getExternalCacheDir();
            if (cacheExternal.exists()) {
                DataCleanManager.deleteFolderFile(cacheExternal.getAbsolutePath(),false);
            }
        }
    }
}
