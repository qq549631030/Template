package com.hx.template.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ListView;

import com.hx.template.R;

/**
 * 功能说明：com.hx.template.base
 * 作者：huangx on 2016/9/6 9:19
 * 邮箱：huangx@pycredit.cn
 */
public class BaseListFragment extends BaseFragment {

    protected ListView listView;
    private ViewStub empty;
    private ViewStub progress;

    protected int getLayoutRes() {
        return R.layout.layout_base_list;
    }

    protected int getEmptyRes() {
        return R.layout.layout_base_empty;
    }

    protected int getProgressRes() {
        return R.layout.layout_base_progress;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutRes() <= 0) {
            throw new IllegalArgumentException("layout error");
        }
        View view = inflater.inflate(getLayoutRes(), null);
        initView(view);
        initListView(view);
        if (listView == null) {
            throw new IllegalArgumentException("listView must be not null");
        }
        initEmpty(view);
        if (empty != null) {
            listView.setEmptyView(empty);
        }
        initProgress(view);
        return view;
    }

    /**
     * 初始化View
     *
     * @param parent
     */
    protected void initView(View parent) {

    }

    /**
     * 初始化ListView
     *
     * @param parent
     */
    protected void initListView(View parent) {
        listView = (ListView) parent.findViewById(R.id.listView);
    }

    /**
     * 初始化Empty
     *
     * @param parent
     */
    protected void initEmpty(View parent) {
        empty = (ViewStub) parent.findViewById(R.id.empty);
        if (empty != null) {
            if (getEmptyRes() > 0) {
                empty.setLayoutResource(getEmptyRes());
                empty.inflate();
            }
        }
    }

    /**
     * 初始化Loading
     *
     * @param parent
     */
    protected void initProgress(View parent) {
        progress = (ViewStub) parent.findViewById(android.R.id.progress);
        if (progress != null) {
            if (getProgressRes() > 0) {
                progress.setLayoutResource(getProgressRes());
                progress.inflate();
            }
        }
    }

    public ListView getListView() {
        return listView;
    }

    /**
     * 显示加载中
     */
    public void showProgress() {
        if (progress != null && getProgressRes() > 0) {
            progress.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏加载中
     */
    public void hideProgress() {
        if (progress != null && getProgressRes() > 0) {
            progress.setVisibility(View.GONE);
        }
    }
}
