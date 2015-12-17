package com.hx.template.demo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.hx.template.BaseActivity;
import com.hx.template.R;
import com.hx.template.adapter.NetworkImageHolderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BannerActivity extends BaseActivity {

    @Bind(R.id.banner)
    ConvenientBanner banner;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    List<String> urls = new ArrayList<String>();
    @Bind(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                        //设置翻页的效果，不需要翻页效果可用不设
                .setPageTransformer(ConvenientBanner.Transformer.DefaultTransformer);
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

    @OnClick({R.id.button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:

                urls.add("http://f.hiphotos.baidu.com/image/pic/item/3ac79f3df8dcd10097cf5921708b4710b9122f5a.jpg");
                urls.add("http://c.hiphotos.baidu.com/image/pic/item/34fae6cd7b899e51c54e161740a7d933c9950df4.jpg");
                urls.add("http://a.hiphotos.baidu.com/image/pic/item/b17eca8065380cd739c928e0a344ad34588281ec.jpg");

                banner.notifyDataSetChanged();
                break;
        }
    }
}
