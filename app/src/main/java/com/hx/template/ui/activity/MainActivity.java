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

import com.hx.template.R;
import com.hx.template.base.BaseActivity;
import com.hx.template.components.MainTabItemView;
import com.hx.template.ui.fragment.FavoriteFragment;
import com.hx.template.ui.fragment.GroupFragment;
import com.hx.template.ui.fragment.HomeFragment;
import com.hx.template.ui.fragment.PersonalCenterFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    public static String PAGE_INDEX = "pageIndex";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.container)
    FrameLayout container;
    @Bind(R.id.main_menu_item_1)
    MainTabItemView mainMenuItem1;
    @Bind(R.id.main_menu_item_2)
    MainTabItemView mainMenuItem2;
    @Bind(R.id.main_menu_item_3)
    MainTabItemView mainMenuItem3;
    @Bind(R.id.main_menu_item_4)
    MainTabItemView mainMenuItem4;

    private FragmentManager mFragmentManager;

    private Fragment currentFragment;

    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        mFragmentManager = getSupportFragmentManager();
        initViews();
        currentIndex = getIntent().getIntExtra(PAGE_INDEX, 0);
        switchPage(currentIndex);
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

    @OnClick({R.id.main_menu_item_1, R.id.main_menu_item_2, R.id.main_menu_item_3, R.id.main_menu_item_4})
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
                    fragment1 = new HomeFragment();
                }
                switchContent(currentFragment, fragment1, "mainMenuItem1");
                currentIndex = 0;
                break;
            case 1:
                mainMenuItem2.setSelected(true);
                Fragment fragment2 = mFragmentManager
                        .findFragmentByTag("mainMenuItem2");
                if (fragment2 == null) {
                    fragment2 = new GroupFragment();
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
