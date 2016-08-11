package com.hx.template.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by huangx on 2016/8/10.
 */
public interface ImageLoader {

    void init(Context context);

    void displayImage(String uri, ImageView imageView);

    void displayImage(String uri, ImageView imageView, int imageResForEmptyUri, int imageResOnFail, int imageResOnLoading);

    /**
     * 同步加载
     *
     * @param uri
     * @return
     */
    Bitmap loadImageSync(String uri);

    /**
     * 异步加载
     *
     * @param uri
     * @param loadingListener
     */
    void loadImageAsync(String uri, ImageLoadingListener loadingListener);
}
