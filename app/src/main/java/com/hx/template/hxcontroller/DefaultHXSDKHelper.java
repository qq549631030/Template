package com.hx.template.hxcontroller;

import android.content.Intent;
import android.content.IntentFilter;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.easemob.easeui.controller.EaseUI;
import com.easemob.easeui.domain.EaseUser;
import com.easemob.easeui.model.EaseNotifier;
import com.easemob.easeui.utils.EaseCommonUtils;

import java.util.List;

/**
 * Created by huangxiang on 2016/11/24.
 */

public class DefaultHXSDKHelper extends HXSDKHelper {

    private CallReceiver callReceiver;

    @Override
    protected HXSDKModel createModel() {
        return null;
    }

    /**
     * 注册通话广播接收者
     */
    @Override
    protected void registerCallReceiver() {
        IntentFilter callFilter = new IntentFilter(EMChatManager.getInstance().getIncomingCallBroadcastAction());
        if (callReceiver == null) {
            callReceiver = new CallReceiver();
        }
        appContext.registerReceiver(callReceiver, callFilter);
    }

    @Override
    protected void setEaseUIProviders() {
        super.setEaseUIProviders();

        easeUI.setSettingsProvider(new EaseUI.EaseSettingsProvider() {

            @Override
            public boolean isSpeakerOpened() {
                return hxModel.getSettingMsgSpeaker();
            }

            @Override
            public boolean isMsgVibrateAllowed(EMMessage message) {
                return hxModel.getSettingMsgVibrate();
            }

            @Override
            public boolean isMsgSoundAllowed(EMMessage message) {
                return hxModel.getSettingMsgSound();
            }

            @Override
            public boolean isMsgNotifyAllowed(EMMessage message) {
                if (message == null) {
                    return hxModel.getSettingMsgNotification();
                }
                if (!hxModel.getSettingMsgNotification()) {
                    return false;
                } else {
                    //如果允许新消息提示
                    //屏蔽的用户和群组不提示用户
                    String chatUsename = null;
                    List<String> notNotifyIds = null;
                    // 获取设置的不提示新消息的用户或者群组ids
                    if (message.getChatType() == EMMessage.ChatType.Chat) {
                        chatUsename = message.getFrom();
                        notNotifyIds = hxModel.getDisabledIds();
                    } else {
                        chatUsename = message.getTo();
                        notNotifyIds = hxModel.getDisabledGroups();
                    }

                    if (notNotifyIds == null || !notNotifyIds.contains(chatUsename)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        });


        //设置表情provider
//        easeUI.setEmojiconInfoProvider(new EaseEmojiconInfoProvider() {
//
//            @Override
//            public EaseEmojicon getEmojiconInfo(String emojiconIdentityCode) {
//                EaseEmojiconGroupEntity data = EmojiconExampleGroupData.getData();
//                for(EaseEmojicon emojicon : data.getEmojiconList()){
//                    if(emojicon.getIdentityCode().equals(emojiconIdentityCode)){
//                        return emojicon;
//                    }
//                }
//                return null;
//            }
//
//            @Override
//            public Map<String, Object> getTextEmojiconMapping() {
//                //返回文字表情emoji文本和图片(resource id或者本地路径)的映射map
//                return null;
//            }
//        });

        easeUI.getNotifier().setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {

            @Override
            public String getTitle(EMMessage message) {
                //修改标题,这里使用默认
                return null;
            }

            @Override
            public int getSmallIcon(EMMessage message) {
                //设置小图标，这里为默认
                return 0;
            }

            @Override
            public String getDisplayedText(EMMessage message) {
                // 设置状态栏的消息提示，可以根据message的类型做相应提示
                String ticker = EaseCommonUtils.getMessageDigest(message, appContext);
                if (message.getType() == EMMessage.Type.TXT) {
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                }
                EaseUser user = getUserInfo(message.getFrom());
                if (user != null) {
                    return getUserInfo(message.getFrom()).getNick() + ": " + ticker;
                } else {
                    return message.getFrom() + ": " + ticker;
                }
            }

            @Override
            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                return null;
                // return fromUsersNum + "个基友，发来了" + messageNum + "条消息";
            }

            @Override
            public Intent getLaunchIntent(EMMessage message) {
//                //设置点击通知栏跳转事件
//                Intent intent = new Intent(appContext, ChatActivity.class);
//                //有电话时优先跳转到通话页面
//                if(isVideoCalling){
//                    intent = new Intent(appContext, VideoCallActivity.class);
//                }else if(isVoiceCalling){
//                    intent = new Intent(appContext, VoiceCallActivity.class);
//                }else{
//                    ChatType chatType = message.getChatType();
//                    if (chatType == ChatType.Chat) { // 单聊信息
//                        intent.putExtra("userId", message.getFrom());
//                        intent.putExtra("chatType", HXConstant.CHATTYPE_SINGLE);
//                    } else { // 群聊信息
//                        // message.getTo()为群聊id
//                        intent.putExtra("userId", message.getTo());
//                        if(chatType == ChatType.GroupChat){
//                            intent.putExtra("chatType", HXConstant.CHATTYPE_GROUP);
//                        }else{
//                            intent.putExtra("chatType", HXConstant.CHATTYPE_CHATROOM);
//                        }
//
//                    }
//                }
//                return intent;
                return new Intent();
            }
        });
    }
}
