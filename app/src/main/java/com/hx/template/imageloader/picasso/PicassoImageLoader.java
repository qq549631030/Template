package com.hx.template.imageloader.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.hx.template.imageloader.ImageLoader;
import com.hx.template.imageloader.ImageLoadingListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

/**
 * Created by huangx on 2016/8/10.
 */
public class PicassoImageLoader implements ImageLoader {

    private Context context;

    @Override
    public void init(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public void displayImage(String uri, ImageView imageView) {
        Picasso.with(context).load(uri).into(imageView);
    }

    @Override
    public void displayImage(String uri, ImageView imageView, int imageResForEmptyUri, int imageResOnFail, int imageResOnLoading) {
        Picasso.with(context).load(uri).placeholder(imageResOnLoading).error(imageResOnFail).into(imageView);
    }

    @Override
    public Bitmap loadImageSync(String uri) {
        try {
            return Picasso.with(context).load(uri).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void loadImageAsync(final String uri, final ImageLoadingListener loadingListener) {
        Picasso.with(context).load(uri).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                if (loadingListener != null) {
                    loadingListener.onLoadingComplete(uri, null, bitmap);
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                if (loadingListener != null) {
                    loadingListener.onLoadingFailed(uri, null, null, null);
                }
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (loadingListener != null) {
                    loadingListener.onLoadingStarted(uri, null);
                }
            }
        });
    }
}
