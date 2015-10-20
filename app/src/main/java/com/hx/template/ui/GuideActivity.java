package com.hx.template.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hx.template.BaseActivity;
import com.hx.template.Constant;
import com.hx.template.MainActivity;
import com.hx.template.R;
import com.hx.template.utils.SharedPreferencesUtil;
import com.hx.template.views.PageIndicator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideActivity extends BaseActivity {

    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.start)
    TextView start;
    @Bind(R.id.indicator)
    PageIndicator indicator;

    private int bitmapsFull[] = new int[]{R.drawable.guide_01, R.drawable.guide_02,
            R.drawable.guide_03};

    private View[] mViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        mViews = new View[bitmapsFull.length];
        viewpager.setAdapter(new GuidePagerAdapter(this));
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == bitmapsFull.length - 1) {
                    start.setVisibility(View.VISIBLE);
                } else {
                    start.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        indicator.setViewPager(viewpager);
    }


    @OnClick({R.id.start})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                SharedPreferencesUtil.setParam(getApplicationContext(), Constant.pref_isFirst, false);
                Intent intentHome = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intentHome);
                GuideActivity.this.finish();
                break;
        }
    }

    private class GuidePagerAdapter extends PagerAdapter {
        private Context context;

        public GuidePagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return mViews.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(bitmapsFull[position]);
            ((ViewPager) container).addView(imageView, 0);
            mViews[position] = imageView;
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(mViews[position]);
            mViews[position] = null;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
