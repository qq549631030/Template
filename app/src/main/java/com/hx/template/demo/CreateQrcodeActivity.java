package com.hx.template.demo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.hx.template.base.BaseActivity;
import com.hx.template.R;
import com.hx.template.qrcode.utils.QRCodeCreateUtil;
import com.hx.template.utils.ImageUtils;

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
      Bitmap qrcode = QRCodeCreateUtil.createQRImage(url,QR_WIDTH,QR_HEIGHT);
        if (qrcode != null) {
            image.setImageBitmap(qrcode);
        }
    }

}
