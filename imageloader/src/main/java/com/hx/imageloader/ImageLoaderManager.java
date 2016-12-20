package com.hx.imageloader;

import android.content.Context;

import com.hx.imageloader.uil.UILImageLoader;


/**
 * Created by huangx on 2016/8/10.
 */
public class ImageLoaderManager {
    private static ImageLoader imageLoader;

    public static ImageLoader getImageLoader(Context context) {
        if (imageLoader == null) {
            synchronized (ImageLoaderManager.class) {
                if (imageLoader == null) {
                    imageLoader = new UILImageLoader();
//                    imageLoader = new PicassoImageLoader();
                    imageLoader.init(context);
                }
            }
        }
        return imageLoader;
    }
}
