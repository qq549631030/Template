package com.hx.template;

import com.hx.easemob.model.UserProfileManager;
import com.hx.mvp.Callback;
import com.hx.template.entity.User;
import com.hx.template.model.ModelManager;
import com.hx.template.model.UserModel;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/20 20:37
 * 邮箱：huangx@pycredit.cn
 */

public class CustomUserProfileManager extends UserProfileManager {
    @Override
    public void asyncFetchContactInfosFromServer(List<String> usernames, final EMValueCallBack<List<EaseUser>> callback) {
        UserModel userModel = ModelManager.provideUserModel();
        userModel.getUserListInfo(usernames, "username", new Callback() {
            @Override
            public void onSuccess(Object... data) {
                if (data != null && data.length > 0 && data[0] instanceof List) {
                    List<User> userList = (List<User>) data[0];
                    List<EaseUser> easeUserList = new ArrayList<EaseUser>();
                    for (User user : userList) {
                        easeUserList.add(new EaseUser(user.getUsername()));
                    }
                }
            }

            @Override
            public void onFailure(String errorCode, Object... errorData) {
                callback.onError(Integer.parseInt(errorCode), errorData != null && errorData.length > 0 ? (String) errorData[0] : "");
            }
        });
    }

    @Override
    public boolean updateCurrentUserNickName(String nickname) {
        return false;
    }

    @Override
    public String uploadUserAvatar(byte[] data) {
        return null;
    }

    @Override
    public void asyncGetCurrentUserInfo() {
        final String username = EMClient.getInstance().getCurrentUser();
        asyncGetUserInfo(username, new EMValueCallBack<EaseUser>() {
            @Override
            public void onSuccess(EaseUser easeUser) {
                if (easeUser != null) {
                    setCurrentUserNick(easeUser.getNick());
                    setCurrentUserAvatar(easeUser.getAvatar());
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    public void asyncGetUserInfo(String username, final EMValueCallBack<EaseUser> callback) {
        UserModel userModel = ModelManager.provideUserModel();
        userModel.getUserInfo(username, "username", new Callback() {
            @Override
            public void onSuccess(Object... data) {
                if (data != null && data.length > 0 && data[0] instanceof User) {
                    User user = (User) data[0];
                    callback.onSuccess(new EaseUser(user.getUsername()));
                }
            }

            @Override
            public void onFailure(String errorCode, Object... errorData) {
                callback.onError(Integer.parseInt(errorCode), errorData != null && errorData.length > 0 ? (String) errorData[0] : "");
            }
        });
    }
}
