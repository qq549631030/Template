package com.hx.template.domain.usercase.single.user;

import com.hx.template.domain.usercase.UseCase;
import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;

import javax.inject.Inject;

/**
 * Created by huangxiang on 16/8/29.
 */
public class GetUserInfoCase extends UseCase<GetUserInfoCase.RequestValues, GetUserInfoCase.ResponseValue> {

    private final UserModel userModel;

    @Inject
    public GetUserInfoCase(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {
        userModel.getUserInfo(requestValues.getFieldValue(), requestValues.getFieldName(), new Callback() {
            @Override
            public void onSuccess(int taskId, Object... data) {
                if (taskId == TaskManager.TASK_ID_GET_USER_INFO) {
                    if (data != null && data.length > 0 && data[0] instanceof User) {
                        getUseCaseCallback().onSuccess(new ResponseValue((User) data[0]));
                    }
                }
            }

            @Override
            public void onFailure(int taskId, String errorCode, String errorMsg) {
                if (TaskManager.TASK_ID_GET_USER_INFO == taskId) {
                    getUseCaseCallback().onError(errorCode, errorMsg);
                }
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final String fieldValue;
        private final String fieldName;

        public RequestValues(String fieldValue, String fieldName) {
            this.fieldValue = fieldValue;
            this.fieldName = fieldName;
        }

        public String getFieldValue() {
            return fieldValue;
        }

        public String getFieldName() {
            return fieldName;
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
