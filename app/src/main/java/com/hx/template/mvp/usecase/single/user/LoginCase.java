package com.hx.template.mvp.usecase.single.user;

import android.support.annotation.NonNull;

import com.hx.mvp.Callback;
import com.hx.mvp.usecase.BaseUseCase;
import com.hx.template.entity.User;
import com.hx.template.model.UserModel;

import javax.inject.Inject;

/**
 * 功能说明：登录用例
 * 作者：huangx on 2016/8/26 17:14
 * 邮箱：huangx@pycredit.cn
 */
public class LoginCase extends BaseUseCase<LoginCase.RequestValues, LoginCase.ResponseValue> {
    private final UserModel userModel;

    @Inject
    public LoginCase(@NonNull UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {
        String username = requestValues.getUsername();
        String password = requestValues.getPassword();
        userModel.login(username, password, new Callback() {
            @Override
            public void onSuccess(Object... data) {
                if (data != null && data.length > 0 && data[0] instanceof User) {
                    getUseCaseCallback().onSuccess(new ResponseValue((User) data[0]));
                }
            }

            @Override
            public void onFailure(String errorCode, Object... errorData) {
                getUseCaseCallback().onError(errorCode, errorData);
            }
        });
    }

    @Override
    public boolean cancel(Object... args) {
        return userModel.cancel(args);
    }

    public static final class RequestValues implements BaseUseCase.RequestValues {
        private final String username;
        private final String password;

        public RequestValues(@NonNull String username, @NonNull String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    public static final class ResponseValue implements BaseUseCase.ResponseValue {
        private final User user;

        public ResponseValue(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }
}
