package com.hx.template.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;

import com.hx.template.base.BaseSwipeToLoadListFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        setTotalCount(120);
        setAutoLoadMoreEnable(true);
        showLoading();
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
                onDataChange();
                if (isRefreshing()) {
                    setRefreshing(false);
                }
                if (isLoadingMore()) {
                    setLoadingMore(false);
                }
                showContent();
            }
        }, 1000);
    }
}
