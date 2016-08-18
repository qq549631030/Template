package com.hx.template.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.hx.template.R;
import com.hx.template.base.BaseStepActivity;
import com.hx.template.entity.User;
import com.hx.template.ui.fragment.BindEmailFragment;
import com.hx.template.ui.fragment.EmailStateFragment;

public class BindEmailActivity extends BaseStepActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        User currentUser = User.getCurrentUser(User.class);
        if (currentUser == null) {
            finish();
            return;
        }
        String email = currentUser.getEmail();
        if (!TextUtils.isEmpty(email)) {
            fragmentClass = EmailStateFragment.class;
        } else {
            fragmentClass = BindEmailFragment.class;
        }
        super.onCreate(savedInstanceState);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_bind_email;
    }
}
