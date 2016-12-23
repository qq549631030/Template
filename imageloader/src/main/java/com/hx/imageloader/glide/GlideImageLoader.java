package com.hx.imageloader.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hx.imageloader.ImageLoader;
import com.hx.imageloader.ImageLoadingListener;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/23 16:39
 * 邮箱：huangx@pycredit.cn
 */

public class GlideImageLoader implements ImageLoader {

    private Context context;

    @Override
    public void init(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public void displayImage(String uri, ImageView imageView) {
        Glide.with(context).load(uri).into(imageView);
    }

    @Override
    public void displayImage(String uri, ImageView imageView, int imageResForEmptyUri, int imageResOnFail, int imageResOnLoading) {
        displayImage(uri, imageView, imageResForEmptyUri, imageResOnFail, imageResOnLoading, null);
    }

    @Override
    public void displayImage(final String uri, ImageView imageView, int imageResForEmptyUri, int imageResOnFail, int imageResOnLoading, final ImageLoadingListener loadingListener) {
        Glide.with(context).load(uri).placeholder(imageResOnLoading).error(imageResOnFail).into(new ImageViewTarget<GlideDrawable>(imageView) {
            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
                if (loadingListener != null) {
                    loadingListener.onLoadingStarted(uri, view);
                }
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                if (loadingListener != null) {
                    loadingListener.onLoadingFailed(uri, view, "-1", e.getMessage());
                }
            }

            @Override
            public void onLoadCleared(Drawable placeholder) {
                super.onLoadCleared(placeholder);
                if (loadingListener != null) {
                    loadingListener.onLoadingCancelled(uri, view);
                }
            }

            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                super.onResourceReady(resource, glideAnimation);
                if (loadingListener != null) {
                    loadingListener.onLoadingComplete(uri, view, resource);
                }
            }

            @Override
            protected void setResource(GlideDrawable resource) {
                view.setImageDrawable(resource);
            }
        });
    }

    /**
     * 同步加载
     *
     * @param uri
     * @return
     */
    @Override
    public Bitmap loadImageSync(String uri) {
        return null;
    }

    /**
     * 异步加载
     *
     * @param uri
     * @param loadingListener
     */
    @Override
    public void loadImageAsync(final String uri, final ImageLoadingListener loadingListener) {
        Glide.with(context).load(uri).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                if (loadingListener != null) {
                    loadingListener.onLoadingComplete(uri, null, resource);
                }
            }
        });
    }
}
