package com.hx.template.demo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import com.hx.template.R;
import com.hx.template.base.BaseMvpActivity;
import com.hx.template.global.HXLog;
import com.hx.template.mvp.contract.SelectImageView;
import com.hx.template.mvp.presenter.SelectImagePresenter;

import java.io.File;


public class SelectImageActivity extends BaseMvpActivity implements SelectImageView, View.OnClickListener {

    SelectImagePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        presenter = new SelectImagePresenter();
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void startActivityForResultDelegate(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    public void takeFromCamera(String outPath) {
        presenter.takeFromCamera(outPath);
    }

    public void takeFromGallery() {
        presenter.takeFromGallery();
    }


    @Override
    public void selectResult(Uri uri) {
        HXLog.e("uri = " + uri.toString());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (presenter.isViewAttached()) {
            presenter.onActivityResultDelegate(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                String path = getExternalCacheDir().getAbsolutePath() + File.separator + SystemClock.currentThreadTimeMillis() + ".png";
                takeFromCamera(path);
                break;
            case R.id.button2:
                takeFromGallery();
                break;
        }
    }
}
