package com.hx.template.http.retrofit;

import android.content.Context;
import android.util.Config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hx.template.HttpConfig;
import com.hx.template.entity.enums.ErrorCode;
import com.hx.template.entity.enums.adapter.ErrorCodeAdapter;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by huangx on 2016/4/1.
 */
public class RetrofitUtils {
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (RetrofitUtils.class) {
                if (retrofit == null) {
                    Gson gson = new GsonBuilder()
                            .enableComplexMapKeySerialization()
                            .registerTypeAdapter(ErrorCode.class, new ErrorCodeAdapter())
                            .create();
                    retrofit = new Retrofit.Builder()
                            .baseUrl(HttpConfig.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))//返回数据用gson解析
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//使用的Rxjava
                            .build();
                }
            }
        }
        return retrofit;
    }

    public static <T> T createApi(Class<T> clazz) {
        return getRetrofit().create(clazz);
    }
}
