package com.hx.template.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.hx.template.BaseActivity;
import com.hx.template.Constant;
import com.hx.template.R;
import com.hx.template.utils.ImageUtils;
import com.hx.template.utils.ToastUtils;
import com.hx.template.views.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CropperDemoActivity extends BaseActivity {

    private static final int REQUEST_GALLERY = 1;
    private static final int REQUEST_CAMERA = 2;
    private static final int REQUEST_CROP = 3;


    private static final int ITEM1 = Menu.FIRST;
    private static final int ITEM2 = Menu.FIRST + 1;
    private static final int ITEM3 = Menu.FIRST + 2;

    @Bind(R.id.image)
    CircleImageView image;


    private File cameraFile;

    private File cropedFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cropper_demo);
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
        registerForContextMenu(image);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //添加菜单项
        menu.add(0, ITEM1, 0, "相册");
        menu.add(0, ITEM2, 0, "拍照");
        menu.add(0, ITEM3, 0, "取消");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case ITEM1:
                doPickPhotoFromGallery();
                break;
            case ITEM2:
                doTakePhoto();
                break;
            case ITEM3:

                break;
        }
        return super.onContextItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_GALLERY:
                    if (data != null) {
                        Uri mUri = data.getData();
                        doCropPhoto(mUri);
                    }
                    break;
                case REQUEST_CAMERA:
                    Uri uri = Uri.fromFile(cameraFile);
                    doCropPhoto(uri);
                    break;
                case REQUEST_CROP:
                    updateAvatar(cropedFile);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image:
                openContextMenu(view);
                break;
        }
    }

    // 请求Gallery程序
    protected void doPickPhotoFromGallery() {
        // Launch picker to choose photo for selected contact
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    // 拍照获取图片
    protected void doTakePhoto() {
        try {
            // Launch camera to take photo for selected contact
            cameraFile = new File(getExternalCacheDir(), Constant.camera_image_name);
            cameraFile.createNewFile();
            if (cameraFile != null && cameraFile.exists()) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
                startActivityForResult(intent, REQUEST_CAMERA);
            } else {
                ToastUtils.showToast(getApplicationContext(), "存储错误");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 启动自定义剪辑器
    private void doCropPhoto(Uri uri) {
        try {
            initCropFile();
            Intent cropIntent = new Intent(CropperDemoActivity.this, CropperActivity.class);
            cropIntent.setData(uri);
            cropIntent.putExtra("cropedFilePath", cropedFile.getAbsolutePath());
            startActivityForResult(cropIntent, REQUEST_CROP);
        } catch (Exception e) {
        }
    }

    private void initCropFile() {
        cropedFile = new File(getExternalCacheDir(), Constant.crop_image_name);
        if (cropedFile.exists()) {
            cropedFile.delete();
        }
        try {
            cropedFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void updateAvatar(File avatar) {
        Uri uri = Uri.fromFile(avatar);
        ImageLoader.getInstance().displayImage(uri.toString(), image);
    }
}