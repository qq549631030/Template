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
import com.hx.template.CustomApplication;
import com.hx.template.R;
import com.hx.template.ui.SettingActivity;
import com.hx.template.utils.ImageUtils;
import com.hx.template.qrcode.activity.CaptureActivity;
import com.hx.template.qrcode.utils.ImageScanUtil;
import com.hx.template.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
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
        setContentView(R.layout.activity_demo_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Common.testLog(" click here");
                Intent intent = new Intent(DemoMainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        ImageLoader.getInstance().displayImage("http://imgt6.bdstatic.com/it/u=2,3222226309&fm=25&gp=0.jpg", image);
//        Picasso.with(DemoMainActivity.this).load("http://d.hiphotos.baidu.com/image/pic/item/d058ccbf6c81800aaa274a03b33533fa838b47f9.jpg").placeholder(R.drawable.default_image).into(image);
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
