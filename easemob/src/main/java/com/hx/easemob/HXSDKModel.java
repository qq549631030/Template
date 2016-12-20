package com.hx.easemob;

import com.hx.easemob.domain.RobotUser;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.List;
import java.util.Map;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/19 19:58
 * 邮箱：huangx@pycredit.cn
 */
public abstract class HXSDKModel {
    public abstract boolean saveContactList(List<EaseUser> contactList);

    public abstract Map<String, EaseUser> getContactList();

    public abstract void saveContact(EaseUser user);

    public abstract void setCurrentUserName(String username);

    public abstract String getCurrentUsernName();

    public abstract Map<String, RobotUser> getRobotList();

    public abstract boolean saveRobotList(List<RobotUser> robotList);

    public abstract void setSettingMsgNotification(boolean paramBoolean);

    public abstract boolean getSettingMsgNotification();

    public abstract void setSettingMsgSound(boolean paramBoolean);

    public abstract boolean getSettingMsgSound();

    public abstract void setSettingMsgVibrate(boolean paramBoolean);

    public abstract boolean getSettingMsgVibrate();

    public abstract void setSettingMsgSpeaker(boolean paramBoolean);

    public abstract boolean getSettingMsgSpeaker();

    public abstract void setDisabledGroups(List<String> groups);

    public abstract List<String> getDisabledGroups();

    public abstract void setDisabledIds(List<String> ids);

    public abstract List<String> getDisabledIds();

    public abstract void setGroupsSynced(boolean synced);

    public abstract boolean isGroupsSynced();

    public abstract void setContactSynced(boolean synced);

    public abstract boolean isContactSynced();

    public abstract void setBlacklistSynced(boolean synced);

    public abstract boolean isBacklistSynced();

    public abstract void allowChatroomOwnerLeave(boolean value);

    public abstract boolean isChatroomOwnerLeaveAllowed();

    public abstract void setDeleteMessagesAsExitGroup(boolean value);

    public abstract boolean isDeleteMessagesAsExitGroup();

    public abstract void setAutoAcceptGroupInvitation(boolean value);

    public abstract boolean isAutoAcceptGroupInvitation();

    public abstract void setAdaptiveVideoEncode(boolean value);

    public abstract boolean isAdaptiveVideoEncode();

    public abstract void setPushCall(boolean value);

    public abstract boolean isPushCall();

    public abstract void setRestServer(String restServer);

    public abstract String getRestServer();

    public abstract void setIMServer(String imServer);

    public abstract String getIMServer();

    public abstract void enableCustomServer(boolean enable);

    public abstract boolean isCustomServerEnable();

    public abstract void enableCustomAppkey(boolean enable);

    public abstract boolean isCustomAppkeyEnabled();

    public abstract void setCustomAppkey(String appkey);

    public abstract String getCutomAppkey();

    enum Key{
        VibrateAndPlayToneOn,
        VibrateOn,
        PlayToneOn,
        SpakerOn,
        DisabledGroups,
        DisabledIds
    }
}
