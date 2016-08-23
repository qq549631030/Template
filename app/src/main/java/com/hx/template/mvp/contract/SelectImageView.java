package com.hx.template.mvp.contract;

import android.content.Intent;
import android.net.Uri;

import com.hx.template.mvp.MvpView;

/**
 * Created by huangx on 2016/8/10.
 */
public interface SelectImageView extends MvpView {
    void startActivityForResultDelegate(Intent intent, int requestCode);

    void selectResult(Uri uri);
}
