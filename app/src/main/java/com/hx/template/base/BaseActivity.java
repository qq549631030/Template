package com.hx.template.base;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.hx.template.global.GlobalActivityManager;
import com.hx.template.global.HXLog;
import com.hx.template.global.SaveSceneUtils;
import com.hx.template.mvp.MvpView;
import com.hx.template.mvp.Presenter;
import com.hx.template.mvp.PresenterFactory;
import com.hx.template.mvp.PresenterLoader;
import com.hx.template.utils.ToastUtils;

public class BaseActivity<P extends Presenter<V>, V extends MvpView> extends AppCompatActivity implements MvpView, LoaderManager.LoaderCallbacks<P> {

    public final static int BASE_ACTIVITY_LOADER_ID = 100;

    private ProgressDialog mProgressDialog;

    private ConnectivityManager mConnectivityManager;

    protected P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalActivityManager.push(this);
        mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        IntentFilter netFilter = new IntentFilter();
        netFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netReceiver, netFilter);
        SaveSceneUtils.onRestoreInstanceState(this, savedInstanceState);
        mProgressDialog = new ProgressDialog(this);
        getSupportLoaderManager().initLoader(BASE_ACTIVITY_LOADER_ID, null, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.attachView((V) this);
        }
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detachView();
        }
        super.onDestroy();
        unregisterReceiver(netReceiver);
        netReceiver = null;
        mConnectivityManager = null;
        GlobalActivityManager.remove(this);
    }

    public void finishForever() {
        super.finish();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        SaveSceneUtils.onSaveInstanceState(this, outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        SaveSceneUtils.onRestoreInstanceState(this, savedInstanceState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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

    /**
     * 显示loading对话框
     *
     * @param msg
     */
    @Override
    public void showLoadingProgress(String msg) {
        mProgressDialog.setMessage(msg);
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    /**
     * 隐藏loading对话框
     */
    @Override
    public void hideLoadingProgress() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public Loader<P> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader(this, new PresenterFactory<P>() {
            @Override
            public P create() {
                return onCreatePresenter();
            }
        });
    }

    @Override
    public void onLoadFinished(Loader<P> loader, P data) {
        HXLog.d("onLoadFinished");
        presenter = data;
    }

    @Override
    public void onLoaderReset(Loader<P> loader) {
        HXLog.d("onLoaderReset");
        presenter = null;
    }

    @Override
    public void showError(String errorMsg) {
        ToastUtils.showToast(this, errorMsg);
    }

    protected P onCreatePresenter() {
        return null;
    }
}
