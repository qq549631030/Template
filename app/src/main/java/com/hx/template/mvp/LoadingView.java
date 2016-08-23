package com.hx.template.mvp;

/**
 * 显示加载进度的页面
 * Created by huangxiang on 16/5/9.
 */
public interface LoadingView {
    /**
     * 显示loading对话框
     *
     * @param msg
     */
    void showLoadingProgress(String msg);

    /**
     * 隐藏loading对话框
     */
    void hideLoadingProgress();
}
