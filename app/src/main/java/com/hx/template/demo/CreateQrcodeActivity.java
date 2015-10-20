package com.hx.template.demo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.hx.template.BaseActivity;
import com.hx.template.R;

import java.util.Hashtable;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateQrcodeActivity extends BaseActivity {

    private static final int QR_WIDTH = 300;
    private static final int QR_HEIGHT = 300;

    @Bind(R.id.editText)
    EditText editText;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_qrcode);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createQRImage(editText.getText().toString().trim());
            }
        });
    }


    public void createQRImage(String url) {
        try {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            //显示到一个ImageView上面
            image.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
