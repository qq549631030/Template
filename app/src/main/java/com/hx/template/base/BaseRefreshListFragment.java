package com.hx.template.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hx.template.R;

/**
 * 功能说明：com.hx.template.base
 * 作者：huangx on 2016/9/7 9:19
 * 邮箱：huangx@pycredit.cn
 */
public abstract class BaseRefreshListFragment extends BaseListFragment {

    public static final String EXTRA_REFRESH_ENABLE = "_extra_refresh_enable";
    public static final String EXTRA_LOAD_MORE_ENABLE = "_extra_load_more_enable";
    public static final String EXTRA_AUTO_LOAD_MORE_ENABLE = "_extra_load_more_enable";

    protected View noMore;

    protected boolean refreshEnable = true;
    protected boolean loadMoreEnable = true;
    protected boolean autoLoadMore = false;

    protected int totalCount = 0;

    protected int getNoMoreRes() {
        return R.layout.layout_base_more;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            refreshEnable = args.getBoolean(EXTRA_REFRESH_ENABLE, true);
            loadMoreEnable = args.getBoolean(EXTRA_LOAD_MORE_ENABLE, false);
            autoLoadMore = args.getBoolean(EXTRA_AUTO_LOAD_MORE_ENABLE, false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (getNoMoreRes() > 0) {
            View more = inflater.inflate(getNoMoreRes(), null);
            initNoMoreLayout(more);
            noMore = more.findViewById(R.id.no_more_layout);
            getListView().addFooterView(more);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRefreshEnable(refreshEnable);
        setLoadMoreEnable(loadMoreEnable);
        if (autoLoadMore) {
            setAutoLoadMoreImpl(autoLoadMore);
        }
    }

    @Override
    protected void initView(View parent) {
        super.initView(parent);
        initRefreshLayout(parent);
    }

    /**
     * 数据改变
     */
    public void onDataChange() {
        if (hasMoreData()) {
            hideNoMore();
        } else {
            showNoMore();
        }
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 是否有更多数据
     *
     * @return
     */
    protected boolean hasMoreData() {
        int currentCount = getListView().getAdapter() == null ? 0 : getListView().getAdapter().getCount();
        return currentCount < totalCount;
    }

    /**
     * 初始化下拉刷新控件
     *
     * @param parent
     */
    protected abstract void initRefreshLayout(View parent);

    /**
     * 初始化没有更多数据
     *
     * @param parent
     */
    protected void initNoMoreLayout(View parent) {

    }

    /**
     * 打开/关闭刷新
     *
     * @param refreshEnable
     */
    public void setRefreshEnable(boolean refreshEnable) {
        this.refreshEnable = refreshEnable;
        setRefreshEnableImpl(refreshEnable);
    }

    /**
     * 打开/关闭刷新实现
     *
     * @param refreshEnable
     */
    protected abstract void setRefreshEnableImpl(boolean refreshEnable);

    /**
     * 打开/关闭加载更多
     *
     * @param loadMoreEnable
     */
    public void setLoadMoreEnable(boolean loadMoreEnable) {
        this.loadMoreEnable = loadMoreEnable;
        setLoadMoreEnableImpl(loadMoreEnable);
    }

    /**
     * 打开/关闭加载更多实现
     *
     * @param loadMoreEnable
     */
    protected abstract void setLoadMoreEnableImpl(boolean loadMoreEnable);

    /**
     * 打开/关闭自动加载更多(注意:使用这个功能，ListView.setOnScrollListener将不可使用)
     *
     * @param autoLoadMore
     */
    public void setAutoLoadMore(boolean autoLoadMore) {
        this.autoLoadMore = autoLoadMore;
        setAutoLoadMoreImpl(autoLoadMore);
    }

    /**
     * 打开/关闭自动加载更多实现
     *
     * @param autoLoadMore
     */
    protected void setAutoLoadMoreImpl(boolean autoLoadMore) {
        if (autoLoadMore) {
            getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    if (SCROLL_STATE_IDLE == scrollState) {
                        if (!ViewCompat.canScrollVertically(view, 1)) {
                            if (hasMoreData()) {
                                setLoadingMore(true);
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

    /**
     * 开始/停止刷新
     *
     * @param refreshing
     */
    public void setRefreshing(boolean refreshing) {
        setRefreshingImpl(refreshing);
    }

    /**
     * 开始/停止刷新实现
     *
     * @param refreshing
     */
    protected abstract void setRefreshingImpl(boolean refreshing);

    /**
     * 开始/停止加载更多
     *
     * @param loadingMore
     */
    public void setLoadingMore(boolean loadingMore) {
        setLoadingMoreImpl(loadingMore);
    }

    /**
     * 开始/停止加载更多实现
     *
     * @param loadingMore
     */
    protected abstract void setLoadingMoreImpl(boolean loadingMore);

    /**
     * 是否正在刷新
     *
     * @return
     */
    protected abstract boolean isRefreshing();

    /**
     * 是否正在加载更多
     *
     * @return
     */
    protected abstract boolean isLoadingMore();

    /**
     * 下拉下新、下拉加载更多结束
     */
    protected void onReset() {
        if (hasMoreData()) {
            setLoadMoreEnable(true);
        } else {
            setLoadMoreEnable(false);
        }
    }

    /**
     * 显示无更多数据
     */
    public void showNoMore() {
        if (noMore != null) {
            noMore.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏无更多数据
     */
    public void hideNoMore() {
        if (noMore != null) {
            noMore.setVisibility(View.GONE);
        }
    }
}
