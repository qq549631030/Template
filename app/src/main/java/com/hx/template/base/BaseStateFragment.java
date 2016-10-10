package com.hx.template.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hx.template.R;
import com.hx.template.widget.MultiStateView;

/**
 * Created by huangxiang on 2016/10/10.
 */

public abstract class BaseStateFragment extends BaseMvpFragment {

    protected MultiStateView multiStateView;

    protected abstract int getContentRes();

    protected int getLoadingRes() {
        return R.layout.layout_base_progress;
    }

    protected int getEmptyRes() {
        return R.layout.layout_base_empty;
    }

    protected int getErrorRes() {
        return R.layout.layout_base_empty;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        multiStateView = new MultiStateView(getContext());
        multiStateView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        multiStateView.setViewForState(getContentRes(), MultiStateView.VIEW_STATE_CONTENT);
        multiStateView.setViewForState(getLoadingRes(), MultiStateView.VIEW_STATE_LOADING);
        multiStateView.setViewForState(getEmptyRes(), MultiStateView.VIEW_STATE_EMPTY);
        multiStateView.setViewForState(getErrorRes(), MultiStateView.VIEW_STATE_ERROR);
        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        return multiStateView;
    }

    /**
     * 显示内容
     */
    protected void showContent() {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
    }

    /**
     * 显示加载中
     */
    protected void showLoading() {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
    }

    /**
     * 显示空
     */
    protected void showEmpty() {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    /**
     * 显示错误
     */
    protected void showError() {
        multiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }
}
