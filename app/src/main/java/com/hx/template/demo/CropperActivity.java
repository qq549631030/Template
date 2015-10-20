package com.hx.template.demo;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.edmodo.cropper.CropImageView;
import com.hx.template.Constant;
import com.hx.template.R;
import com.hx.template.utils.ClickUtils;
import com.hx.template.utils.ImageUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CropperActivity extends AppCompatActivity {

    @Bind(R.id.cropImageView)
    CropImageView cropImageView;

    private Uri srcUri;

    private File cropedFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cropper);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        srcUri = getIntent().getData();
        File defaultFile = new File(getExternalCacheDir(), Constant.crop_image_name);
        String path = getIntent().getStringExtra("cropedFilePath");
        if (!TextUtils.isEmpty(path)) {
            cropedFile = new File(path);
        } else {
            cropedFile = defaultFile;
        }
        initViews();
    }

    private void initViews() {
        Bitmap bitmap = ImageUtils.getImage(this, srcUri);
        cropImageView.setImageBitmap(bitmap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cropper, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_confirm:
                if (ClickUtils.notFastClick()) {
                    Bitmap cropedBitmap = cropImageView.getCroppedImage();
                    try {
                        FileOutputStream outputStream = new FileOutputStream(cropedFile);
                        cropedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    setResult(RESULT_OK);
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
