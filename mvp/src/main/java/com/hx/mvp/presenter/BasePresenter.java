package com.hx.mvp.presenter;

import com.hx.mvp.view.BaseMvpView;
import com.hx.mvp.view.ViewState;

import java.lang.ref.WeakReference;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/2 16:05
 * 邮箱：549631030@qq.com
 */

public abstract class BasePresenter<V extends BaseMvpView> implements Presenter<V> {

    private WeakReference<V> viewRef;

    private ViewState viewState;
    /**
     * View 附着到窗口
     *
     * @param mvpView
     */
    @Override
    public void attachView(V mvpView) {
        viewRef = new WeakReference<V>(mvpView);
        if (viewState != null) {
            viewState.apply(mvpView);
        }
    }

    /**
     * View 从窗口脱离
     */
    @Override
    public void detachView() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    public ViewState getViewState() {
        return viewState;
    }

    public void setViewState(ViewState viewState) {
        this.viewState = viewState;
    }

    /**
     *
     * @return
     */
    public boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    public V getMvpView() {
        return viewRef == null ? null : viewRef.get();
    }

    @Override
    public boolean cancel(Object... args) {
        return true;
    }
}
