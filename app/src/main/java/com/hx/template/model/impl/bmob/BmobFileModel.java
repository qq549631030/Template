package com.hx.template.model.impl.bmob;

import com.hx.template.model.FileModel;
import com.hx.template.model.FileUploadCallback;

import java.io.File;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by huangxiang on 16/8/14.
 */
public class BmobFileModel implements FileModel {
    /**
     * 上传文件
     *
     * @param file
     * @param callback
     */
    @Override
    public void uploadFile(File file, final FileUploadCallback callback) {
        BmobFile.uploadBatch(new String[]{file.getAbsolutePath()}, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> list, List<String> list1) {
                if (list.size() == 1) {
                    BmobCallBackDeliver.deliverSuccess(callback, list1.get(0));
                }
            }

            @Override
            public void onProgress(int i, int i1, int i2, int i3) {

            }

            @Override
            public void onError(int i, String s) {
                BmobCallBackDeliver.deliverFailure(callback, new BmobException(i, s));
            }
        });
    }

    /**
     * 上传字节数组
     *
     * @param data
     * @param callback
     */
    @Override
    public void uploadByteArray(byte[] data, FileUploadCallback callback) {

    }

    /**
     * 取消操作
     *
     * @param args
     * @return
     */
    @Override
    public boolean cancel(Object... args) {
        return false;
    }
}
