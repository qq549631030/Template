package com.hx.template.demo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.hx.template.R;
import com.hx.template.utils.TimeUtils;
import com.hx.template.views.PullToRefreshView;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PullToRefreshActivity extends AppCompatActivity {
    private static final String TAG = "PullToRefreshActivity";
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.pullToRefreshView)
    PullToRefreshView pullToRefreshView;


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
//            getMessageList();
        }
    };

    private PullToRefreshView.OnFooterRefreshListener footerRefreshListener = new PullToRefreshView.OnFooterRefreshListener() {
        @Override
        public void onFooterRefresh(PullToRefreshView view) {
            isReload = false;
//            getMessageList();
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
//        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

}
