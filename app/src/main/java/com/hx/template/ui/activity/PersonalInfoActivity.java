package com.hx.template.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.Loader;
import android.support.v4.util.Pair;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hx.imageloader.ImageLoaderManager;
import com.hx.mvp.presenter.Presenter;
import com.hx.mvp.presenter.PresenterFactory;
import com.hx.mvp.presenter.PresenterLoader;
import com.hx.template.CustomApplication;
import com.hx.template.R;
import com.hx.template.base.BaseMvpActivity;
import com.hx.template.dagger2.ComponentHolder;
import com.hx.template.entity.User;
import com.hx.template.entity.enums.Gender;
import com.hx.template.event.UserInfoUpdateEvent;
import com.hx.template.global.FastClickUtils;
import com.hx.template.mvp.contract.PersonalInfoContract;
import com.hx.template.mvp.presenter.PersonalInfoPresenter;
import com.hx.template.utils.ActivityOptionsHelper;
import com.hx.template.utils.StringUtils;
import com.hx.template.utils.ToastUtils;
import com.hx.template.utils.UriUtils;
import com.hx.template.widget.CircleImageView;
import com.soundcloud.android.crop.Crop;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import id.zelory.compressor.Compressor;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class PersonalInfoActivity extends BaseMvpActivity<PersonalInfoPresenter, PersonalInfoContract.View> implements PersonalInfoContract.View, View.OnClickListener {

    private static final int REQUEST_CODE_SELECT_IMAGE = 101;
    private static final int REQUEST_CODE_CROP_IMAGE = 102;

    private static final int RC_CAMERA_AND_STORAGE = 201;

    Toolbar toolbar;

    private Handler handler = new Handler();
    private CircleImageView avatar;
    private RelativeLayout avatarLayout;
    private TextView nickname;
    private RelativeLayout nicknameLayout;
    private TextView username;
    private RelativeLayout usernameLayout;
    private TextView mobilePhone;
    private RelativeLayout mobilePhoneLayout;
    private TextView email;
    private RelativeLayout emailLayout;
    private ImageView qrcode;
    private RelativeLayout qrcodeLayout;
    private TextView gender;
    private RelativeLayout genderLayout;

    @Override
    public Loader<PersonalInfoPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(this, new PresenterFactory() {
            @Override
            public Presenter create() {
                return ComponentHolder.getAppComponent().personalInfoPresenter();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        initView();
        setTitle(getString(R.string.personal_info_title));
        EventBus.getDefault().register(this);
        refreshViews();
    }

    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        avatar = (CircleImageView) findViewById(R.id.avatar);
        avatarLayout = (RelativeLayout) findViewById(R.id.avatar_layout);
        nickname = (TextView) findViewById(R.id.nickname);
        nicknameLayout = (RelativeLayout) findViewById(R.id.nickname_layout);
        username = (TextView) findViewById(R.id.username);
        usernameLayout = (RelativeLayout) findViewById(R.id.username_layout);
        mobilePhone = (TextView) findViewById(R.id.mobile_phone);
        mobilePhoneLayout = (RelativeLayout) findViewById(R.id.mobile_phone_layout);
        email = (TextView) findViewById(R.id.email);
        emailLayout = (RelativeLayout) findViewById(R.id.email_layout);
        qrcode = (ImageView) findViewById(R.id.qrcode);
        qrcodeLayout = (RelativeLayout) findViewById(R.id.qrcode_layout);
        gender = (TextView) findViewById(R.id.gender);
        genderLayout = (RelativeLayout) findViewById(R.id.gender_layout);

        avatar.setOnClickListener(this);
        avatarLayout.setOnClickListener(this);
        nicknameLayout.setOnClickListener(this);
        usernameLayout.setOnClickListener(this);
        mobilePhoneLayout.setOnClickListener(this);
        emailLayout.setOnClickListener(this);
        qrcodeLayout.setOnClickListener(this);
        genderLayout.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserInfoUpdateEvent event) {
        refreshViews();
    }

    private void refreshViews() {
        User currentUser = User.getCurrentUser(User.class);
        if (currentUser != null) {
            nickname.setText(StringUtils.nullStrToEmpty(currentUser.getNickname()));
            username.setText(StringUtils.nullStrToEmpty(currentUser.getUsername()));
            mobilePhone.setText(StringUtils.nullStrToEmpty(currentUser.getMobilePhoneNumber()));
            email.setText(StringUtils.nullStrToEmpty(currentUser.getEmail()));
            String avatarUrl = currentUser.getAvatar();
            if (avatarUrl != null) {
                ImageLoaderManager.getImageLoader(this).displayImage(avatarUrl, avatar, R.drawable.default_avatar, R.drawable.default_avatar, 0);
            }
            Gender genderObj = Gender.getInstanceByCode(currentUser.getGender());
            gender.setText(StringUtils.nullStrToEmpty(genderObj == null ? "" : genderObj.getValue()));
        }
    }

    public void onClick(View view) {
        if (!FastClickUtils.isTimeToProcess(view.getId())) {
            return;
        }
        switch (view.getId()) {
            case R.id.avatar:
                break;
            case R.id.avatar_layout:
                selectImage();
                break;
            case R.id.nickname_layout:
                startActivity(new Intent(PersonalInfoActivity.this, PersonalInfoUpdateActivity.class).putExtra(User.INFO_TYPE, User.INFO_TYPE_NICKNAME).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.username_layout:
                break;
            case R.id.qrcode_layout:
                startActivity(new Intent(PersonalInfoActivity.this, QrcodeCardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP),
                        ActivityOptionsHelper.makeSceneTransitionAnimationBundle(PersonalInfoActivity.this, true, new Pair(avatar, "avatar"), new Pair(nickname, "nickname"), new Pair(qrcode, "qrcode")));
                break;
        }
    }

    @AfterPermissionGranted(RC_CAMERA_AND_STORAGE)
    public void selectImage() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            MultiImageSelector.create().count(1).single().showCamera(true).start(this, REQUEST_CODE_SELECT_IMAGE);
        } else {
            EasyPermissions.requestPermissions(this, "要更换头像必须要读SDK权限、打开摄像头权限",
                    RC_CAMERA_AND_STORAGE, perms);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_SELECT_IMAGE) {
                if (data != null) {
                    List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    if (path != null && path.size() > 0) {
                        beginCrop(Uri.fromFile(new File(path.get(0))));
                    }
                }
            }
            if (requestCode == Crop.REQUEST_CROP) {
                if (data != null) {
                    handleCrop(resultCode, data);
                }
            }
        }
    }

    /**
     * 开始裁剪
     *
     * @param source
     */
    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    /**
     * 裁剪完成
     *
     * @param resultCode
     * @param result
     */
    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Uri output = Crop.getOutput(result);
            String path = UriUtils.getPath(getApplicationContext(), output);
            File compressedImageFile = new Compressor.Builder(this)
                    .setMaxWidth(480)
                    .setMaxHeight(640)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .build().compressToFile(new File(path));
            User currentUser = User.getCurrentUser(User.class);
            if (currentUser != null) {
                presenter.updateAvatar(currentUser.getObjectId(), compressedImageFile);
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            ToastUtils.showToast(this, Crop.getError(result).getMessage());
        }
    }

    /**
     * 头像修改成功
     */
    @Override
    public void avatarUpdateSuccess() {
        CustomApplication.reloadUserInfo();
    }

    /**
     * 头像修改失败
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    @Override
    public void avatarUpdateFail(String errorCode, String errorMsg) {
        ToastUtils.showToast(this, StringUtils.nullStrToEmpty(errorMsg));
    }


}
