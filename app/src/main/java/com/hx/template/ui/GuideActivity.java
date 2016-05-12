package com.hx.template.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.HandlerThread;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.hx.template.base.BaseActivity;
import com.hx.template.Constant;
import com.hx.template.R;
import com.hx.template.demo.DemoMainActivity;
import com.hx.template.utils.ColorUtils;
import com.hx.template.utils.SharedPreferencesUtil;
import com.hx.template.components.PageIndicator;

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

    HandlerThread handlerThread = new HandlerThread("handleThread");

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
                changeColor(position);
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
        changeColor(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.start})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                SharedPreferencesUtil.setParam(getApplicationContext(), Constant.pref_isFirst, false);
                Intent intentHome = new Intent(GuideActivity.this, DemoMainActivity.class);
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
            container.addView(imageView, 0);
            mViews[position] = imageView;
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViews[position]);
            mViews[position] = null;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


    private void changeColor(int position){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),bitmapsFull[position]);
        // Asynchronous
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                Palette.Swatch vibrant = palette.getVibrantSwatch();
                // 其中状态栏、游标、底部导航栏的颜色需要加深一下，也可以不加，具体情况在代码之后说明
                start.setBackgroundColor(ColorUtils.colorBurn(vibrant.getRgb()));
                start.setTextColor(vibrant.getBodyTextColor());
                if (android.os.Build.VERSION.SDK_INT >= 21) {
                    Window window = getWindow();
                    // 很明显，这两货是新API才有的。
                    window.setStatusBarColor(ColorUtils.colorBurn(vibrant.getRgb()));
                    window.setNavigationBarColor(ColorUtils.colorBurn(vibrant.getRgb()));
                }
            }
        });
    }
}
