package com.hx.template.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.hx.template.R;
import com.hx.template.base.BaseStepActivity;
import com.hx.template.entity.User;
import com.hx.template.ui.fragment.BindPhoneFragment;
import com.hx.template.ui.fragment.VerifyPhoneFragment;

public class BindPhoneActivity extends BaseStepActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        User currentUser = User.getCurrentUser(User.class);
        if (currentUser == null) {
            finish();
            return;
        }
        boolean mobileVerified = currentUser.getMobilePhoneNumberVerified() != null ? currentUser.getMobilePhoneNumberVerified().booleanValue() : false;
        if (mobileVerified) {
            fragmentClass = VerifyPhoneFragment.class;
        } else {
            fragmentClass = BindPhoneFragment.class;
        }
        super.onCreate(savedInstanceState);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_bind_phone;
    }
}
