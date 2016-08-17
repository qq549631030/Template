package com.hx.template.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.hx.template.R;
import com.hx.template.base.BaseStepActivity;
import com.hx.template.entity.User;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BindEmailActivity extends BaseStepActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User currentUser = User.getCurrentUser(User.class);
        if (currentUser == null) {
            finish();
            return;
        }
        boolean emailVerified = currentUser.getEmail() != null ? currentUser.getEmailVerified().booleanValue() : false;

        setContentView(R.layout.activity_bind_email);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
