/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.hx.template.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.allenliu.badgeview.BadgeFactory;
import com.allenliu.badgeview.BadgeView;
import com.hx.easemob.db.InviteMessgeDao;
import com.hx.template.R;
import com.hx.template.annotation.SaveInstanceAnnotation;
import com.hx.template.base.BaseActivity;
import com.hx.template.event.UnReadMsgChangeEvent;
import com.hx.template.ui.fragment.ContactListFragment;
import com.hx.template.ui.fragment.ConversationListFragment;
import com.hx.template.ui.fragment.FavoriteFragment;
import com.hx.template.ui.fragment.HomeFragment;
import com.hx.template.ui.fragment.PersonalCenterFragment;
import com.hx.template.widget.MainTabItemView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import me.leolin.shortcutbadger.ShortcutBadger;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    public static String PAGE_INDEX = "pageIndex";

    Toolbar toolbar;

    FrameLayout container;

    MainTabItemView mainMenuItem1;

    MainTabItemView mainMenuItem2;

    MainTabItemView mainMenuItem3;

    MainTabItemView mainMenuItem4;

    private FragmentManager mFragmentManager;

    private Fragment currentFragment;
    @SaveInstanceAnnotation
    private int currentIndex;

    private BadgeView unReadChat;
    private BadgeView unReadInvite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        container = (FrameLayout) findViewById(R.id.container);
        mainMenuItem1 = (MainTabItemView) findViewById(R.id.main_menu_item_1);
        mainMenuItem2 = (MainTabItemView) findViewById(R.id.main_menu_item_2);
        mainMenuItem3 = (MainTabItemView) findViewById(R.id.main_menu_item_3);
        mainMenuItem4 = (MainTabItemView) findViewById(R.id.main_menu_item_4);
        mainMenuItem1.setOnClickListener(this);
        mainMenuItem2.setOnClickListener(this);
        mainMenuItem3.setOnClickListener(this);
        mainMenuItem4.setOnClickListener(this);
        setSupportActionBar(toolbar);
        mFragmentManager = getSupportFragmentManager();
        initViews();
        if (savedInstanceState == null) {
            currentIndex = getIntent().getIntExtra(PAGE_INDEX, 0);
        }
        switchPage(currentIndex);
        EventBus.getDefault().register(this);
        refreshViews();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void refreshViews() {
        int unreadChatMsgCount = 0;
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        for (EMConversation emConversation : conversations.values()) {
            unreadChatMsgCount += emConversation.getUnreadMsgCount();
        }
        ShortcutBadger.applyCount(getApplicationContext(), unreadChatMsgCount); //for 1.1.4+
        if (unReadChat == null) {
            unReadChat = BadgeFactory.createCircle(this);
        }
        if (unreadChatMsgCount > 0) {
            unReadChat.setBadgeCount(unreadChatMsgCount);
            unReadChat.bind(mainMenuItem1.getImage());
        } else {
            unReadChat.unbind();
        }
        int unreadInviteMsgCount = 0;
        InviteMessgeDao dao = new InviteMessgeDao(this);
        unreadInviteMsgCount = dao.getUnreadMessagesCount();
        if (unReadInvite == null) {
            unReadInvite = BadgeFactory.createDot(this);
        }
        if (unreadInviteMsgCount > 0) {
            unReadInvite.bind(mainMenuItem4.getImage());
        } else {
            unReadInvite.unbind();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UnReadMsgChangeEvent event) {
        refreshViews();
    }

    private void initViews() {
        mainMenuItem1.setImageResNormal(R.drawable.icon_home);
        mainMenuItem1.setImageResSelected(R.drawable.icon_home);
        mainMenuItem1.setColorNormal(R.color.main_menu_color_normal);
        mainMenuItem1.setColorSelected(R.color.main_menu_color_selected);
        mainMenuItem2.setImageResNormal(R.drawable.icon_group);
        mainMenuItem2.setImageResSelected(R.drawable.icon_group);
        mainMenuItem2.setColorNormal(R.color.main_menu_color_normal);
        mainMenuItem2.setColorSelected(R.color.main_menu_color_selected);
        mainMenuItem3.setImageResNormal(R.drawable.icon_favorite);
        mainMenuItem3.setImageResSelected(R.drawable.icon_favorite);
        mainMenuItem3.setColorNormal(R.color.main_menu_color_normal);
        mainMenuItem3.setColorSelected(R.color.main_menu_color_selected);
        mainMenuItem4.setImageResNormal(R.drawable.icon_user);
        mainMenuItem4.setImageResSelected(R.drawable.icon_user);
        mainMenuItem4.setColorNormal(R.color.main_menu_color_normal);
        mainMenuItem4.setColorSelected(R.color.main_menu_color_selected);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_menu_item_1:
                switchPage(0);
                break;
            case R.id.main_menu_item_2:
                switchPage(1);
                break;
            case R.id.main_menu_item_3:
                switchPage(2);
                break;
            case R.id.main_menu_item_4:
                switchPage(3);
                break;
        }
    }

    private void switchPage(int pageIndex) {
        mainMenuItem1.setSelected(false);
        mainMenuItem2.setSelected(false);
        mainMenuItem3.setSelected(false);
        mainMenuItem4.setSelected(false);
        switch (pageIndex) {
            case 0:
                mainMenuItem1.setSelected(true);
                Fragment fragment1 = mFragmentManager
                        .findFragmentByTag("mainMenuItem1");
                if (fragment1 == null) {
                    fragment1 = new ConversationListFragment();
                }
                switchContent(currentFragment, fragment1, "mainMenuItem1");
                currentIndex = 0;
                break;
            case 1:
                mainMenuItem2.setSelected(true);
                Fragment fragment2 = mFragmentManager
                        .findFragmentByTag("mainMenuItem2");
                if (fragment2 == null) {
                    fragment2 = new ContactListFragment();

                }
                switchContent(currentFragment, fragment2, "mainMenuItem2");
                currentIndex = 1;
                break;
            case 2:
                mainMenuItem3.setSelected(true);
                Fragment fragment3 = mFragmentManager
                        .findFragmentByTag("mainMenuItem3");
                if (fragment3 == null) {
                    fragment3 = new FavoriteFragment();
                }
                switchContent(currentFragment, fragment3, "mainMenuItem3");
                currentIndex = 2;
                break;
            case 3:
                mainMenuItem4.setSelected(true);
                mainMenuItem4.setSelected(true);
                Fragment fragment4 = mFragmentManager
                        .findFragmentByTag("mainMenuItem4");
                if (fragment4 == null) {
                    fragment4 = new PersonalCenterFragment();
                }
                switchContent(currentFragment, fragment4, "mainMenuItem4");
                currentIndex = 3;
                break;
            default:
                mainMenuItem1.setSelected(true);
                mainMenuItem1.setSelected(true);
                Fragment fragment = mFragmentManager
                        .findFragmentByTag("mainMenuItem1");
                if (fragment == null) {
                    fragment = new HomeFragment();
                }
                switchContent(currentFragment, fragment, "mainMenuItem1");
                currentIndex = 0;
                break;
        }
    }


    public void switchContent(Fragment from, Fragment to, String toTag) {
        if (from != to) {
            FragmentTransaction transaction = mFragmentManager
                    .beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                if (from != null) {
                    transaction.hide(from);
                }
                transaction.add(R.id.container, to, toTag).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                if (from != null) {
                    transaction.hide(from);
                }
                transaction.show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
            currentFragment = to;
        }
    }
}
