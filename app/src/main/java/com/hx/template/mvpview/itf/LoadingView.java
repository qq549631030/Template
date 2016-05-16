package com.hx.template.mvpview.itf;

/**
 * 显示加载进度的页面
 * Created by huangxiang on 16/5/9.
 */
public interface LoadingView {
    void showLoadingProgress(String msg);
    void hideLoadingProgress();
}
