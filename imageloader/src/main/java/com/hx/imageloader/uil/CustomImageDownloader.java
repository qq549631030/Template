package com.hx.imageloader.uil;

import android.content.Context;

import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.IOException;
import java.io.InputStream;

/**
 * 自定义下载器
 * Created by huangx on 2016/8/10.
 */
public class CustomImageDownloader extends BaseImageDownloader {
    public CustomImageDownloader(Context context) {
        super(context);
    }

    public CustomImageDownloader(Context context, int connectTimeout, int readTimeout) {
        super(context, connectTimeout, readTimeout);
    }

    @Override
    protected InputStream getStreamFromNetwork(String imageUri, Object extra) throws IOException {
        InputStream inputStream = super.getStreamFromNetwork(imageUri, extra);
        // TODO: 2016/8/10 (do some thing , decrypt eg.) 
        return inputStream;
    }
}
