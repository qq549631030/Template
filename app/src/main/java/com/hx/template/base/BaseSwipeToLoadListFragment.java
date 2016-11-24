package com.hx.template.base;

import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.hx.template.R;

import cn.hx.swipetoloadlayout.OnLoadMoreListener;
import cn.hx.swipetoloadlayout.OnRefreshListener;
import cn.hx.swipetoloadlayout.SwipeToLoadLayout;

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
    protected void setRefreshEnableImpl(boolean refreshEnable) {
        swipeToLoadLayout.setRefreshEnabled(refreshEnable);
    }

    @Override
    protected void setLoadMoreEnableImpl(boolean loadMoreEnable) {
        swipeToLoadLayout.setLoadMoreEnabled(loadMoreEnable);
    }

    /**
     * 打开/关闭自动加载更多实现(注意:使用这个功能，ListView.setOnScrollListener将不可使用)
     *
     * @param autoLoadMore
     */
    @Override
    protected void setAutoLoadMoreEnableImpl(boolean autoLoadMore) {
        if (autoLoadMore) {
            getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    if (SCROLL_STATE_IDLE == scrollState) {
                        if (!ViewCompat.canScrollVertically(view, 1)) {
                            if (hasMoreData()) {
                                onLoadMore();
                            }
                        }
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });
        } else {
            getListView().setOnScrollListener(null);
        }
    }

    @Override
    protected void setRefreshingImpl(boolean refreshing) {
        swipeToLoadLayout.setRefreshing(refreshing);
    }

    @Override
    protected void setLoadingMoreImpl(boolean loadingMore) {
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
