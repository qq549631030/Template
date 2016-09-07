package com.hx.template.base;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.canyinghao.canrefresh.CanRefreshLayout;
import com.hx.template.R;

import java.lang.reflect.Field;

/**
 * 功能说明：com.hx.template.base
 * 作者：huangx on 2016/9/7 8:33
 * 邮箱：huangx@pycredit.cn
 */
public class BaseCanRefreshListFragment extends BaseRefreshListFragment implements CanRefreshLayout.OnRefreshListener, CanRefreshLayout.OnLoadMoreListener {

    private CanRefreshLayout canRefreshLayout;

    @Override
    protected int getLayoutRes() {
        return R.layout.layout_base_can_refresh_list;
    }

    @Override
    protected void initRefreshLayout(View parent) {
        canRefreshLayout = (CanRefreshLayout) parent.findViewById(R.id.refresh);
        if (canRefreshLayout == null) {
            throw new IllegalArgumentException("must have a CanRefreshLayout with id refresh");
        }
        canRefreshLayout.setStyle(CanRefreshLayout.UPPER, CanRefreshLayout.CLASSIC);
        canRefreshLayout.setOnRefreshListener(this);
        canRefreshLayout.setOnLoadMoreListener(this);
        canRefreshLayout.setRefreshEnabled(refreshEnable);
        canRefreshLayout.setLoadMoreEnabled(refreshEnable);
    }

    @Override
    protected void initListView(View parent) {
        listView = (ListView) parent.findViewById(R.id.can_content_view);
        if (listView == null) {
            throw new IllegalArgumentException("must have a ListView with id can_content_view");
        }
    }

    public CanRefreshLayout getRefreshLayout() {
        return canRefreshLayout;
    }

    @Override
    public void setRefreshEnable(boolean refreshEnable) {
        super.setRefreshEnable(refreshEnable);
        canRefreshLayout.setRefreshEnabled(refreshEnable);
    }

    @Override
    public void setLoadMoreEnable(boolean loadMoreEnable) {
        super.setLoadMoreEnable(loadMoreEnable);
        canRefreshLayout.setLoadMoreEnabled(loadMoreEnable);
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        if (refreshing) {
            canRefreshLayout.autoRefresh();
        } else {
            canRefreshLayout.refreshComplete();
        }
    }

    @Override
    public void setLoadingMore(boolean loadingMore) {
        if (loadingMore) {
            //unSupport
        } else {
            canRefreshLayout.loadMoreComplete();
        }
    }

    @Override
    protected boolean isRefreshing() {
        try {
            Field field = CanRefreshLayout.class.getDeclaredField("isHeaderRefreshing");
            if (field != null) {
                field.setAccessible(true);
                boolean isRefreshing = (boolean) field.get(canRefreshLayout);
                return isRefreshing;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected boolean isLoadingMore() {
        try {
            Field field = CanRefreshLayout.class.getDeclaredField("isFooterRefreshing");
            if (field != null) {
                field.setAccessible(true);
                boolean isLoadingMore = (boolean) field.get(canRefreshLayout);
                return isLoadingMore;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
