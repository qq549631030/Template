package com.hx.template.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.hx.template.Constant;
import com.hx.template.CustomApplication;
import com.hx.template.R;
import com.hx.template.base.BaseActivity;
import com.hx.template.entity.User;
import com.hx.template.utils.SharedPreferencesUtil;

import java.io.File;
import java.io.IOException;

import cn.huangx.common.utils.AppUtils;
import cn.huangx.common.utils.FileUtils;
import cn.huangx.common.utils.PackageUtils;
//import com.networkbench.agent.impl.NBSAppAgent;

public class SplashActivity extends BaseActivity {
    private static final int GO_TO_GUIDE = 1;
    private static final int GO_TO_LOGIN = 2;
    private static final int GO_TO_HOME = 3;

    private boolean isFirst;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_TO_GUIDE:
                    Intent intentGuide = new Intent(SplashActivity.this, GuideActivity.class);
                    startActivity(intentGuide);
                    SplashActivity.this.finish();
                    break;

                case GO_TO_LOGIN:
                    Intent intentLogin = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intentLogin);
                    SplashActivity.this.finish();

                    break;
                case GO_TO_HOME:
                    Intent intentHome = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intentHome);
                    SplashActivity.this.finish();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        NBSAppAgent.setLicenseKey("5bdc5627a996487392abdc6349523f48").withLocationServiceEnabled(true).start(this.getApplicationContext());
        setContentView(R.layout.activity_splash);
        int lastLaunchVersion = (int) SharedPreferencesUtil.getParam(this, Constant.pref_lastLaunchVersion, 1);
        int currentVersion = PackageUtils.getAppVersionCode(this);
        isFirst = lastLaunchVersion < currentVersion;
        if (isFirst) {
            mHandler.sendEmptyMessageDelayed(GO_TO_GUIDE, 1500);
        } else {
            User currentUser = User.getCurrentUser(User.class);
            if (currentUser != null) {
                CustomApplication.reloadUserInfo();
                mHandler.sendEmptyMessageDelayed(GO_TO_HOME, 1500);
            } else {
                mHandler.sendEmptyMessageDelayed(GO_TO_LOGIN, 1500);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
