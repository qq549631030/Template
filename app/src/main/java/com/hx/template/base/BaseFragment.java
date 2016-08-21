package com.hx.template.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.View;

import com.hx.template.global.HXLog;
import com.hx.template.global.SaveSceneUtils;
import com.hx.template.mvpview.MvpView;
import com.hx.template.presenter.Presenter;

/**
 * Created by huangx on 2016/5/12.
 */
public class BaseFragment<P extends Presenter<V>, V extends MvpView> extends Fragment implements MvpView, LoaderManager.LoaderCallbacks<P> {

    public final static int BASE_FRAGMENT_LOADER_ID = 200;

    protected boolean isViewCreated;

    protected boolean isVisable;

    // boolean flag to avoid delivering the result twice. Calling initLoader in onActivityCreated makes
    // onLoadFinished will be called twice during configuration change.
    private boolean delivered = false;

    protected P presenter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        String title = getFragmentTitle();
        if (!TextUtils.isEmpty(title)) {
            activity.setTitle(title);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SaveSceneUtils.onRestoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(BASE_FRAGMENT_LOADER_ID, null, this);
        SaveSceneUtils.onRestoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        SaveSceneUtils.onRestoreInstanceState(this, savedInstanceState);
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
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
    }

    protected String getFragmentTitle() {
        return null;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        SaveSceneUtils.onSaveInstanceState(this, outState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisable = isVisibleToUser;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        if (!hidden) {
            String title = getFragmentTitle();
            if (!TextUtils.isEmpty(title)) {
                getActivity().setTitle(title);
            }
        }
        super.onHiddenChanged(hidden);
    }

    public boolean onBackKeyPress() {
        return false;
    }

    public void finish() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }


    @Override
    public Loader<P> onCreateLoader(int id, Bundle args) {
        return null;
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

    @Override
    public void showError(String errorMsg) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showError(errorMsg);
        }
    }

    /**
     * 显示loading对话框
     *
     * @param msg
     */
    @Override
    public void showLoadingProgress(String msg) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showLoadingProgress(msg);
        }
    }

    /**
     * 隐藏loading对话框
     */
    @Override
    public void hideLoadingProgress() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideLoadingProgress();
        }
    }
}
