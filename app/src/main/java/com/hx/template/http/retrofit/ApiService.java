package com.hx.template.http.retrofit;

import com.hx.template.entity.User;
import com.hx.template.http.impl.HttpReturn;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by huangx on 2016/4/1.
 */
public interface ApiService {
    @POST("member/login")
    Observable<HttpReturn.LoginReturn> login(@Query("userName") String username, @Query("password") String password);
}
