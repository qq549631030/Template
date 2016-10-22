package com.hx.template.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    protected boolean refreshEnable = true;
    protected boolean loadMoreEnable = false;
    protected boolean autoLoadMore = false;

    private View more;
    private View loadingMore;
    private ProgressBar pbLoading;
    private TextView tvMore;
    private View noMore;
    private TextView tvNoMore;

    protected int getMoreRes() {
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
        if (getMoreRes() > 0) {
            View moreView = inflater.inflate(getMoreRes(), null);
            more = moreView.findViewById(R.id.no_more_layout);
            loadingMore = moreView.findViewById(R.id.ll_loading_more);
            pbLoading = (ProgressBar) moreView.findViewById(R.id.pb_loading);
            tvMore = (TextView) moreView.findViewById(R.id.tv_more);

            noMore = moreView.findViewById(R.id.no_more);
            tvNoMore = (TextView) moreView.findViewById(R.id.tv_no_more);

            listView.addFooterView(moreView);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRefreshEnable(refreshEnable);
        if (autoLoadMore) {
            if (more != null) {
                more.setVisibility(View.VISIBLE);
            }
            if (loadingMore != null) {
                loadingMore.setVisibility(View.VISIBLE);
            }
            if (noMore != null) {
                noMore.setVisibility(View.GONE);
            }
            setLoadMoreEnable(false);
        } else {
            if (more != null) {
                more.setVisibility(View.GONE);
            }
            setLoadMoreEnable(loadMoreEnable);
        }
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
        setRefreshEnableImpl(refreshEnable);
    }

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

    protected abstract void setLoadMoreEnableImpl(boolean loadMoreEnable);

    /**
     * 开始/停止刷新
     *
     * @param refreshing
     */
    public void setRefreshing(boolean refreshing) {
        if (refreshEnable) {
            setRefreshEnableImpl(refreshing);
        }
    }

    protected abstract void setRefreshingImpl(boolean refreshing);

    /**
     * 开始/停止加载更多
     *
     * @param loadingMore
     */
    public void setLoadingMore(boolean loadingMore) {
        if (loadMoreEnable) {
            setLoadingMoreImpl(loadingMore);
        }
    }

    protected abstract void setLoadingMoreImpl(boolean loadingMore);

    /**
     * @return
     */
    protected abstract boolean isRefreshing();

    /**
     * @return
     */
    protected abstract boolean isLoadingMore();

    public void showNoMore() {
        showNoMore("NO MORE DATA");
    }

    /**
     * 显示无更多数据
     */
    public void showNoMore(CharSequence noMoreStr) {
        if (more != null) {
            more.setVisibility(View.VISIBLE);
        }
        if (loadingMore != null) {
            loadingMore.setVisibility(View.GONE);
        }
        if (noMore != null) {
            noMore.setVisibility(View.VISIBLE);
        }
        if (tvMore != null) {
            tvMore.setText(noMoreStr);
        }
        setLoadMoreEnable(false);
    }

    /**
     * 隐藏无更多数据
     */
    public void hideNoMore() {
        if (more != null) {
            if (autoLoadMore) {
                more.setVisibility(View.VISIBLE);
                if (loadingMore != null) {
                    loadingMore.setVisibility(View.VISIBLE);
                }
                if (noMore != null) {
                    noMore.setVisibility(View.GONE);
                }
            } else {
                more.setVisibility(View.GONE);
            }
        }
        if (loadMoreEnable) {
            if (autoLoadMore) {
                setLoadMoreEnable(false);
            } else {
                setLoadMoreEnable(true);
            }
        } else {
            setLoadMoreEnable(false);
        }
    }
}
