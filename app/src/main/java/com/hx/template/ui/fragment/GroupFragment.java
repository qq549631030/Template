package com.hx.template.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hx.template.R;
import com.hx.template.base.BaseCanRefreshListFragment;
import com.hx.template.base.BaseFragment;
import com.hx.template.base.BaseListFragment;
import com.hx.template.base.BaseSwipeToLoadListFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends BaseSwipeToLoadListFragment {

    private BaseAdapter adapter;

    private List<Map<String, String>> list = new ArrayList<>();

    private Handler handler = new Handler();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new SimpleAdapter(getContext(), list, android.R.layout.simple_expandable_list_item_1, new String[]{"title"}, new int[]{android.R.id.text1});
        getListView().setAdapter(adapter);
        getListView().setDividerHeight(10);
//        getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView absListView, int i) {
//                if (i == SCROLL_STATE_IDLE) {
//                    if (absListView.getLastVisiblePosition() == absListView.getCount() - 1 && !ViewCompat.canScrollVertically(absListView, 1)) {
//                        setLoadingMore(true);
//                    }
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
//
//            }
//        });
        showProgress();
        fetchData();
    }

    @Override
    public void onRefresh() {
        fetchData();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        fetchData();
    }

    private void fetchData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isRefreshing()) {
                    list.clear();
                }
                int start = list.size();
                for (int i = 0; i < 40; i++) {
                    Map<String, String> item = new HashMap<String, String>();
                    item.put("title", "item" + (start + i));
                    list.add(item);
                }
                adapter.notifyDataSetChanged();
                if (isRefreshing()) {
                    setRefreshing(false);
                }
                if (isLoadingMore()) {
                    setLoadingMore(false);
                }
                if (list.size() >= 120) {
                    showNoMore();
                } else {
                    hideNoMore();
                }
                hideProgress();
            }
        }, 1000);
    }
}
