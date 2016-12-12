package com.hx.template.model;

import com.hx.mvp.Callback;

/**
 * 功能说明：com.hx.template.model
 * 作者：huangx on 2016/8/30 11:24
 * 邮箱：huangx@pycredit.cn
 */
public interface FileUploadCallback extends Callback {
    public void onProgress(float progress);
}
