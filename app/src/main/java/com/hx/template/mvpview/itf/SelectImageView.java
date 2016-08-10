package com.hx.template.mvpview.itf;

import android.content.Intent;
import android.net.Uri;

import com.hx.template.mvpview.MvpView;

/**
 * Created by huangx on 2016/8/10.
 */
public interface SelectImageView extends MvpView {
    void startActivityForResultDelegate(Intent intent, int requestCode);

    void selectResult(Uri uri);
}
