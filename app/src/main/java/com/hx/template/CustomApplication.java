package com.hx.template;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.StrictMode;

//import com.squareup.leakcanary.LeakCanary;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.utils.L;

import java.io.File;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.GINGERBREAD;

/**
 * Created by huangxiang on 15/10/14.
 */
public class CustomApplication extends Application {

    private static CustomApplication instance;

    public static DisplayImageOptions defaultOptions;//默认图片配置
    public static DisplayImageOptions avatarOptions;//头像图片配置

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initImageLoader(instance);
        enabledStrictMode();
        //内存泄露检测(debug时候用就好)
//        LeakCanary.install(this);
    }

    private void enabledStrictMode() {
        if (SDK_INT >= GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                    .detectAll() //
                    .penaltyLog() //
                    .penaltyDeath() //
                    .build());
        }
    }

    public static void initImageLoader(Context context) {
        defaultOptions = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.default_image).showImageOnFail(R.drawable.default_image).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).displayer(new SimpleBitmapDisplayer()).bitmapConfig(Bitmap.Config.RGB_565).build();
        avatarOptions = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.default_avatar).showImageOnFail(R.drawable.default_avatar).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).displayer(new SimpleBitmapDisplayer()).bitmapConfig(Bitmap.Config.RGB_565).build();

        try {// 缓存目录
            ImageLoaderConfiguration config;
            config = new ImageLoaderConfiguration.Builder(context).threadPriority(Thread
                    .NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                    // 使用1/8 (13%)APP内存
                    .memoryCacheSizePercentage(13).diskCacheFileNameGenerator(new ImageNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
            ImageLoader.getInstance().init(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
        L.disableLogging();
    }

    private static class ImageNameGenerator extends Md5FileNameGenerator {

        @Override
        public String generate(String imageUri) {
            return super.generate(imageUri) + ".jpg";
        }

    }
}
