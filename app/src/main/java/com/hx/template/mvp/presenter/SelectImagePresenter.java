package com.hx.template.mvp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.hx.template.mvp.contract.SelectImageView;
import com.hx.template.mvp.BasePresenter;
import com.hx.template.mvp.contract.ISelectImagePresenter;

import java.io.File;

import cn.huangx.common.listener.OnActivityResultListener;
import cn.huangx.common.utils.FileUtils;

/**
 * Created by huangx on 2016/8/10.
 */
public class SelectImagePresenter extends BasePresenter<SelectImageView> implements ISelectImagePresenter, OnActivityResultListener {

    private String cameraOutPath;

    private static final int REQUEST_CODE_CAMERA = 10101;
    private static final int REQUEST_CODE_GALLERY = 10102;

    @Override
    public void takeFromCamera(String outPath) {
        cameraOutPath = outPath;
        if (FileUtils.makeDirs(outPath)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(outPath)));
            if (isViewAttached()) {
                getMvpView().startActivityForResultDelegate(intent, REQUEST_CODE_CAMERA);
            }
        }
    }

    @Override
    public void takeFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        if (isViewAttached()) {
            getMvpView().startActivityForResultDelegate(intent, REQUEST_CODE_GALLERY);
        }
    }

    @Override
    public void onActivityResultDelegate(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CAMERA:
                    Uri cameraUri = Uri.fromFile(new File(cameraOutPath));
                    if (isViewAttached()) {
                        getMvpView().selectResult(cameraUri);
                    }
                    break;
                case REQUEST_CODE_GALLERY:
                    if (data != null) {
                        Uri uri = data.getData();
                        if (isViewAttached()) {
                            getMvpView().selectResult(uri);
                        }
                    }
                    break;
            }
        }
    }
}
