/*
 *  Copyright &amp;copy; 2014-2016  All rights reserved.
 *  Licensed under the 深圳中盟燧石科技 License, Version 1.0 (the "License");
 *
 */

package com.hx.template.demo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.hx.template.R;
import com.hx.template.adapter.NetworkImageHolderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ScrollingActivity extends AppCompatActivity {


    List<String> urls = new ArrayList<String>();
    @Bind(R.id.banner)
    ConvenientBanner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        urls.add("http://f.hiphotos.baidu.com/image/pic/item/3ac79f3df8dcd10097cf5921708b4710b9122f5a.jpg");
        urls.add("http://c.hiphotos.baidu.com/image/pic/item/34fae6cd7b899e51c54e161740a7d933c9950df4.jpg");
        urls.add("http://a.hiphotos.baidu.com/image/pic/item/b17eca8065380cd739c928e0a344ad34588281ec.jpg");

        banner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                NetworkImageHolderView holderView = new NetworkImageHolderView();
                return holderView;
            }
        }, urls)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可不用设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
    }

    @Override
    protected void onResume() {
        super.onResume();
        banner.startTurning(2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        banner.stopTurning();
    }
}
