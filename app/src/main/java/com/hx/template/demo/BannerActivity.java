package com.hx.template.demo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.hx.template.R;
import com.hx.template.adapter.NetworkImageHolderView;
import com.hx.template.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BannerActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "BannerActivity";

    ConvenientBanner banner;

    FloatingActionButton fab;

    List<String> urls = new ArrayList<String>();

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        banner = (ConvenientBanner) findViewById(R.id.banner);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urls.add("http://f.hiphotos.baidu.com/image/pic/item/3ac79f3df8dcd10097cf5921708b4710b9122f5a.jpg");
                banner.notifyDataSetChanged();
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
    protected void onStart() {
        super.onStart();
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

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                int count = new Random().nextInt(10);
                urls.clear();
                for (int i = 0; i <count ; i++) {
                    urls.add("http://f.hiphotos.baidu.com/image/pic/item/3ac79f3df8dcd10097cf5921708b4710b9122f5a.jpg");
                }
                banner.notifyDataSetChanged();
                break;
        }
    }
}
