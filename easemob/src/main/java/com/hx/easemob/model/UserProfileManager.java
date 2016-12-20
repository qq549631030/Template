package com.hx.easemob.model;

import android.content.Context;

import com.hx.easemob.HXSDKHelper;
import com.hx.easemob.utils.PreferenceManager;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/19 19:25
 * 邮箱：huangx@pycredit.cn
 */

public abstract class UserProfileManager {
    /**
     * application context
     */
    protected Context appContext = null;

    /**
     * init flag: test if the sdk has been inited before, we don't need to init
     * again
     */
    private boolean sdkInited = false;

    /**
     * HuanXin sync contact nick and avatar listener
     */
    private List<HXSDKHelper.DataSyncListener> syncContactInfosListeners = new ArrayList<HXSDKHelper.DataSyncListener>();

    protected boolean isSyncingContactInfosWithServer = false;

    protected EaseUser currentUser;

    public UserProfileManager() {
    }

    protected boolean init(Context context) {
        if (sdkInited) {
            return sdkInited;
        }
        sdkInited = true;
        return sdkInited;
    }

    public void addSyncContactInfoListener(HXSDKHelper.DataSyncListener listener) {
        if (listener == null) {
            return;
        }
        if (!syncContactInfosListeners.contains(listener)) {
            syncContactInfosListeners.add(listener);
        }
    }

    public void removeSyncContactInfoListener(HXSDKHelper.DataSyncListener listener) {
        if (listener == null) {
            return;
        }
        if (syncContactInfosListeners.contains(listener)) {
            syncContactInfosListeners.remove(listener);
        }
    }

    public abstract void asyncFetchContactInfosFromServer(List<String> usernames, final EMValueCallBack<List<EaseUser>> callback);

    public void notifyContactInfosSyncListener(boolean success) {
        for (HXSDKHelper.DataSyncListener listener : syncContactInfosListeners) {
            listener.onSyncComplete(success);
        }
    }

    public boolean isSyncingContactInfoWithServer() {
        return isSyncingContactInfosWithServer;
    }

    public synchronized void reset() {
        isSyncingContactInfosWithServer = false;
        currentUser = null;
        PreferenceManager.getInstance().removeCurrentUserInfo();
    }

    public synchronized EaseUser getCurrentUserInfo() {
        if (currentUser == null) {
            String username = EMClient.getInstance().getCurrentUser();
            currentUser = new EaseUser(username);
            String nick = getCurrentUserNick();
            currentUser.setNick((nick != null) ? nick : username);
            currentUser.setAvatar(getCurrentUserAvatar());
        }
        return currentUser;
    }

    public abstract boolean updateCurrentUserNickName(final String nickname);

    public abstract String uploadUserAvatar(byte[] data);

    public abstract void asyncGetCurrentUserInfo();

    public abstract void asyncGetUserInfo(final String username, final EMValueCallBack<EaseUser> callback);

    protected void setCurrentUserNick(String nickname) {
        getCurrentUserInfo().setNick(nickname);
        PreferenceManager.getInstance().setCurrentUserNick(nickname);
    }

    protected void setCurrentUserAvatar(String avatar) {
        getCurrentUserInfo().setAvatar(avatar);
        PreferenceManager.getInstance().setCurrentUserAvatar(avatar);
    }

    private String getCurrentUserNick() {
        return PreferenceManager.getInstance().getCurrentUserNick();
    }

    private String getCurrentUserAvatar() {
        return PreferenceManager.getInstance().getCurrentUserAvatar();
    }


}
