package com.hx.template.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.hx.template.base.BaseActivity;
import com.hx.template.R;
import com.hx.template.global.HXLog;
import com.hx.template.qrcode.utils.ImageScanUtil;
import com.hx.template.utils.ImageUtils;
import com.hx.template.utils.ToastUtils;



import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxJavaActivity extends BaseActivity {


    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        image = (ImageView) findViewById(R.id.image);


        Observable.just("huang").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                ToastUtils.showToast(getApplicationContext(), s);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rxjava, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.action_select_image:
                intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                );
                intent.setType("image/*");
                this.startActivityForResult(intent, 1);
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



            Observable.just(uri)
                    .map(new Func1<Uri, Bitmap>() { //加载图片
                        @Override
                        public Bitmap call(Uri uri) {
                            Bitmap bitmap = ImageUtils.getImage(getApplicationContext(), uri);
                            String result = ImageScanUtil.decodeByZXing(bitmap);
                            HXLog.e("result = "+result);
                            return bitmap;
                        }
                    })
                    .subscribeOn(Schedulers.io())//加载图片放到io线程中
                    .observeOn(AndroidSchedulers.mainThread())//显示图片放到主线程中
                    .subscribe(new Action1<Bitmap>() {//显示图片
                        @Override
                        public void call(Bitmap bitmap) {
                            image.setImageBitmap(bitmap);
                        }
                    });
        }
    }
}
