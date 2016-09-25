package com.hx.template.base;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.hx.template.global.HXLog;
import com.hx.template.mvp.BasePresenter;
import com.hx.template.mvp.MvpView;
import com.hx.template.mvp.Presenter;
import com.hx.template.mvp.PresenterFactory;
import com.hx.template.mvp.PresenterLoader;
import com.hx.template.mvp.ViewState;
import com.hx.template.utils.ToastUtils;

/**
 * Created by huangxiang on 2016/9/24.
 */

public class BaseMvpActivity<P extends Presenter<V>, V extends MvpView> extends BaseActivity implements MvpView, LoaderManager.LoaderCallbacks<P> {

    public final static int BASE_ACTIVITY_PRESENTER_LOADER_ID = 100;


    protected P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportLoaderManager().initLoader(BASE_ACTIVITY_PRESENTER_LOADER_ID, null, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.attachView((V) this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (presenter instanceof BasePresenter) {
            ViewState viewState = ((BasePresenter) presenter).getViewState();
            if (viewState != null) {
                viewState.save(this);
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    public Loader<P> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader(this, new PresenterFactory<P>() {
            @Override
            public P create() {
                return onCreatePresenter();
            }
        });
    }

    @Override
    public void onLoadFinished(Loader<P> loader, P data) {
        HXLog.d("onLoadFinished");
        presenter = data;
        if (presenter instanceof BasePresenter) {
            ((BasePresenter) presenter).setViewState(onCreateViewState());
        }
    }

    @Override
    public void onLoaderReset(Loader<P> loader) {
        HXLog.d("onLoaderReset");
        presenter = null;
    }

    @Override
    public void showLoadingProgress(String msg) {
        showDefaultLoadingProgress(msg);
    }

    @Override
    public void hideLoadingProgress() {
        hideDefaultLoadingProgress();
    }

    @Override
    public void showError(String errorMsg) {
        ToastUtils.showToast(this, errorMsg);
    }

    protected P onCreatePresenter() {
        return null;
    }

    protected ViewState<V> onCreateViewState() {
        return null;
    }
}
