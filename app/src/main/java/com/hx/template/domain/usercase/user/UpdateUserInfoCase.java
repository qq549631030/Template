package com.hx.template.domain.usercase.user;

import com.hx.template.domain.usercase.UseCase;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;

import java.util.Map;

/**
 * Created by huangxiang on 16/8/29.
 */
public class UpdateUserInfoCase extends UseCase<UpdateUserInfoCase.RequestValues, UpdateUserInfoCase.ResponseValue> {
    private final UserModel userModel;

    public UpdateUserInfoCase(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {
        Map<String, Object> updateMap = requestValues.getUpdateMap();
        userModel.updateUserInfo(updateMap, new Callback() {
            @Override
            public void onSuccess(int taskId, Object... data) {
                if (taskId == TaskManager.TASK_ID_UPDATE_USER_INFO) {
                    getUseCaseCallback().onSuccess(new ResponseValue());
                }
            }

            @Override
            public void onFailure(int taskId, String errorCode, Object... errorMsg) {
                if (taskId == TaskManager.TASK_ID_UPDATE_USER_INFO) {
                    getUseCaseCallback().onError(errorCode, errorMsg.toString());
                }
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final Map<String, Object> updateMap;

        public RequestValues(Map<String, Object> updateMap) {
            this.updateMap = updateMap;
        }

        public Map<String, Object> getUpdateMap() {
            return updateMap;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

    }
}
