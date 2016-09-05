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
import com.hx.template.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心
 */
public class PersonalCenterFragment extends BaseFragment {

    @Bind(R.id.avatar)
    CircleImageView avatar;
    @Bind(R.id.nickname)
    TextView nickname;
    @Bind(R.id.username)
    TextView username;
    @Bind(R.id.qrcode)
    ImageView qrcode;

    @Override
    protected String getFragmentTitle() {
        return "个人中心";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_center, null);
        ButterKnife.bind(this, view);
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
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.settings, R.id.qrcode, R.id.personal_into_layout})
    public void onClick(View view) {
        if (!FastClickUtils.isTimeToProcess(view.getId())) {
            return;
        }
        switch (view.getId()) {
            case R.id.personal_into_layout:
                final Pair<View, String>[] pairs = new Pair[]{new Pair(avatar, "avatar"), new Pair(nickname, "nickname"), new Pair(username, "username"), new Pair(qrcode, "qrcode")};
                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairs);
                startActivity(new Intent(getContext(), PersonalInfoActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP), transitionActivityOptions.toBundle());
                break;
            case R.id.qrcode:
                break;
            case R.id.settings:
                startActivity(new Intent(getContext(), SettingActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
        }
    }
}
