package com.hx.template.ui.fragment;

import android.content.Intent;
import android.view.View;

import com.hx.easemob.Constant;
import com.hx.easemob.domain.EmojiconExampleGroupData;
import com.hx.easemob.domain.RobotUser;
import com.hx.template.CustomSDKHelper;
import com.hx.template.ui.activity.MainActivity;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenu;
import com.hyphenate.util.EasyUtils;

import java.util.Map;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/21 19:58
 * 邮箱：huangx@pycredit.cn
 */

public class ChatFragment extends EaseChatFragment implements EaseChatFragment.EaseChatFragmentHelper {


    /**
     * if it is chatBot
     */
    private boolean isRobot;

    /**
     * init view
     */
    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void setUpView() {
        setChatFragmentListener(this);
        if (chatType == Constant.CHATTYPE_SINGLE) {
            Map<String, RobotUser> robotMap = CustomSDKHelper.getInstance().getRobotList();
            if (robotMap != null && robotMap.containsKey(toChatUsername)) {
                isRobot = true;
            }
        }
        super.setUpView();
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (EasyUtils.isSingleActivity(getActivity())) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
                onBackPressed();
            }
        });
        ((EaseEmojiconMenu) inputMenu.getEmojiconMenu()).addEmojiconGroup(EmojiconExampleGroupData.getData());
    }


    /**
     * set message attribute
     *
     * @param message
     */
    @Override
    public void onSetMessageAttributes(EMMessage message) {
        if (isRobot) {
            //set message extension
            message.setAttribute("em_robot_message", isRobot);
        }
    }

    /**
     * enter to chat detail
     */
    @Override
    public void onEnterToChatDetails() {

    }

    /**
     * on avatar clicked
     *
     * @param username
     */
    @Override
    public void onAvatarClick(String username) {

    }

    /**
     * on avatar long pressed
     *
     * @param username
     */
    @Override
    public void onAvatarLongClick(String username) {
        inputAtUsername(username);
    }

    /**
     * on message bubble clicked
     *
     * @param message
     */
    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        return false;
    }

    /**
     * on message bubble long pressed
     *
     * @param message
     */
    @Override
    public void onMessageBubbleLongClick(EMMessage message) {

    }

    /**
     * on extend menu item clicked, return true if you want to override
     *
     * @param itemId
     * @param view
     * @return
     */
    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        return false;
    }

    /**
     * on set custom chat row provider
     *
     * @return
     */
    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return null;
    }
}
