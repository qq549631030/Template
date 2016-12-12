package com.hx.template.base;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.hx.mvp.presenter.BasePresenter;
import com.hx.mvp.presenter.Presenter;
import com.hx.mvp.presenter.PresenterFactory;
import com.hx.mvp.presenter.PresenterLoader;
import com.hx.mvp.view.BaseMvpView;
import com.hx.mvp.view.ViewState;
import com.hx.template.global.HXLog;
import com.hx.template.utils.ToastUtils;

/**
 * Created by huangxiang on 2016/9/24.
 */

public class BaseMvpActivity<P extends Presenter<V>, V extends BaseMvpView> extends BaseActivity implements BaseMvpView, LoaderManager.LoaderCallbacks<P> {

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
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    public Loader<P> onCreateLoader(int id, Bundle args) {
        if (BASE_ACTIVITY_PRESENTER_LOADER_ID == id) {
            return new PresenterLoader(this, new PresenterFactory<P>() {
                @Override
                public P create() {
                    return onCreatePresenter();
                }
            });
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<P> loader, P data) {
        HXLog.d("onLoadFinished");
        presenter = data;
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

    /**
     * 显示/隐藏加载中动画
     *
     * @param show true 显示 false 隐藏
     * @param args 额外参数
     */
    @Override
    public void showLoadingProgress(boolean show, Object... args) {
        if (show) {
            String loadingMsg;
            if (args != null && args.length > 0) {
                loadingMsg = args[0].toString();
            } else {
                loadingMsg = "";
            }
            showDefaultLoadingProgress(loadingMsg);
        } else {
            hideDefaultLoadingProgress();
        }
    }

    /**
     * 通用错误
     *
     * @param errorData 错误数据
     */
    @Override
    public void showError(Object... errorData) {
        if (errorData != null && errorData.length > 0) {
            ToastUtils.showToast(this, errorData[0].toString());
        }
    }
}
