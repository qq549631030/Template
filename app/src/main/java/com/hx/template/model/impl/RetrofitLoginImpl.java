package com.hx.template.model.impl;

import android.text.TextUtils;

import com.hx.template.CustomApplication;
import com.hx.template.R;
import com.hx.template.entity.User;
import com.hx.template.entity.enums.ErrorCode;
import com.hx.template.http.impl.HttpReturn;
import com.hx.template.http.retrofit.ApiService;
import com.hx.template.http.retrofit.RetrofitUtils;
import com.hx.template.model.LoginModel;
import com.hx.template.utils.LogUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by huangx on 2016/4/1.
 */
public class RetrofitLoginImpl implements LoginModel.Model {
    @Override
    public void login(String username, String password, final LoginModel.OnLoginListener listener) {
        ApiService apiService = RetrofitUtils.createApi(ApiService.class);
        apiService.login(username, password)
                .subscribeOn(Schedulers.io())//在io线程执行网络请求
                .observeOn(AndroidSchedulers.mainThread())//在主线程回调
                .subscribe(new Subscriber<HttpReturn.LoginReturn>() {
                    @Override
                    public void onNext(HttpReturn.LoginReturn loginReturn) {
                        if (loginReturn != null) {
                            if (loginReturn.getStatus() == 1) {
                                User user = loginReturn.getData();
                                listener.loginSuccess(user);
                            } else {
                                ErrorCode code = loginReturn.getCode();
                                if (code != null) {
                                    listener.loginFailed(CustomApplication.getInstance().getString(loginReturn.getCode().getRes()));
                                } else {
                                    String msg = loginReturn.getMsg();
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
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loginFailed(CustomApplication.getInstance().getString(R.string.error_unknow));
                    }
                });
    }
}
