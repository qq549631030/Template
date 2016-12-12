package com.hx.template.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hx.template.R;
import com.hx.template.base.BaseFragment;
import com.hx.template.widget.CircleImageView;
import com.hx.template.entity.User;
import com.hx.template.event.UserInfoUpdateEvent;
import com.hx.template.global.FastClickUtils;
import com.hx.template.imageloader.ImageLoaderManager;
import com.hx.template.ui.activity.PersonalInfoActivity;
import com.hx.template.ui.activity.SettingActivity;
import com.hx.template.utils.ActivityOptionsHelper;
import com.hx.template.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;



import butterknife.OnClick;

/**
 * 个人中心
 */
public class PersonalCenterFragment extends BaseFragment {


    CircleImageView avatar;

    TextView nickname;

    TextView username;

    ImageView qrcode;

    @Override
    protected String getFragmentTitle() {
        return "个人中心";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_center, null);
        qrcode = (ImageView) view.findViewById(R.id.qrcode);
        username = (TextView) view.findViewById(R.id.username);
        nickname = (TextView) view.findViewById(R.id.nickname);
        avatar = (CircleImageView) view.findViewById(R.id.avatar);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        refreshViews();
    }

    private void refreshViews() {
        User currentUser = User.getCurrentUser(User.class);
        if (currentUser != null) {
            nickname.setText(StringUtils.nullStrToEmpty(currentUser.getNickname()));
            username.setText(StringUtils.nullStrToEmpty(currentUser.getUsername()));
            String avatarUrl = currentUser.getAvatar();
            if (avatarUrl != null) {
                ImageLoaderManager.getImageLoader(getContext()).displayImage(avatarUrl, avatar, R.drawable.default_avatar, R.drawable.default_avatar, R.drawable.default_avatar);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserInfoUpdateEvent event) {
        if (isViewCreated) {
            refreshViews();
        }
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();

    }


    @OnClick({R.id.settings, R.id.qrcode, R.id.personal_into_layout})
    public void onClick(View view) {
        if (!FastClickUtils.isTimeToProcess(view.getId())) {
            return;
        }
        switch (view.getId()) {
            case R.id.personal_into_layout:
                startActivity(new Intent(getContext(), PersonalInfoActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP), ActivityOptionsHelper.makeSceneTransitionAnimationBundle(getActivity(), true, new Pair(avatar, "avatar"), new Pair(nickname, "nickname"), new Pair(username, "username"), new Pair(qrcode, "qrcode")));
                break;
            case R.id.qrcode:
                break;
            case R.id.settings:
                startActivity(new Intent(getContext(), SettingActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
        }
    }
}
