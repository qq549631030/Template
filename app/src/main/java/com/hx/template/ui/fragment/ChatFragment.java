package com.hx.template.ui.fragment;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.BaseAdapter;

import com.hx.easemob.Constant;
import com.hx.easemob.domain.EmojiconExampleGroupData;
import com.hx.easemob.domain.RobotUser;
import com.hx.template.CustomSDKHelper;
import com.hx.template.event.UnReadMsgChangeEvent;
import com.hx.template.ui.activity.MainActivity;
import com.hx.template.widget.ChatRowApplyStatus;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenu;
import com.hyphenate.util.EasyUtils;

import org.greenrobot.eventbus.EventBus;

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
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            inputMenu.getPrimaryMenu().getEditText().addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count == 1 && "@".equals(String.valueOf(s.charAt(start)))) {
//                        startActivityForResult(new Intent(getActivity(), PickAtUserActivity.class).
//                                putExtra("groupId", toChatUsername), REQUEST_CODE_SELECT_AT_USER);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }


    @Override
    protected void onConversationInit() {
        super.onConversationInit();
        EventBus.getDefault().post(new UnReadMsgChangeEvent());//打开聊天页面后，当前会话的未读消息数置0
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
        return new CustomChatRowProvider();
    }


    public class CustomChatRowProvider implements EaseCustomChatRowProvider {

        /**
         * 获取多少种类型的自定义chatrow<br/>
         * 注意，每一种chatrow至少有两种type：发送type和接收type
         *
         * @return
         */
        @Override
        public int getCustomChatRowTypeCount() {
            return 1;
        }

        /**
         * 获取chatrow type，必须大于0, 从1开始有序排列
         *
         * @param message
         * @return
         */
        @Override
        public int getCustomChatRowType(EMMessage message) {
            if (message.getType() == EMMessage.Type.TXT) {
                String customType = message.getStringAttribute("customType", "");
                if ("applyStatus".equals(customType)) {
                    return 1;
                }
            }
            return 0;
        }

        /**
         * 根据给定message返回chat row
         *
         * @param message
         * @param position
         * @param adapter
         * @return
         */
        @Override
        public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
            if (message.getType() == EMMessage.Type.TXT) {
                String customType = message.getStringAttribute("customType", "");
                if ("applyStatus".equals(customType)) {
                    return new ChatRowApplyStatus(getActivity(), message, position, adapter);
                }
            }
            return null;
        }
    }
}
