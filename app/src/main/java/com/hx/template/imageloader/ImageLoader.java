package com.hx.template.imageloader;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by huangx on 2016/8/10.
 */
public interface ImageLoader {

    void init(Context context);
    
    void displayImage(String uri, ImageView imageView);

    void displayImage(String uri, ImageView imageView, int imageResForEmptyUri, int imageResOnFail, int imageResOnLoading);
    
}
