package com.hx.template.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hx.template.R;

/**
 * 功能说明：com.hx.template.base
 * 作者：huangx on 2016/9/7 9:19
 * 邮箱：huangx@pycredit.cn
 */
public abstract class BaseRefreshListFragment extends BaseListFragment {

    public static final String EXTRA_REFRESH_ENABLE = "_extra_refresh_enable";
    public static final String EXTRA_LOAD_MORE_ENABLE = "_extra_load_more_enable";

    protected boolean refreshEnable = true;
    protected boolean loadMoreEnable = false;

    private View noMore;

    protected int getNoMoreRes() {
        return R.layout.layout_base_no_more;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            refreshEnable = args.getBoolean(EXTRA_REFRESH_ENABLE, true);
            loadMoreEnable = args.getBoolean(EXTRA_LOAD_MORE_ENABLE, true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (getNoMoreRes() > 0) {
            View noMoreView = inflater.inflate(getNoMoreRes(), null);
            noMore = noMoreView.findViewById(R.id.no_more_layout);
            listView.addFooterView(noMoreView);
        }
        return view;
    }

    @Override
    protected void initView(View parent) {
        super.initView(parent);
        initRefreshLayout(parent);
    }

    /**
     * 初始化下拉刷新控件
     *
     * @param parent
     */
    protected abstract void initRefreshLayout(View parent);

    /**
     * 打开/关闭刷新
     *
     * @param refreshEnable
     */
    public void setRefreshEnable(boolean refreshEnable) {
        this.refreshEnable = refreshEnable;
    }

    /**
     * 打开/关闭加载更多
     *
     * @param loadMoreEnable
     */
    public void setLoadMoreEnable(boolean loadMoreEnable) {
        this.loadMoreEnable = loadMoreEnable;
    }

    /**
     * 开始/停止刷新
     *
     * @param refreshing
     */
    protected abstract void setRefreshing(boolean refreshing);

    /**
     * 开始/停止加载更多
     *
     * @param loadingMore
     */
    protected abstract void setLoadingMore(boolean loadingMore);

    /**
     * @return
     */
    protected abstract boolean isRefreshing();

    /**
     * @return
     */
    protected abstract boolean isLoadingMore();

    /**
     * 显示无更多数据
     */
    public void showNoMore() {
        if (noMore != null) {
            noMore.setVisibility(View.VISIBLE);
        }
        setLoadMoreEnable(false);
    }

    /**
     * 隐藏无更多数据
     */
    public void hideNoMore() {
        if (noMore != null) {
            noMore.setVisibility(View.GONE);
        }
        setLoadMoreEnable(true);
    }
}
