package com.hx.template.demo;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hx.template.R;
import com.hx.template.base.BaseActivity;
import com.hx.template.widget.PullToRefreshView;
import com.hx.template.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PullToRefreshActivity extends BaseActivity {
    private static final String TAG = "PullToRefreshActivity";
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.pullToRefreshView)
    PullToRefreshView pullToRefreshView;
    ArrayAdapter adapter;
    List<String> datas = new ArrayList<String>();
    /**
     * 是否加载过数据
     */
    private boolean initRefresh;
    /**
     * 是否重新加载
     */
    private boolean isReload;
    /**
     * 当前分页号
     */
    private int pageNo = 1;

    private PullToRefreshView.OnHeaderRefreshListener headerRefreshListener = new PullToRefreshView.OnHeaderRefreshListener() {
        @Override
        public void onHeaderRefresh(PullToRefreshView view) {
            isReload = true;
            pageNo = 1;
            getDataList();
        }
    };

    private PullToRefreshView.OnFooterRefreshListener footerRefreshListener = new PullToRefreshView.OnFooterRefreshListener() {
        @Override
        public void onFooterRefresh(PullToRefreshView view) {
            isReload = false;
            getDataList();
        }
    };

    private void onHeaderRefreshComplete() {
        pullToRefreshView.onHeaderRefreshComplete(String.format(getResources().getString(R.string.pull_to_refresh_pull_summary_formart), TimeUtils.transformToTime1(Calendar.getInstance().getTimeInMillis())));
    }

    private void onFooterRefreshComplete() {
        pullToRefreshView.onFooterRefreshComplete();
    }

    private void onRefreshComplete() {
        if (isReload) {
            onHeaderRefreshComplete();
        } else {
            onFooterRefreshComplete();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh);
        ButterKnife.bind(this);
        pullToRefreshView.setOnHeaderRefreshListener(headerRefreshListener);
        pullToRefreshView.setOnFooterRefreshListener(footerRefreshListener);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        for (int i = 0; i < 10; i++) {
            datas.add("item " + i);
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, datas);
        listView.setAdapter(adapter);
    }


    private void getDataList() {
        if (isReload) {
            datas.clear();
        }
        for (int i = 0; i < 10; i++) {
            datas.add("item " + i);
        }
        pageNo++;
        onRefreshComplete();
    }

}
