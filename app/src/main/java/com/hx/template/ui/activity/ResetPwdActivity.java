package com.hx.template.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.hx.template.R;
import com.hx.template.base.BaseStepActivity;
import com.hx.template.ui.fragment.ResetPwdByPhoneFragment;

/**
 * 重置密码
 */
public class ResetPwdActivity extends BaseStepActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fragmentClass = ResetPwdByPhoneFragment.class;
        super.onCreate(savedInstanceState);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.reset_pwd_title));
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_bind_phone;
    }
}
