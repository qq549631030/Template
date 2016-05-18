package com.hx.template.http.retrofit.mock;

import com.hx.template.HttpConfig;
import com.hx.template.entity.User;
import com.hx.template.http.HttpReturn;
import com.hx.template.http.HttpReturn.LoginReturn;
import com.hx.template.http.retrofit.ApiService;

import java.util.Random;

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
    Random random = new Random();

    public MockApiService(BehaviorDelegate<ApiService> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Observable<LoginReturn> login(@Url final String url, @Query("userName") final String username, @Query("password") final String password) {
        Observable observable = Observable.create(new Observable.OnSubscribe<LoginReturn>() {
            @Override
            public void call(Subscriber<? super LoginReturn> subscriber) {
                // Add some random delay to mock the network delay
                int fakeNetworkTimeCost = random.nextInt(500) + 500;
                try {
                    Thread.sleep(fakeNetworkTimeCost);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (HttpConfig.LOGIN_URL.equals(url) && "15870679047".equals(username) && "123456".equals(password)) {
                    LoginReturn mReturn = new LoginReturn();
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    mReturn.setStatus(HttpReturn.BaseReturn.STATUS_SUCCESS);
                    mReturn.setData(user);
                    subscriber.onNext(mReturn);
                } else {
                    LoginReturn mReturn = new LoginReturn();
                    mReturn.setStatus(HttpReturn.BaseReturn.STATUS_FAIL);
                    mReturn.setMsg("用户名或密码错误");
                    subscriber.onNext(mReturn);
                }
            }
        });
        return observable;
    }
}
