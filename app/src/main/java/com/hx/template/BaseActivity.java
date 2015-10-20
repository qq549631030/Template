package com.hx.template;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    public ProgressDialog mProgressDialog;
    //是否记录当前Activity
    private boolean addToList = true;

    public void setAddToList(boolean addToList) {
        this.addToList = addToList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(this);
        if (addToList) {
            //记录当前Activity
            CustomApplication.addActivity(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消当前Activity记录
        CustomApplication.removeActivity(this);
    }
}
