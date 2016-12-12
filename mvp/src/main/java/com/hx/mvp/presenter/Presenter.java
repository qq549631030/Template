package com.hx.mvp.presenter;

import com.hx.mvp.Cancelable;
import com.hx.mvp.view.BaseMvpView;

/**
 * 功能说明：MVP中的Presenter
 * 作者：huangx on 2016/12/2 16:02
 * 邮箱：huangx@pycredit.cn
 */

public interface Presenter<V extends BaseMvpView> extends Cancelable {
    /**
     * View 附着到窗口
     *
     * @param mvpView
     */
    void attachView(V mvpView);

    /**
     * View 从窗口脱离
     */
    void detachView();
}
