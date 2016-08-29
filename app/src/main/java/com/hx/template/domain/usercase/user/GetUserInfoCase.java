package com.hx.template.domain.usercase.user;

import com.hx.template.domain.usercase.UseCase;
import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;

/**
 * Created by huangxiang on 16/8/29.
 */
public class GetUserInfoCase extends UseCase<GetUserInfoCase.RequestValues, GetUserInfoCase.ResponseValue> {

    private final UserModel userModel;

    public GetUserInfoCase(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {
        String userId = requestValues.getUserId();
        userModel.getUserInfo(userId, new Callback() {
            @Override
            public void onSuccess(int taskId, Object... data) {
                if (taskId == TaskManager.TASK_ID_GET_USER_INFO) {
                    if (data != null && data.length > 0 && data[0] instanceof User) {
                        getUseCaseCallback().onSuccess(new ResponseValue((User) data[0]));
                    }
                }
            }

            @Override
            public void onFailure(int taskId, String errorCode, Object... errorMsg) {
                if (TaskManager.TASK_ID_GET_USER_INFO == taskId) {
                    getUseCaseCallback().onError(errorCode, errorMsg);
                }
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final String userId;

        public RequestValues(String userId) {
            this.userId = userId;
        }

        public String getUserId() {
            return userId;
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
