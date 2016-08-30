package com.hx.template.domain.usercase.single.user;

import com.hx.template.domain.usercase.UseCase;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by huangxiang on 16/8/29.
 */
public class UpdateUserInfoCase extends UseCase<UpdateUserInfoCase.RequestValues, UpdateUserInfoCase.ResponseValue> {
    private final UserModel userModel;

    @Inject
    public UpdateUserInfoCase(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {
        Map<String, Object> updateMap = requestValues.getUpdateMap();
        String userId = requestValues.getUserId();

        userModel.updateUserInfo(userId, updateMap, new Callback() {
            @Override
            public void onSuccess(int taskId, Object... data) {
                if (taskId == TaskManager.TASK_ID_UPDATE_USER_INFO) {
                    getUseCaseCallback().onSuccess(new ResponseValue());
                }
            }

            @Override
            public void onFailure(int taskId, String errorCode, String errorMsg) {
                if (taskId == TaskManager.TASK_ID_UPDATE_USER_INFO) {
                    getUseCaseCallback().onError(errorCode, errorMsg);
                }
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final String userId;
        private final Map<String, Object> updateMap;

        public RequestValues(String userId, Map<String, Object> updateMap) {
            this.userId = userId;
            this.updateMap = updateMap;
        }

        public String getUserId() {
            return userId;
        }

        public Map<String, Object> getUpdateMap() {
            return updateMap;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

    }
}
