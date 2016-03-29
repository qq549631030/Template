package com.hx.template.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.hx.template.BaseActivity;
import com.hx.template.R;
import com.hx.template.ui.SettingActivity;
import com.hx.template.utils.ImageUtils;
import com.hx.template.qrcode.zxing.activity.CaptureActivity;
import com.hx.template.qrcode.utils.ImageScanUtil;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DemoMainActivity extends BaseActivity {
    private static final String TAG = "DemoMainActivity";
    @Bind(R.id.image)
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called with: " + "savedInstanceState = [" + savedInstanceState + "]");
        setContentView(R.layout.activity_demo_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoMainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

//        ImageLoader.getInstance().displayImage("http://imgt6.bdstatic.com/it/u=2,3222226309&fm=25&gp=0.jpg", image, CustomApplication.defaultOptions, new ImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String s, View view) {
//                Log.d(TAG, "onLoadingStarted() called with: " + "s = [" + s + "], view = [" + view + "]");
//            }
//
//            @Override
//            public void onLoadingFailed(String s, View view, FailReason failReason) {
//                Log.d(TAG, "onLoadingFailed() called with: " + "s = [" + s + "], view = [" + view + "], failReason = [" + failReason + "]");
//            }
//
//            @Override
//            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
//                Log.d(TAG, "onLoadingComplete() called with: " + "s = [" + s + "], view = [" + view + "], bitmap = [" + bitmap + "]");
//            }
//
//            @Override
//            public void onLoadingCancelled(String s, View view) {
//                Log.d(TAG, "onLoadingCancelled() called with: " + "s = [" + s + "], view = [" + view + "]");
//            }
//        }, new ImageLoadingProgressListener() {
//            @Override
//            public void onProgressUpdate(String s, View view, int i, int i1) {
//                Log.d(TAG, "onProgressUpdate() called with: " + "s = [" + s + "], view = [" + view + "], i = [" + i + "], i1 = [" + i1 + "]");
//            }
//        });
        Picasso.with(DemoMainActivity.this).load("http://d.hiphotos.baidu.com/image/pic/item/d058ccbf6c81800aaa274a03b33533fa838b47f9.jpg").placeholder(R.drawable.default_image).into(image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_scan_qrcode:
                Intent intent = new Intent(DemoMainActivity.this, CaptureActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_create_qrcode:
                Intent intent2 = new Intent(DemoMainActivity.this, CreateQrcodeActivity.class);
                startActivity(intent2);
                return true;
            case R.id.action_scan_qrcode_from_image:
                Intent targetIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                );
                targetIntent.setType("image/*");
                this.startActivityForResult(targetIntent, 1);
                return true;
            case R.id.action_banner:
                Intent intent3 = new Intent(DemoMainActivity.this, BannerActivity.class);
                startActivity(intent3);
                return true;
            case R.id.action_ptr:
                Intent intent4 = new Intent(DemoMainActivity.this, PullToRefreshActivity.class);
                startActivity(intent4);
                return true;
            case R.id.action_cropper:
                Intent intent5 = new Intent(DemoMainActivity.this, CropperDemoActivity.class);
                startActivity(intent5);
                return true;
            case R.id.action_activity:
                Intent intent6 = new Intent(DemoMainActivity.this, ActivityDemoActivity.class);
                startActivity(intent6);
                return true;
            case R.id.action_scrolling:
                Intent intent7 = new Intent(DemoMainActivity.this, ScrollingActivity.class);
                startActivity(intent7);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (1 == requestCode && resultCode == RESULT_OK) {
            if (data == null) {
                return;
            }
            Uri uri = data.getData();
            Bitmap bitmap = ImageUtils.getImage(this, uri);
            String result = ImageScanUtil.decodeByZbar(bitmap);
//            String result = ImageScanUtil.decodeByZXing(bitmap);
            Log.e("huangxiang", "result = " + result);
        }
    }
}
