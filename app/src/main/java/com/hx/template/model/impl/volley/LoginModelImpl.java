package com.hx.template.model.impl.volley;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.hx.template.CustomApplication;
import com.hx.template.HttpConfig;
import com.hx.template.R;
import com.hx.template.entity.User;
import com.hx.template.entity.enums.ErrorCode;
import com.hx.template.http.HttpListener;
import com.hx.template.http.HttpPostUtils;
import com.hx.template.http.impl.HttpParams;
import com.hx.template.http.impl.HttpParseUtils;
import com.hx.template.http.impl.HttpReturn;
import com.hx.template.model.LoginModel;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangxiang on 16/3/9.
 */
public class LoginModelImpl implements LoginModel.Model {
    @Override
    public void login(String userName, String password, final LoginModel.OnLoginListener listener) {
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
                        listener.loginSuccess(user);
                    } else {
                        ErrorCode code = mReturn.getCode();
                        if (code != null) {
                            listener.loginFailed(CustomApplication.getInstance().getString(mReturn.getCode().getRes()));
                        } else {
                            String msg = mReturn.getMsg();
                            if (TextUtils.isEmpty(msg)) {
                                listener.loginFailed(CustomApplication.getInstance().getString(R.string.error_unknow));
                            } else {
                                listener.loginFailed(msg);
                            }
                        }
                    }
                } else {
                    listener.loginFailed(CustomApplication.getInstance().getString(R.string.error_unknow));
                }
            }

            @Override
            public void onError(String ErrorMsg, int errorCode) {
                listener.loginFailed(ErrorMsg);
            }
        }, false);
    }
}
