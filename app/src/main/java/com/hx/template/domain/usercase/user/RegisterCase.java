package com.hx.template.domain.usercase.user;

import android.support.annotation.NonNull;

import com.hx.template.domain.usercase.UseCase;
import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;

/**
 * 功能说明：注册用例
 * 作者：huangx on 2016/8/29 9:10
 * 邮箱：huangx@pycredit.cn
 */
public class RegisterCase extends UseCase<RegisterCase.RequestValues, RegisterCase.ResponseValue> {
    private final UserModel userModel;

    public RegisterCase(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {
        String username = requestValues.getUsername();
        String password = requestValues.getPassword();
        userModel.register(username, password, new Callback() {
            @Override
            public void onSuccess(int taskId, Object... data) {
                if (TaskManager.TASK_ID_REGISTER == taskId) {
                    if (data != null && data.length > 0 && data[0] instanceof User) {
                        getUseCaseCallback().onSuccess(new ResponseValue((User) data[0]));
                    }
                }
            }

            @Override
            public void onFailure(int taskId, String errorCode, Object... errorMsg) {
                if (TaskManager.TASK_ID_REGISTER == taskId) {
                    getUseCaseCallback().onError(errorCode, errorMsg);
                }
            }
        });

    }

    public static final class RequestValues implements UseCase.RequestValues {
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

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final User user;

        public ResponseValue(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }
}
