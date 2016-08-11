package com.hx.template.imageloader;

import android.graphics.Bitmap;
import android.view.View;

import com.nostra13.universalimageloader.core.assist.FailReason;


/**
 * Created by huangx on 2016/8/11.
 */
public interface ImageLoadingListener {
    void onLoadingStarted(String imageUri, View view);

    void onLoadingFailed(String imageUri, View view, String errorCode, String errorMsg);

    void onLoadingComplete(String imageUri, View view, Bitmap loadedImage);

    void onLoadingCancelled(String imageUri, View view);
}
