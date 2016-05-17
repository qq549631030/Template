package com.hx.template.http.retrofit;

import com.hx.template.http.HttpReturn;

import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by huangx on 2016/4/1.
 */
public interface ApiService {
    @POST
    Observable<HttpReturn.LoginReturn> login(@Url String url, @Query("userName") String username, @Query("password") String password);
}
