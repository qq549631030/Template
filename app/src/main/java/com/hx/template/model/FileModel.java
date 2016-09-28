package com.hx.template.model;

import java.io.File;

/**
 * Created by huangxiang on 16/8/14.
 */
public interface FileModel extends BaseModel{
    /**
     * 上传文件
     *
     * @param file
     * @param callback
     */
    void uploadFile(File file, FileUploadCallback callback);
}
