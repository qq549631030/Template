package com.hx.template.imageloader.uil;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.hx.template.R;
import com.hx.template.imageloader.ImageLoader;
import com.hx.template.imageloader.ImageLoadingListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

/**
 * Created by huangx on 2016/8/10.
 */
public class UILImageLoader implements ImageLoader {

    public static DisplayImageOptions defaultOptions;

    @Override
    public void init(Context context) {
        defaultOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.default_image)
                .showImageOnLoading(R.drawable.default_image)
                .showImageOnFail(R.drawable.default_image)
                .cacheInMemory(true)//缓存到内存
                .cacheOnDisk(true)//缓存到SD卡
                .considerExifParams(true)
                .displayer(new SimpleBitmapDisplayer())
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoaderConfiguration config;
        config = new ImageLoaderConfiguration.Builder(context)
                .denyCacheImageMultipleSizesInMemory()
                // 使用1/8 (13%)APP内存
                .memoryCacheSizePercentage(13)
                .diskCacheFileNameGenerator(new CustomMd5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .imageDownloader(new CustomImageDownloader(context))
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config);
    }

    @Override
    public void displayImage(String uri, ImageView imageView) {
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(uri, imageView);
    }

    @Override
    public void displayImage(String uri, ImageView imageView, int imageResForEmptyUri, int imageResOnFail, int imageResOnLoading) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder().
                cloneFrom(defaultOptions);
        if (imageResForEmptyUri > 0) {
            builder.showImageForEmptyUri(imageResForEmptyUri);
        }
        if (imageResOnFail > 0) {
            builder.showImageOnFail(imageResOnFail);
        }
        if (imageResOnLoading > 0) {
            builder.showImageOnLoading(imageResOnLoading);
        }
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(uri, imageView, builder.build());
    }

    @Override
    public Bitmap loadImageSync(String uri) {
        return com.nostra13.universalimageloader.core.ImageLoader.getInstance().loadImageSync(uri);
    }
    
    @Override
    public void loadImageAsync(String uri, final ImageLoadingListener loadingListener) {
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().loadImage(uri, new com.nostra13.universalimageloader.core.listener.ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if (loadingListener != null) {
                    loadingListener.onLoadingStarted(imageUri, view);
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if (loadingListener != null) {
                    loadingListener.onLoadingFailed(imageUri, view, failReason.getType().name(), failReason.getCause().getMessage());
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (loadingListener != null) {
                    loadingListener.onLoadingComplete(imageUri, view, loadedImage);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                if (loadingListener != null) {
                    loadingListener.onLoadingCancelled(imageUri, view);
                }
            }
        });
    }
}
