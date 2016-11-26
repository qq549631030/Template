package com.hx.template.model;

import java.io.File;

/**
 * Created by huangxiang on 16/8/14.
 */
public interface FileModel extends BaseModel {
    /**
     * 上传文件
     *
     * @param file
     * @param callback
     */
    void uploadFile(File file, FileUploadCallback callback);

    /**
     * 上传字节数组
     *
     * @param data
     * @param callback
     */
    void uploadByteArray(byte[] data, FileUploadCallback callback);
}
