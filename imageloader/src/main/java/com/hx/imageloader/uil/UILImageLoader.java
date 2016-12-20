package com.hx.imageloader.uil;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.hx.imageloader.BuildConfig;
import com.hx.imageloader.ImageLoader;
import com.hx.imageloader.ImageLoadingListener;
import com.hx.imageloader.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.utils.L;

/**
 * Created by huangx on 2016/8/10.
 */
public class UILImageLoader implements ImageLoader {

    public static DisplayImageOptions defaultOptions;

    @Override
    public void init(Context context) {
        defaultOptions = new DisplayImageOptions.Builder()
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
        if (BuildConfig.DEBUG) {
            L.writeDebugLogs(true);
            L.writeLogs(true);
        } else {
            L.writeDebugLogs(false);
            L.writeLogs(false);
        }
    }

    @Override
    public void displayImage(String uri, ImageView imageView) {
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(uri, imageView);
    }

    @Override
    public void displayImage(String uri, ImageView imageView, int imageResForEmptyUri, int imageResOnFail, int imageResOnLoading) {
        displayImage(uri, imageView, imageResForEmptyUri, imageResOnFail, imageResOnLoading, null);
    }

    @Override
    public void displayImage(String uri, ImageView imageView, int imageResForEmptyUri, int imageResOnFail, int imageResOnLoading, final ImageLoadingListener loadingListener) {
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

        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(uri, imageView, builder.build(), loadingListener == null ? null : new com.nostra13.universalimageloader.core.listener.ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                loadingListener.onLoadingStarted(imageUri, view);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                loadingListener.onLoadingFailed(imageUri, view, failReason.getType().name(), failReason.getCause().getMessage());
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                loadingListener.onLoadingComplete(imageUri, view, loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                loadingListener.onLoadingCancelled(imageUri, view);
            }
        });
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
