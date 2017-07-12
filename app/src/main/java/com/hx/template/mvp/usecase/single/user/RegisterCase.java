package com.hx.template.mvp.usecase.single.user;

import android.support.annotation.NonNull;

import com.hx.mvp.Callback;
import com.hx.mvp.usecase.BaseUseCase;
import com.hx.template.entity.User;
import com.hx.template.model.UserModel;

import javax.inject.Inject;

/**
 * 功能说明：注册用例
 * 作者：huangx on 2016/8/29 9:10
 * 邮箱：549631030@qq.com
 */
public class RegisterCase extends BaseUseCase<RegisterCase.RequestValues, RegisterCase.ResponseValue> {
    private final UserModel userModel;

    @Inject
    public RegisterCase(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {
        String username = requestValues.getUsername();
        String password = requestValues.getPassword();
        userModel.register(username, password, new Callback() {
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

    /**
     * 取消操作
     *
     * @param args 参数
     */
    @Override
    public boolean cancel(Object... args) {
        return false;
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
