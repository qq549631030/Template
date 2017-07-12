package com.hx.template;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.hx.easemob.DefaultSDKHelper;
import com.hx.easemob.model.UserProfileManager;
import com.hx.template.event.UnReadMsgChangeEvent;
import com.hx.template.ui.activity.ChatActivity;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.util.EMLog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/20 20:36
 * 邮箱：549631030@qq.com
 */

public class CustomSDKHelper extends DefaultSDKHelper {
    @Override
    protected UserProfileManager createUserProfileManager() {
        return new CustomUserProfileManager();
    }

    @NonNull
    @Override
    protected EMMessageListener createEmMessageListener() {
        return new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    EMLog.d(TAG, "onMessageReceived id : " + message.getMsgId());
                    // in background, do not refresh UI, notify it in notification bar
                    if (!easeUI.hasForegroundActivies()) {
                        getNotifier().onNewMsg(message);
                    }
                }
                EventBus.getDefault().post(new UnReadMsgChangeEvent());
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    EMLog.d(TAG, "receive command message");
                    //get message body
                    EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
                    final String action = cmdMsgBody.action();//获取自定义action
                    //red packet code : 处理红包回执透传消息
                    if (!easeUI.hasForegroundActivies()) {
//                        if (action.equals(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION)){
//                            RedPacketUtil.receiveRedPacketAckMessage(message);
//                            broadcastManager.sendBroadcast(new Intent(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION));
//                        }
                    }

                    if (action.equals("__Call_ReqP2P_ConferencePattern")) {
                        String title = message.getStringAttribute("em_apns_ext", "conference call");
                        Toast.makeText(getAppContext(), title, Toast.LENGTH_LONG).show();
                    }
                    //end of red packet code
                    //获取扩展属性 此处省略
                    //maybe you need get extension of your message
                    //message.getStringAttribute("");
                    EMLog.d(TAG, String.format("Command：action:%s,message:%s", action, message.toString()));
                }
            }

            @Override
            public void onMessageReadAckReceived(List<EMMessage> messages) {

            }

            @Override
            public void onMessageDeliveryAckReceived(List<EMMessage> messages) {

            }

            @Override
            public void onMessageChanged(EMMessage emMessage, Object change) {

            }
        };
    }

    @NonNull
    @Override
    protected EaseNotifier.EaseNotificationInfoProvider createNotificationInfoProvider() {
        return new EaseNotifier.EaseNotificationInfoProvider() {
            @Override
            public String getDisplayedText(EMMessage message) {
                // be used on notification bar, different text according the message type.
                String ticker = EaseCommonUtils.getMessageDigest(message, getAppContext());
                if (message.getType() == EMMessage.Type.TXT) {
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                }
                EaseUser user = getUserInfo(message.getFrom());
                if (user != null) {
                    if (EaseAtMessageHelper.get().isAtMeMsg(message)) {
                        return String.format(getAppContext().getString(com.hx.easemob.R.string.at_your_in_group), user.getNick());
                    }
                    return user.getNick() + ": " + ticker;
                } else {
                    if (EaseAtMessageHelper.get().isAtMeMsg(message)) {
                        return String.format(getAppContext().getString(com.hx.easemob.R.string.at_your_in_group), message.getFrom());
                    }
                    return message.getFrom() + ": " + ticker;
                }
            }

            @Override
            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                return null;
            }

            @Override
            public String getTitle(EMMessage message) {
                return null;
            }

            @Override
            public int getSmallIcon(EMMessage message) {
                return 0;
            }

            @Override
            public Intent getLaunchIntent(EMMessage message) {
                // you can set what activity you want display when user click the notification
                Intent intent = new Intent(getAppContext(), ChatActivity.class);
                // open calling activity if there is call
                if (isVideoCalling) {
//                    intent = new Intent(getAppContext(), VideoCallActivity.class);
                } else if (isVoiceCalling) {
//                    intent = new Intent(getAppContext(), VoiceCallActivity.class);
                } else {
                    EMMessage.ChatType chatType = message.getChatType();
                    if (chatType == EMMessage.ChatType.Chat) { // single chat message
                        intent.putExtra("userId", message.getFrom());
                        intent.putExtra("chatType", com.hx.easemob.Constant.CHATTYPE_SINGLE);
                    } else { // group chat message
                        // message.getTo() is the group id
                        intent.putExtra("userId", message.getTo());
                        if (chatType == EMMessage.ChatType.GroupChat) {
                            intent.putExtra("chatType", com.hx.easemob.Constant.CHATTYPE_GROUP);
                        } else {
                            intent.putExtra("chatType", com.hx.easemob.Constant.CHATTYPE_CHATROOM);
                        }

                    }
                }
                return intent;
            }
        };
    }
}
