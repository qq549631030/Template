package com.hx.template.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.hx.template.base.BaseActivity;
import com.hx.template.Constant;
import com.hx.template.CustomApplication;
import com.hx.template.demo.DemoMainActivity;
import com.hx.template.R;
import com.hx.template.entity.User;
import com.hx.template.model.LoginModel;
import com.hx.template.model.impl.volley.LoginModelImpl;
import com.hx.template.utils.SecretUtils;
import com.hx.template.utils.SharedPreferencesUtil;
import com.hx.template.utils.ToastUtils;
import com.networkbench.agent.impl.NBSAppAgent;

public class SplashActivity extends BaseActivity {
    private static final int GO_TO_GUIDE = 1;
    private static final int GO_TO_LOGIN = 2;
    private static final int GO_TO_HOME = 3;

    private boolean isFirst;
    private boolean autoLogin;
    private String userName;
    private String password;
   private LoginModel.Model loginModel;

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
                    Intent intentHome = new Intent(SplashActivity.this, DemoMainActivity.class);
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
        NBSAppAgent.setLicenseKey("5bdc5627a996487392abdc6349523f48").withLocationServiceEnabled(true).start(this.getApplicationContext());
        setContentView(R.layout.activity_splash);
        loginModel = new LoginModelImpl();
        initData();
        if (isFirst) {
            mHandler.sendEmptyMessageDelayed(GO_TO_GUIDE, 1500);
        } else {
            if (autoLogin) {
                if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
                    login(userName, password);
                } else {
                    mHandler.sendEmptyMessageDelayed(GO_TO_LOGIN, 1500);
                }
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

    protected void initData() {
        isFirst = (Boolean) SharedPreferencesUtil.getParam(this, Constant.pref_isFirst, true);
        autoLogin = (Boolean) SharedPreferencesUtil.getParam(this, Constant.pref_autoLogin, false);
        userName = (String) SharedPreferencesUtil.getParam(this, Constant.pref_userName, "");

        String encryPwd = (String) SharedPreferencesUtil.getParam(this, Constant.pref_password, "");
        if (!TextUtils.isEmpty(encryPwd)) {
            try {
                password = SecretUtils.decrypt(Constant.SECRET_KEY, encryPwd.trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void login(String userName, final String password) {
        loginModel.login(userName, password, new LoginModel.OnLoginListener() {
            @Override
            public void loginSuccess(User user) {
                CustomApplication.saveLoginInfo(user,password);
                mHandler.sendEmptyMessageDelayed(GO_TO_HOME, 1500);
            }

            @Override
            public void loginFailed(String reason) {
                ToastUtils.showToast(getApplicationContext(), reason);
                mHandler.sendEmptyMessageDelayed(GO_TO_LOGIN, 1500);
            }
        });
    }
}
