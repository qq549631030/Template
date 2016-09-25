package com.hx.template.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class BaseMvpFragment<P extends Presenter<V>, V extends MvpView> extends BaseFragment implements MvpView, LoaderManager.LoaderCallbacks<P> {

    public final static int BASE_FRAGMENT_PRESENTER_LOADER_ID = 200;

    // boolean flag to avoid delivering the result twice. Calling initLoader in onActivityCreated makes
    // onLoadFinished will be called twice during configuration change.
    private boolean delivered = false;

    protected P presenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(BASE_FRAGMENT_PRESENTER_LOADER_ID, null, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.attachView((V) this);
        }
    }

    @Override
    public void onPause() {
        if (presenter != null) {
            presenter.detachView();
        }
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (presenter instanceof BasePresenter) {
            ViewState viewState = ((BasePresenter) presenter).getViewState();
            if (viewState == null) {
                viewState = onCreateViewState();
                ((BasePresenter) presenter).setViewState(viewState);
            }
            if (viewState != null) {
                viewState.save(this);
            }
        }
    }

    @Override
    public Loader<P> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader(getContext(), new PresenterFactory<P>() {
            @Override
            public P create() {
                return onCreatePresenter();
            }
        });
    }

    @Override
    public void onLoadFinished(Loader<P> loader, P data) {
        HXLog.d("onLoadFinished delivered = " + delivered);
        if (!delivered) {
            presenter = data;
            delivered = true;
        }
    }

    @Override
    public void onLoaderReset(Loader<P> loader) {
        HXLog.d("onLoaderReset");
        presenter = null;
    }

    protected P onCreatePresenter() {
        return null;
    }

    protected ViewState<V> onCreateViewState() {
        return null;
    }

    @Override
    public void showError(String errorMsg) {
        ToastUtils.showToast(getContext(), errorMsg);
    }

    /**
     * 显示loading对话框
     *
     * @param msg
     */
    @Override
    public void showLoadingProgress(String msg) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showDefaultLoadingProgress(msg);
        }
    }

    /**
     * 隐藏loading对话框
     */
    @Override
    public void hideLoadingProgress() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideDefaultLoadingProgress();
        }
    }
}
