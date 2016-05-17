package com.hx.template.http.retrofit.mock;

import com.hx.template.entity.User;
import com.hx.template.http.HttpReturn.LoginReturn;
import com.hx.template.http.retrofit.ApiService;

import retrofit2.http.Query;
import retrofit2.http.Url;
import retrofit2.mock.BehaviorDelegate;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by huangx on 2016/5/17.
 */
public class MockApiService implements ApiService {

    private final BehaviorDelegate<ApiService> delegate;

    public MockApiService(BehaviorDelegate<ApiService> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Observable<LoginReturn> login(@Url String url, @Query("userName") final String username, @Query("password") final String password) {

        Observable observable = Observable.create(new Observable.OnSubscribe<LoginReturn>() {
            @Override
            public void call(Subscriber<? super LoginReturn> subscriber) {
                if ("158706790474".equals(username)&&"123456".equals(password)){
                    LoginReturn mReturn = new LoginReturn();
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    mReturn.setStatus(1);
                    mReturn.setData(user);
                    subscriber.onNext(mReturn);
                }else{
                    LoginReturn mReturn = new LoginReturn();
                    mReturn.setStatus(0);
                    mReturn.setMsg("用户名或密码错误");
                    subscriber.onNext(mReturn);
                }
               
            }
        });
        return observable;
    }
}
