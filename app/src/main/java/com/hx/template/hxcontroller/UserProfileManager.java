package com.hx.template.hxcontroller;

import android.content.Context;
import android.util.Log;

import com.easemob.EMValueCallBack;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.easeui.domain.EaseUser;
import com.hx.template.domain.usercase.UseCase;
import com.hx.template.domain.usercase.single.user.GetUserInfoCase;
import com.hx.template.domain.usercase.single.user.GetUserListInfoCase;
import com.hx.template.entity.User;
import com.hx.template.hxcontroller.HXSDKHelper.DataSyncListener;
import com.hx.template.model.Callback;
import com.hx.template.model.ModelManager;
import com.hx.template.model.UserModel;
import com.hx.template.utils.DecimalUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class UserProfileManager {

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
	private List<DataSyncListener> syncContactInfosListeners = new ArrayList<DataSyncListener>();

	private boolean isSyncingContactInfosWithServer = false;

	private EaseUser currentUser;

	public UserProfileManager() {
	}

	public synchronized boolean init(Context context) {
		if (sdkInited) {
			return true;
		}
		syncContactInfosListeners = new ArrayList<DataSyncListener>();
		sdkInited = true;
		return true;
	}

	public void addSyncContactInfoListener(DataSyncListener listener) {
		if (listener == null) {
			return;
		}
		if (!syncContactInfosListeners.contains(listener)) {
			syncContactInfosListeners.add(listener);
		}
	}

	public void removeSyncContactInfoListener(DataSyncListener listener) {
		if (listener == null) {
			return;
		}
		if (syncContactInfosListeners.contains(listener)) {
			syncContactInfosListeners.remove(listener);
		}
	}

	public void asyncFetchContactInfosFromServer(List<String> usernames, final EMValueCallBack<List<EaseUser>> callback) {
		if (isSyncingContactInfosWithServer) {
			return;
		}
		isSyncingContactInfosWithServer = true;
		UserModel userModel = ModelManager.provideUserModel();
		GetUserListInfoCase getUserListInfoCase = new GetUserListInfoCase(userModel);
		GetUserListInfoCase.RequestValues requestValues = new GetUserListInfoCase.RequestValues(usernames,"username");
		getUserListInfoCase.setRequestValues(requestValues);
		getUserListInfoCase.setUseCaseCallback(new UseCase.UseCaseCallback<GetUserListInfoCase.ResponseValue>() {
			@Override
			public void onSuccess(GetUserListInfoCase.ResponseValue response) {
                isSyncingContactInfosWithServer = false;
                List<User> userList = response.getUserList();
                // in case that logout already before server returns,we should
                // return immediately
                if (!EMChat.getInstance().isLoggedIn()) {
                    return;
                }
                if (callback != null) {
                    List<EaseUser> easeUsers = new ArrayList<EaseUser>();
                    if (userList != null && userList.size() > 0) {
                        for (User user : userList) {
                            easeUsers.add(convertToEaseUser(user));
                        }
                    }
                    callback.onSuccess(easeUsers);
                }
            }

			@Override
			public void onError(String errorCode, String errorMsg) {
				isSyncingContactInfosWithServer = false;
				if (callback != null) {
					callback.onError(DecimalUtils.getInt(errorCode), errorMsg);
				}
			}
		});
		getUserListInfoCase.run();
	}

	public void notifyContactInfosSyncListener(boolean success) {
		for (DataSyncListener listener : syncContactInfosListeners) {
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
			User user = User.getCurrentUser();
			if (user != null) {
				currentUser = convertToEaseUser(user);
			}
		}
		return currentUser;
	}

	public void asyncGetUserInfo(final String username, final EMValueCallBack<EaseUser> callback){
		UserModel userModel = ModelManager.provideUserModel();
		GetUserInfoCase getUserInfoCase = new GetUserInfoCase(userModel);
		GetUserInfoCase.RequestValues requestValues = new GetUserInfoCase.RequestValues(username,"username");
		getUserInfoCase.setRequestValues(requestValues);
		getUserInfoCase.setUseCaseCallback(new UseCase.UseCaseCallback<GetUserInfoCase.ResponseValue>() {
			@Override
			public void onSuccess(GetUserInfoCase.ResponseValue response) {
				User user = response.getUser();
				if (user != null) {
					final EaseUser easeUser = convertToEaseUser(user);
					callback.onSuccess(easeUser);
				}
			}

			@Override
			public void onError(String errorCode, String errorMsg) {
				callback.onError(DecimalUtils.getInt(errorCode),errorMsg);
			}
		});
		getUserInfoCase.run();
	}

    public static EaseUser convertToEaseUser(User user){
        EaseUser easeUser = new EaseUser(user.getUsername());
        easeUser.setAvatar(user.getAvatar());
        easeUser.setNick(user.getNickname());
        return easeUser;
    }
}
