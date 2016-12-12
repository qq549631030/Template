package com.hx.mvp.view;

/**
 * 功能说明：MVP中的View
 * 作者：huangx on 2016/12/2 15:17
 * 邮箱：huangx@pycredit.cn
 */

public interface BaseMvpView {
    /**
     * 显示/隐藏加载中动画
     *
     * @param show true 显示 false 隐藏
     * @param args 额外参数
     */
    void showLoadingProgress(boolean show, Object... args);

    /**
     * 通用错误
     *
     * @param errorData 错误数据
     */
    void showError(Object... errorData);
}
