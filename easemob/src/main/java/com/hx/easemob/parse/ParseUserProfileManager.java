package com.hx.easemob.parse;

import android.content.Context;

import com.hx.easemob.HXSDKHelper;
import com.hx.easemob.model.UserProfileManager;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.List;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/19 19:44
 * 邮箱：huangx@pycredit.cn
 */

public class ParseUserProfileManager extends UserProfileManager {

    @Override
    public synchronized boolean init(Context context) {
        ParseManager.getInstance().onInit(context);
        return super.init(context);
    }

    @Override
    public void asyncFetchContactInfosFromServer(List<String> usernames, final EMValueCallBack<List<EaseUser>> callback) {
        if (isSyncingContactInfosWithServer) {
            return;
        }
        isSyncingContactInfosWithServer = true;
        ParseManager.getInstance().getContactInfos(usernames, new EMValueCallBack<List<EaseUser>>() {

            @Override
            public void onSuccess(List<EaseUser> value) {
                isSyncingContactInfosWithServer = false;
                // in case that logout already before server returns,we should
                // return immediately
                if (!HXSDKHelper.getInstance().isLoggedIn()) {
                    return;
                }
                if (callback != null) {
                    callback.onSuccess(value);
                }
            }

            @Override
            public void onError(int error, String errorMsg) {
                isSyncingContactInfosWithServer = false;
                if (callback != null) {
                    callback.onError(error, errorMsg);
                }
            }

        });
    }

    @Override
    public boolean updateCurrentUserNickName(String nickname) {
        boolean isSuccess = ParseManager.getInstance().updateParseNickName(nickname);
        if (isSuccess) {
            setCurrentUserNick(nickname);
        }
        return isSuccess;
    }

    @Override
    public String uploadUserAvatar(byte[] data) {
        String avatarUrl = ParseManager.getInstance().uploadParseAvatar(data);
        if (avatarUrl != null) {
            setCurrentUserAvatar(avatarUrl);
        }
        return avatarUrl;
    }

    @Override
    public void asyncGetCurrentUserInfo() {
        ParseManager.getInstance().asyncGetCurrentUserInfo(new EMValueCallBack<EaseUser>() {

            @Override
            public void onSuccess(EaseUser value) {
                if (value != null) {
                    setCurrentUserNick(value.getNick());
                    setCurrentUserAvatar(value.getAvatar());
                }
            }

            @Override
            public void onError(int error, String errorMsg) {

            }
        });
    }

    @Override
    public void asyncGetUserInfo(String username, EMValueCallBack<EaseUser> callback) {
        ParseManager.getInstance().asyncGetUserInfo(username, callback);
    }
}
