package com.hx.template.mvp;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

/**
 * Created by huangx on 2016/5/9.
 */
public class BasePresenter<T extends MvpView> implements Presenter<T> {

    private WeakReference<T> viewRef;

    private ViewState viewState;

    @Override
    public void attachView(@NonNull T mvpView) {
        viewRef = new WeakReference<T>(mvpView);
        if (viewState != null) {
            viewState.apply(mvpView);
        }
    }

    @Override
    public void detachView() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    public boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    public T getMvpView() {
        return viewRef == null ? null : viewRef.get();
    }

    public ViewState getViewState() {
        return viewState;
    }

    public void setViewState(ViewState viewState) {
        this.viewState = viewState;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) {
            throw new MvpViewNotAttachedException();
        }
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
