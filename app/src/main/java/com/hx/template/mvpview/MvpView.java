package com.hx.template.mvpview;

/**
 * Created by huangx on 2016/5/9.
 */
public interface MvpView {
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

    /**
     * 显示错误信息
     *
     * @param errorMsg
     */
    void showError(String errorMsg);
}
