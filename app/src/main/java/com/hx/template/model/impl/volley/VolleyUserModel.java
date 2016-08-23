package com.hx.template.model.impl.volley;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.hx.template.CustomApplication;
import com.hx.template.HttpConfig;
import com.hx.template.R;
import com.hx.template.entity.User;
import com.hx.template.entity.enums.ErrorCode;
import com.hx.template.http.volley.HttpListener;
import com.hx.template.http.volley.HttpPostUtils;
import com.hx.template.http.HttpParams;
import com.hx.template.http.HttpParseUtils;
import com.hx.template.http.HttpReturn;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangxiang on 16/3/9.
 */
public class VolleyUserModel implements UserModel {
    @Override
    public void register(String username, String password, Callback callback) {

    }

    @Override
    public void login(String userName, String password, final Callback callback) {
        final Map<String, String> params = new HashMap<String, String>();
        if (!TextUtils.isEmpty(userName)) {
            params.put(HttpParams.Login.userName, userName);
        }
        if (!TextUtils.isEmpty(password)) {
            params.put(HttpParams.Login.password, password);
        }

        HttpPostUtils.doLazyFromPostRequest(HttpConfig.LOGIN_URL, params, new HttpListener() {

            @Override
            public void onPass(JSONObject jsonObject) {
                Type type = new TypeToken<HttpReturn.LoginReturn>() {
                }.getType();
                HttpReturn.LoginReturn mReturn = HttpParseUtils.parseReturn(jsonObject, type);
                if (mReturn != null) {
                    if (mReturn.getStatus() == 1) {
                        User user = mReturn.getData();
                        callback.onSuccess(TaskManager.TASK_ID_LOGIN, user);
                    } else {
                        ErrorCode code = mReturn.getCode();
                        if (code != null) {
                            callback.onFailure(TaskManager.TASK_ID_LOGIN, mReturn.getCode().getId(), CustomApplication.getInstance().getString(mReturn.getCode().getRes()));
                        } else {
                            String msg = mReturn.getMsg();
                            if (TextUtils.isEmpty(msg)) {
                                callback.onFailure(TaskManager.TASK_ID_LOGIN, "-1", CustomApplication.getInstance().getString(R.string.error_unknow));
                            } else {
                                callback.onFailure(TaskManager.TASK_ID_LOGIN, "-1", msg);
                            }
                        }
                    }
                } else {
                    callback.onFailure(TaskManager.TASK_ID_LOGIN, "-1", CustomApplication.getInstance().getString(R.string.error_unknow));
                }
            }

            @Override
            public void onError(String ErrorMsg, int errorCode) {
                callback.onFailure(TaskManager.TASK_ID_LOGIN, Integer.toString(errorCode), ErrorMsg);
            }
        }, false);
    }

    /**
     * 修改密码
     *
     * @param oldPwd
     * @param newPwd
     * @param callback
     */
    @Override
    public void modifyPwd(String oldPwd, String newPwd, Callback callback) {

    }

    /**
     * 获取用户信息
     *
     * @param userId   用户id
     * @param callback 回调监听
     */
    @Override
    public void getUserInfo(String userId, Callback callback) {

    }

    /**
     * 更新用户信息
     *
     * @param values   要更新的用户信息
     * @param callback 回调监听
     */
    @Override
    public void updateUserInfo(Map<String, Object> values, Callback callback) {
        
    }


    /**
     * 请求验证Email
     *
     * @param email    要验证有邮箱
     * @param callback 回调监听
     */
    @Override
    public void requestEmailVerify(String email, Callback callback) {

    }

    @Override
    public void resetPasswordBySMSCode(String code, String pwd, Callback callback) {

    }

    /**
     * 邮箱重置密码
     *
     * @param email    绑定的邮箱地址
     * @param callback 回调监听
     */
    @Override
    public void resetPasswordByEmail(String email, Callback callback) {

    }


    @Override
    public void logout() {

    }
}
