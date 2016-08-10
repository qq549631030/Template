package com.hx.template.imageloader.picasso;

import android.content.Context;
import android.widget.ImageView;

import com.hx.template.imageloader.ImageLoader;
import com.squareup.picasso.Picasso;

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
}
