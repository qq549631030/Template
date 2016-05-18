package com.hx.template.model.impl.retrofit;

import android.text.TextUtils;

import com.hx.template.CustomApplication;
import com.hx.template.HttpConfig;
import com.hx.template.R;
import com.hx.template.entity.User;
import com.hx.template.entity.enums.ErrorCode;
import com.hx.template.global.GsonUtils;
import com.hx.template.global.HXLog;
import com.hx.template.http.HttpReturn;
import com.hx.template.http.retrofit.ApiService;
import com.hx.template.http.retrofit.RetrofitUtils;
import com.hx.template.http.retrofit.mock.MockApiService;
import com.hx.template.model.LoginModel;

import retrofit2.Retrofit;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by huangx on 2016/4/1.
 */
public class RetrofitLoginImpl implements LoginModel.Model {
    @Override
    public void login(String username, String password, final LoginModel.OnLoginListener listener) {
        ApiService apiService;
//        apiService = RetrofitUtils.createApi(ApiService.class);
        Retrofit retrofit = RetrofitUtils.getRetrofit();
        NetworkBehavior behavior = NetworkBehavior.create();
        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();
        BehaviorDelegate<ApiService> delegate = mockRetrofit.create(ApiService.class);
        apiService = new MockApiService(delegate);
        apiService.login(HttpConfig.LOGIN_URL, username, password)
                .subscribeOn(Schedulers.io())//在io线程执行网络请求
                .observeOn(AndroidSchedulers.mainThread())//在主线程回调
                .subscribe(new Subscriber<HttpReturn.LoginReturn>() {
                    @Override
                    public void onNext(HttpReturn.LoginReturn loginReturn) {
                        HXLog.d(GsonUtils.toJson(loginReturn));
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
