package com.hx.template.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.hx.template.R;
import com.hx.template.base.BaseActivity;
import com.hx.template.entity.User;
import com.hx.template.imageloader.ImageLoaderManager;
import com.hx.template.qrcode.utils.QRCodeCreateUtil;
import com.hx.template.utils.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.datatype.BmobFile;

public class QrcodeCardActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.avatar)
    ImageView avatar;
    @Bind(R.id.nickname)
    TextView nickname;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.qrcode)
    ImageView qrcode;
    @Bind(R.id.tips)
    TextView tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_card);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("二维码名片");
        refreshViews();
    }

    private void refreshViews() {
        User currentUser = User.getCurrentUser(User.class);
        if (currentUser != null) {
            nickname.setText(StringUtils.nullStrToEmpty(currentUser.getNickname()));
            String avatarUrl = currentUser.getAvatar();
            if (avatarUrl != null) {
                ImageLoaderManager.getImageLoader(this).displayImage(avatarUrl, avatar, R.drawable.default_avatar, R.drawable.default_avatar, R.drawable.default_avatar);
            }
            qrcode.setImageBitmap(QRCodeCreateUtil.createQRImage("null", 600, 600));
        }
    }

}
