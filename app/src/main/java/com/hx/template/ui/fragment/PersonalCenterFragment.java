package com.hx.template.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hx.easemob.db.InviteMessgeDao;
import com.hx.imageloader.ImageLoaderManager;
import com.hx.template.R;
import com.hx.template.base.BaseFragment;
import com.hx.template.entity.User;
import com.hx.template.event.UnReadMsgChangeEvent;
import com.hx.template.event.UserInfoUpdateEvent;
import com.hx.template.global.FastClickUtils;
import com.hx.template.ui.activity.NewFriendsMsgActivity;
import com.hx.template.ui.activity.PersonalInfoActivity;
import com.hx.template.ui.activity.SettingActivity;
import com.hx.template.utils.ActivityOptionsHelper;
import com.hx.template.utils.StringUtils;
import com.hx.template.widget.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 个人中心
 */
public class PersonalCenterFragment extends BaseFragment implements View.OnClickListener {


    private CircleImageView avatar;
    private TextView nickname;
    private TextView username;
    private ImageView qrcode;
    private RelativeLayout personal_info_layout;
    private TextView settings;

    private InviteMessgeDao inviteMessgeDao;

    @Override
    protected String getFragmentTitle() {
        return getString(R.string.personal_center_title);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        inviteMessgeDao = new InviteMessgeDao(activity);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.message);
        int unReadCount = inviteMessgeDao.getUnreadMessagesCount();
        if (unReadCount > 0) {
            menuItem.setIcon(R.drawable.icon_msg_unread);
        } else {
            menuItem.setIcon(R.drawable.icon_msg_read);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_personal_center, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.message:
                Intent intent = new Intent(getContext(), NewFriendsMsgActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_center, null);
        initView(view);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UnReadMsgChangeEvent event) {
        if (isViewCreated) {
            getActivity().invalidateOptionsMenu();
        }
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();

    }


    public void onClick(View view) {
        if (!FastClickUtils.isTimeToProcess(view.getId())) {
            return;
        }
        switch (view.getId()) {
            case R.id.personal_info_layout:
                startActivity(new Intent(getContext(), PersonalInfoActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP), ActivityOptionsHelper.makeSceneTransitionAnimationBundle(getActivity(), true, new Pair(avatar, "avatar"), new Pair(nickname, "nickname"), new Pair(username, "username"), new Pair(qrcode, "qrcode")));
                break;
            case R.id.qrcode:
                break;
            case R.id.settings:
                startActivity(new Intent(getContext(), SettingActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
        }
    }

    private void initView(View view) {
        avatar = (CircleImageView) view.findViewById(R.id.avatar);
        nickname = (TextView) view.findViewById(R.id.nickname);
        username = (TextView) view.findViewById(R.id.username);
        qrcode = (ImageView) view.findViewById(R.id.qrcode);
        personal_info_layout = (RelativeLayout) view.findViewById(R.id.personal_info_layout);
        settings = (TextView) view.findViewById(R.id.settings);

        qrcode.setOnClickListener(this);
        personal_info_layout.setOnClickListener(this);
        settings.setOnClickListener(this);
    }
}
