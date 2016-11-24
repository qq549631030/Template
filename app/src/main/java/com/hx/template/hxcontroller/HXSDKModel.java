/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hx.template.hxcontroller;

import com.easemob.easeui.domain.EaseUser;

import java.util.List;
import java.util.Map;

/**
 * HX SDK app model which will manage the user data and preferences
 *
 * @author easemob
 */
public abstract class HXSDKModel {
    public abstract void setSettingMsgNotification(boolean paramBoolean);

    // 震动和声音总开关，来消息时，是否允许此开关打开
    // the vibrate and sound notification are allowed or not?
    public abstract boolean getSettingMsgNotification();

    public abstract void setSettingMsgSound(boolean paramBoolean);

    // 是否打开声音
    // sound notification is switched on or not?
    public abstract boolean getSettingMsgSound();

    public abstract void setSettingMsgVibrate(boolean paramBoolean);

    // 是否打开震动
    // vibrate notification is switched on or not?
    public abstract boolean getSettingMsgVibrate();

    public abstract void setSettingMsgSpeaker(boolean paramBoolean);

    // 是否打开扬声器
    // the speaker is switched on or not?
    public abstract boolean getSettingMsgSpeaker();

    public abstract void allowChatroomOwnerLeave(boolean value);

    //
    public abstract boolean isChatroomOwnerLeaveAllowed();

    public abstract void setAdaptiveVideoEncode(boolean value);

    public abstract boolean isAdaptiveVideoEncode();

    /**
     * 设置当前用户的环信id
     *
     * @param username
     */
    public abstract void setCurrentUserName(String username);

    /**
     * 获取当前用户的环信id
     */
    public abstract String getCurrentUsernName();

    public abstract String getHXId();

    public abstract void setDisabledGroups(List<String> groups);

    public abstract List<String> getDisabledGroups();

    public abstract void setDisabledIds(List<String> ids);

    public abstract List<String> getDisabledIds();

    public abstract boolean saveContactList(List<EaseUser> contactList);

    public abstract Map<String, EaseUser> getContactList();

    public abstract void saveContact(EaseUser user);
    /**
     * 返回application所在的process name,默认是包名
     *
     * @return
     */
    public abstract String getAppProcessName();

    /**
     * 是否总是接收好友邀请
     *
     * @return
     */
    public boolean getAcceptInvitationAlways() {
        return false;
    }

    /**
     * 是否需要环信好友关系，默认是false
     *
     * @return
     */
    public boolean getUseHXRoster() {
        return false;
    }

    /**
     * 是否需要已读回执
     *
     * @return
     */
    public boolean getRequireReadAck() {
        return true;
    }

    /**
     * 是否需要已送达回执
     *
     * @return
     */
    public boolean getRequireDeliveryAck() {
        return false;
    }

    /**
     * 是否运行在sandbox测试环境. 默认是关掉的
     * 设置sandbox 测试环境
     * 建议开发者开发时设置此模式
     */
    public boolean isSandboxMode() {
        return false;
    }

    /**
     * 是否设置debug模式
     *
     * @return
     */
    public boolean isDebugMode() {
        return true;
    }

    public void setGroupsSynced(boolean synced) {
    }

    public boolean isGroupsSynced() {
        return false;
    }

    public void setContactSynced(boolean synced) {
    }

    public boolean isContactSynced() {
        return false;
    }

    public void setBlacklistSynced(boolean synced) {
    }

    public boolean isBacklistSynced() {
        return false;
    }

}
