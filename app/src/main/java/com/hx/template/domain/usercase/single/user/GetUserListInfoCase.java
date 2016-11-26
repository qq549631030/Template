package com.hx.template.domain.usercase.single.user;

import com.hx.template.domain.usercase.UseCase;
import com.hx.template.entity.User;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;

import java.util.List;

/**
 * 功能说明：
 * 作者：huangx on 2016/11/26 10:28
 * 邮箱：huangx@pycredit.cn
 */

public class GetUserListInfoCase extends UseCase<GetUserListInfoCase.RequestValues, GetUserListInfoCase.ResponseValue> {

    private final UserModel userModel;

    public GetUserListInfoCase(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {
        userModel.getUserListInfo(requestValues.getFieldValues(), requestValues.getFieldName(), new Callback() {
            @Override
            public void onSuccess(int taskId, Object... data) {
                if (taskId == TaskManager.TASK_ID_GET_USER_INFO_LIST) {
                    if (data != null && data.length > 0 && data[0] instanceof List) {
                        getUseCaseCallback().onSuccess(new ResponseValue((List<User>) data[0]));
                    }
                }
            }

            @Override
            public void onFailure(int taskId, String errorCode, String errorMsg) {
                if (TaskManager.TASK_ID_GET_USER_INFO_LIST == taskId) {
                    getUseCaseCallback().onError(errorCode, errorMsg);
                }
            }
        });
    }

    public static final class RequestValues<T> implements UseCase.RequestValues {
        private final List<T> fieldValues;
        private final String fieldName;

        public RequestValues(List<T> fieldValues, String fieldName) {
            this.fieldValues = fieldValues;
            this.fieldName = fieldName;
        }

        public List<T> getFieldValues() {
            return fieldValues;
        }

        public String getFieldName() {
            return fieldName;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final List<User> userList;

        public ResponseValue(List<User> userList) {
            this.userList = userList;
        }

        public List<User> getUserList() {
            return userList;
        }
    }
}
