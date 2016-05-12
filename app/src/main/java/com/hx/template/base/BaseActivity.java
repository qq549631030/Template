package com.hx.template.base;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.hx.template.CustomApplication;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    public ProgressDialog mProgressDialog;

    private ConnectivityManager mConnectivityManager;

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
        mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        IntentFilter netFilter = new IntentFilter();
        netFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netReceiver, netFilter);
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
        unregisterReceiver(netReceiver);
        mProgressDialog = null;
        netReceiver = null;
        mConnectivityManager = null;
    }


    /**
     * 网络变化监听
     */
    private BroadcastReceiver netReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isAvailable()) {
                    /////////////网络连接
                    onNetworkChange(true);
                    String name = netInfo.getTypeName();
                    if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        /////WiFi网络

                    } else if (netInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {
                        /////有线网络

                    } else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                        /////////移动网络

                    }
                } else {
                    ////////网络断开
                    onNetworkChange(false);
                }
            }
        }
    };

    /**
     * 网络变化
     *
     * @param on
     */
    protected void onNetworkChange(boolean on) {

    }


}
