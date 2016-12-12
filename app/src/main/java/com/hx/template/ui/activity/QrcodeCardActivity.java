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



import cn.bmob.v3.datatype.BmobFile;

public class QrcodeCardActivity extends BaseActivity {


    Toolbar toolbar;

    ImageView avatar;

    TextView nickname;

    TextView address;

    ImageView qrcode;

    TextView tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_card);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        avatar = (ImageView) findViewById(R.id.avatar);
        nickname = (TextView) findViewById(R.id.nickname);
        address = (TextView) findViewById(R.id.address);
        qrcode = (ImageView) findViewById(R.id.qrcode);
        tips = (TextView) findViewById(R.id.tips);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.qrcode_card_title));
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
