package com.hx.imageloader;

import android.graphics.Bitmap;
import android.view.View;


/**
 * Created by huangx on 2016/8/11.
 */
public interface ImageLoadingListener<T> {
    void onLoadingStarted(String imageUri, View view);

    void onLoadingFailed(String imageUri, View view, String errorCode, String errorMsg);

    void onLoadingComplete(String imageUri, View view, T loadedImage);

    void onLoadingCancelled(String imageUri, View view);
}
