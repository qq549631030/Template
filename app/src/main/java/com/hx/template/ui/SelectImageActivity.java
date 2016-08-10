package com.hx.template.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.hx.template.R;
import com.hx.template.base.BaseActivity;
import com.hx.template.mvpview.itf.SelectImageView;
import com.hx.template.presenter.impl.SelectImagePresenter;

public class SelectImageActivity extends BaseActivity implements SelectImageView {

    SelectImagePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);
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

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (presenter.isViewAttached()) {
            presenter.onActivityResultDelegate(requestCode, resultCode, data);
        }
    }
}
