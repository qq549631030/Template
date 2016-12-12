package com.hx.template.mvp.usecase.single.user;

import com.hx.mvp.Callback;
import com.hx.mvp.usecase.BaseUseCase;
import com.hx.template.model.UserModel;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by huangxiang on 16/8/29.
 */
public class UpdateUserInfoCase extends BaseUseCase<UpdateUserInfoCase.RequestValues, UpdateUserInfoCase.ResponseValue> {
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
            public void onSuccess(Object... data) {
                getUseCaseCallback().onSuccess(new ResponseValue());
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

    public static final class ResponseValue implements BaseUseCase.ResponseValue {

    }
}
