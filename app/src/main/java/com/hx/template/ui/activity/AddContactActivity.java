package com.hx.template.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hx.template.R;
import com.hx.template.base.BaseActivity;

public class AddContactActivity extends BaseActivity implements View.OnClickListener {

    private EditText editNote;

    private RelativeLayout contentAddContact;
    private RelativeLayout llUser;
    private ImageView avatar;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editNote = (EditText) findViewById(R.id.edit_note);
        contentAddContact = (RelativeLayout) findViewById(R.id.content_add_contact);
        llUser = (RelativeLayout) findViewById(R.id.ll_user);
        avatar = (ImageView) findViewById(R.id.avatar);
        name = (TextView) findViewById(R.id.name);
        findViewById(R.id.indicator).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.indicator:

                break;
        }
    }
}
