package com.hx.template.base;

import android.view.View;
import android.widget.ListView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hx.template.R;

/**
 * 功能说明：com.hx.template.base
 * 作者：huangx on 2016/9/7 8:34
 * 邮箱：huangx@pycredit.cn
 */
public class BaseSwipeToLoadListFragment extends BaseRefreshListFragment implements OnRefreshListener, OnLoadMoreListener {

    private SwipeToLoadLayout swipeToLoadLayout;

    @Override
    protected int getContentRes() {
        return R.layout.layout_base_swip_to_load_list;
    }

    @Override
    protected void initRefreshLayout(View parent) {
        swipeToLoadLayout = (SwipeToLoadLayout) parent.findViewById(R.id.refresh);
        if (swipeToLoadLayout == null) {
            throw new IllegalArgumentException("must have a SwipeToLoadLayout with id refresh");
        }
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshEnabled(refreshEnable);
        swipeToLoadLayout.setLoadMoreEnabled(loadMoreEnable);
    }

    @Override
    protected void initListView(View parent) {
        listView = (ListView) parent.findViewById(R.id.swipe_target);
        if (listView == null) {
            throw new IllegalArgumentException("must have a ListView with id swipe_target");
        }
    }

    public SwipeToLoadLayout getRefreshLayout() {
        return swipeToLoadLayout;
    }

    @Override
    public void setRefreshEnable(boolean refreshEnable) {
        super.setRefreshEnable(refreshEnable);
        swipeToLoadLayout.setRefreshEnabled(refreshEnable);
    }

    @Override
    public void setLoadMoreEnable(boolean loadMoreEnable) {
        super.setLoadMoreEnable(loadMoreEnable);
        swipeToLoadLayout.setLoadMoreEnabled(loadMoreEnable);
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        swipeToLoadLayout.setRefreshing(refreshing);
    }

    @Override
    public void setLoadingMore(boolean loadingMore) {
        swipeToLoadLayout.setLoadingMore(loadingMore);
    }

    @Override
    protected boolean isRefreshing() {
        return swipeToLoadLayout.isRefreshing();
    }

    @Override
    protected boolean isLoadingMore() {
        return swipeToLoadLayout.isLoadingMore();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {

    }
}
