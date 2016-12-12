package com.hx.template.mvp.usecase.single.user;

import com.hx.mvp.Callback;
import com.hx.mvp.usecase.BaseUseCase;
import com.hx.template.entity.User;
import com.hx.template.model.UserModel;

import javax.inject.Inject;

/**
 * Created by huangxiang on 16/8/29.
 */
public class GetUserInfoCase extends BaseUseCase<GetUserInfoCase.RequestValues, GetUserInfoCase.ResponseValue> {

    private final UserModel userModel;

    @Inject
    public GetUserInfoCase(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {
        userModel.getUserInfo(requestValues.getFieldValue(), requestValues.getFieldName(), new Callback() {
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
