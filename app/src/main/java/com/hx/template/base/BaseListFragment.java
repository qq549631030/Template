package com.hx.template.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ListView;

import com.canyinghao.canrefresh.CanRefreshLayout;
import com.hx.template.R;

/**
 * 功能说明：com.hx.template.base
 * 作者：huangx on 2016/9/6 9:19
 * 邮箱：huangx@pycredit.cn
 */
public class BaseListFragment extends BaseFragment implements CanRefreshLayout.OnRefreshListener, CanRefreshLayout.OnLoadMoreListener {

    public static final String EXTRA_REFRESH_ENABLE = "_extra_refresh_enable";

    private CanRefreshLayout refreshLayout;
    private View refreshHeader;
    private View refreshFooter;
    private ListView listView;
    private ViewStub empty;
    private ViewStub progress;
    private View noMore;

    private boolean refreshEnable = true;

    protected int getLayoutRes() {
        return R.layout.layout_base_list;
    }

    protected int getEmptyRes() {
        return R.layout.layout_base_empty;
    }

    protected int getProgressRes() {
        return R.layout.layout_base_progress;
    }

    protected int getNoMoreRes() {
        return R.layout.layout_base_no_more;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            refreshEnable = args.getBoolean(EXTRA_REFRESH_ENABLE, true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutRes() <= 0) {
            throw new IllegalArgumentException("layout error");
        }
        View view = inflater.inflate(getLayoutRes(), null);
        initRefreshLayout(view);
        if (refreshLayout != null) {
            refreshLayout.setStyle(CanRefreshLayout.UPPER, CanRefreshLayout.CLASSIC);
            refreshLayout.setOnRefreshListener(this);
            refreshLayout.setOnLoadMoreListener(this);
            refreshLayout.setRefreshEnabled(refreshEnable);
            refreshLayout.setLoadMoreEnabled(refreshEnable);
        }
        initListView(view);
        if (listView == null) {
            throw new IllegalArgumentException("listView must be not null");
        }
        initEmpty(view);
        if (empty != null) {
            listView.setEmptyView(empty);
        }
        initProgress(view);
        if (getLayoutRes() > 0) {
            View noMoreView = inflater.inflate(getNoMoreRes(), null);
            noMore = noMoreView.findViewById(R.id.no_more_layout);
            listView.addFooterView(noMoreView);
        }
        return view;
    }

    /**
     * 初始化下拉刷新控件
     *
     * @param parent
     */
    protected void initRefreshLayout(View parent) {
        refreshLayout = (CanRefreshLayout) parent.findViewById(R.id.refresh);
        refreshHeader = parent.findViewById(R.id.can_refresh_header);
        refreshFooter = parent.findViewById(R.id.can_refresh_footer);
    }

    /**
     * 初始化ListView
     *
     * @param parent
     */
    protected void initListView(View parent) {
        listView = (ListView) parent.findViewById(R.id.can_content_view);
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

    public CanRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    public ListView getListView() {
        return listView;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

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

    /**
     * 显示无更多数据
     */
    public void showNoMore() {
        if (noMore != null) {
            noMore.setVisibility(View.VISIBLE);
            noMore.setPadding(0, 0, 0, 0);
        }
    }

    /**
     * 隐藏无更多数据
     */
    public void hideNoMore() {
        if (noMore != null) {
            noMore.setVisibility(View.GONE);
            noMore.setPadding(0, -1 * noMore.getHeight(), 0, 0);
        }
    }
}
