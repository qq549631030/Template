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
import com.hx.template.model.LoginModel;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangxiang on 16/3/9.
 */
public class LoginModelImpl implements LoginModel {
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
                        callback.onSuccess(user);
                    } else {
                        ErrorCode code = mReturn.getCode();
                        if (code != null) {
                            callback.onFailure(mReturn.getCode().getId(), CustomApplication.getInstance().getString(mReturn.getCode().getRes()));
                        } else {
                            String msg = mReturn.getMsg();
                            if (TextUtils.isEmpty(msg)) {
                                callback.onFailure("-1", CustomApplication.getInstance().getString(R.string.error_unknow));
                            } else {
                                callback.onFailure("-1", msg);
                            }
                        }
                    }
                } else {
                    callback.onFailure("-1", CustomApplication.getInstance().getString(R.string.error_unknow));
                }
            }

            @Override
            public void onError(String ErrorMsg, int errorCode) {
                callback.onFailure(Integer.toString(errorCode), ErrorMsg);
            }
        }, false);
    }
}
