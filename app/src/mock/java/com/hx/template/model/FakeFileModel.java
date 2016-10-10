package com.hx.template.model;

import android.net.Uri;
import android.os.Handler;

import java.io.File;

/**
 * Created by huangx on 2016/8/23.
 */
public class FakeFileModel implements FileModel {

    Handler handler;

    public FakeFileModel() {
        handler = new Handler();
    }

    /**
     * 上传文件
     *
     * @param file
     * @param callback
     */
    @Override
    public void uploadFile(final File file, final FileUploadCallback callback) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(TaskManager.TASK_ID_UPLOAD_FILE, Uri.fromFile(file).toString());
            }
        }, 2000);
    }

    @Override
    public boolean cancel(Object... args) {
        handler.removeCallbacksAndMessages(null);
        return true;
    }
}
