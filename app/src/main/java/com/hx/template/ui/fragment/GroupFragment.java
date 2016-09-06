package com.hx.template.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hx.template.R;
import com.hx.template.base.BaseFragment;
import com.hx.template.base.BaseListFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends BaseListFragment {

    private BaseAdapter adapter;

    private List<Map<String, String>> list = new ArrayList<>();

    private Handler handler = new Handler();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new SimpleAdapter(getContext(), list, android.R.layout.simple_expandable_list_item_1, new String[]{"title"}, new int[]{android.R.id.text1});
        getListView().setAdapter(adapter);
        getListView().setDividerHeight(10);
        showProgress();
        fetchData();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        list.clear();
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
                for (int i = 0; i < 40; i++) {
                    Map<String, String> item = new HashMap<String, String>();
                    item.put("title", "item" + i);
                    list.add(item);
                }
                adapter.notifyDataSetChanged();
                hideProgress();
                getRefreshLayout().refreshComplete();
                getRefreshLayout().loadMoreComplete();
                if (list.size() > 120) {
//                    showNoMore();
                    getRefreshLayout().setLoadMoreEnabled(false);
                } else {
//                    hideNoMore();
                    getRefreshLayout().setLoadMoreEnabled(true);
                }
            }
        }, 2000);
    }
}
