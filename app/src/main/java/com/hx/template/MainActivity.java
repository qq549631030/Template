package com.hx.template;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.hx.template.demo.BannerActivity;
import com.hx.template.demo.CreateQrcodeActivity;
import com.hx.template.demo.CropperDemoActivity;
import com.hx.template.demo.PullToRefreshActivity;
import com.hx.template.zxing.activity.CaptureActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    @Bind(R.id.image)
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called with: " + "savedInstanceState = [" + savedInstanceState + "]");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ImageLoader.getInstance().displayImage("http://f.hiphotos.baidu.com/image/pic/item/3ac79f3df8dcd10097cf5921708b4710b9122f5a.jpg", image, CustomApplication.defaultOptions, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                Log.d(TAG, "onLoadingStarted() called with: " + "s = [" + s + "], view = [" + view + "]");
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                Log.d(TAG, "onLoadingFailed() called with: " + "s = [" + s + "], view = [" + view + "], failReason = [" + failReason + "]");
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                Log.d(TAG, "onLoadingComplete() called with: " + "s = [" + s + "], view = [" + view + "], bitmap = [" + bitmap + "]");
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                Log.d(TAG, "onLoadingCancelled() called with: " + "s = [" + s + "], view = [" + view + "]");
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String s, View view, int i, int i1) {
                Log.d(TAG, "onProgressUpdate() called with: " + "s = [" + s + "], view = [" + view + "], i = [" + i + "], i1 = [" + i1 + "]");
            }
        });
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
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_create_qrcode:
                Intent intent2 = new Intent(MainActivity.this, CreateQrcodeActivity.class);
                startActivity(intent2);
                return true;
            case R.id.action_banner:
                Intent intent3 = new Intent(MainActivity.this, BannerActivity.class);
                startActivity(intent3);
                return true;
            case R.id.action_ptr:
                Intent intent4 = new Intent(MainActivity.this, PullToRefreshActivity.class);
                startActivity(intent4);
                return true;
            case R.id.action_cropper:
                Intent intent5 = new Intent(MainActivity.this, CropperDemoActivity.class);
                startActivity(intent5);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
