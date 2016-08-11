package com.hx.template.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.hx.template.R;
import com.hx.template.base.BaseActivity;
import com.hx.template.global.HXLog;
import com.hx.template.imageloader.ImageLoaderManager;
import com.hx.template.imageloader.ImageLoadingListener;
import com.hx.template.qrcode.activity.CaptureActivity;
import com.hx.template.qrcode.utils.ImageScanUtil;
import com.hx.template.utils.ImageUtils;
import com.hx.template.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DemoMainActivity extends BaseActivity {
    private static final String TAG = "DemoMainActivity";
    @Bind(R.id.image)
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(DemoMainActivity.this, SelectImageActivity.class);
//                startActivity(intent);
                ImageLoaderManager.getImageLoader(DemoMainActivity.this).loadImageAsync("https://dn-pycredit-pub.qbox.me/logo-rzyl.png", new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, String errorCode, String errorMsg) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        HXLog.d("onLoadingComplete() called with: " + "imageUri = [" + imageUri + "], view = [" + view + "], loadedImage = [" + loadedImage + "]");
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });
            }
        });
        ImageLoaderManager.getImageLoader(this).displayImage("http://b.hiphotos.baidu.com/image/h%3D200/sign=239b2b62d3ca7bcb627bc02f8e086b3f/7dd98d1001e9390170aa9f9f7fec54e737d196e2.jpg", image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_zxing_scan_qrcode:
                intent = new Intent(DemoMainActivity.this, CaptureActivity.class);
                intent.putExtra("mode", CaptureActivity.SCAN_BY_ZXING);
                startActivity(intent);
                return true;
            case R.id.action_zbar_scan_qrcode:
                intent = new Intent(DemoMainActivity.this, CaptureActivity.class);
                intent.putExtra("mode", CaptureActivity.SCAN_BY_ZBAR);
                startActivity(intent);
                return true;
            case R.id.action_create_qrcode:
                intent = new Intent(DemoMainActivity.this, CreateQrcodeActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_zxing_scan_qrcode_from_image:
                intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                );
                intent.setType("image/*");
                this.startActivityForResult(intent, 1);
                return true;
            case R.id.action_zbar_scan_qrcode_from_image:
                intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                );
                intent.setType("image/*");
                this.startActivityForResult(intent, 2);
                return true;
            case R.id.action_banner:
                intent = new Intent(DemoMainActivity.this, BannerActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_ptr:
                intent = new Intent(DemoMainActivity.this, PullToRefreshActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_cropper:
                intent = new Intent(DemoMainActivity.this, CropperDemoActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_activity:
                intent = new Intent(DemoMainActivity.this, ActivityDemoActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_scrolling:
                intent = new Intent(DemoMainActivity.this, ScrollingActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_rxjava:
                intent = new Intent(DemoMainActivity.this, RxJavaActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data == null) {
                return;
            }
            Uri uri = data.getData();
            Bitmap bitmap = ImageUtils.getImage(this, uri);
            String result = null;
            if (requestCode == 1) {
                result = ImageScanUtil.decodeByZXing(bitmap);
            } else if (requestCode == 2) {
                result = ImageScanUtil.decodeByZbar(bitmap);
            }
            ToastUtils.showToast(getApplicationContext(), result);
        }
    }
}
