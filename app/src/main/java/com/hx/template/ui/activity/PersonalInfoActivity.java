package com.hx.template.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.Loader;
import android.support.v4.util.Pair;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hx.template.R;
import com.hx.template.base.BaseActivity;
import com.hx.template.widget.CircleImageView;
import com.hx.template.dagger2.ComponentHolder;
import com.hx.template.entity.User;
import com.hx.template.entity.enums.Gender;
import com.hx.template.event.UserInfoUpdateEvent;
import com.hx.template.global.FastClickUtils;
import com.hx.template.imageloader.ImageLoaderManager;
import com.hx.template.mvp.Presenter;
import com.hx.template.mvp.PresenterFactory;
import com.hx.template.mvp.PresenterLoader;
import com.hx.template.mvp.contract.PersonalInfoContract;
import com.hx.template.mvp.presenter.PersonalInfoPresenter;
import com.hx.template.utils.ActivityOptionsHelper;
import com.hx.template.utils.StringUtils;
import com.hx.template.utils.ToastUtils;
import com.hx.template.utils.UriUtils;
import com.soundcloud.android.crop.Crop;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class PersonalInfoActivity extends BaseActivity<PersonalInfoPresenter, PersonalInfoContract.View> implements PersonalInfoContract.View {

    private static final int REQUEST_CODE_SELECT_IMAGE = 101;
    private static final int REQUEST_CODE_CROP_IMAGE = 102;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.avatar)
    CircleImageView avatar;
    @Bind(R.id.nickname)
    TextView nickname;
    @Bind(R.id.username)
    TextView username;
    @Bind(R.id.mobile_phone)
    TextView mobilePhone;
    @Bind(R.id.email)
    TextView email;
    @Bind(R.id.gender)
    TextView gender;
    @Bind(R.id.qrcode)
    ImageView qrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("个人信息");
        EventBus.getDefault().register(this);
        refreshViews();
    }

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

    @OnClick({R.id.avatar, R.id.avatar_layout, R.id.nickname_layout, R.id.username_layout, R.id.qrcode_layout, R.id.gender_layout})
    public void onClick(View view) {
        if (!FastClickUtils.isTimeToProcess(view.getId())) {
            return;
        }
        switch (view.getId()) {
            case R.id.avatar:
                break;
            case R.id.avatar_layout:
                MultiImageSelector.create().count(1).single().showCamera(true).start(this, REQUEST_CODE_SELECT_IMAGE);
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
            case R.id.gender_layout:
                break;
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
        EventBus.getDefault().post(new UserInfoUpdateEvent());
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
