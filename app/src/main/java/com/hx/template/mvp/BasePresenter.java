package com.hx.template.mvp;

import android.support.annotation.NonNull;

/**
 * Created by huangx on 2016/5/9.
 */
public class BasePresenter<T extends MvpView> implements Presenter<T> {

    private T mMvpView;

    @Override
    public void attachView(@NonNull T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    @Override
    public void onDestroyed() {
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
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
