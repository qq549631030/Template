package com.hx.template.mvp.usecase.single.user;

import com.hx.mvp.Callback;
import com.hx.mvp.usecase.BaseUseCase;
import com.hx.template.entity.User;
import com.hx.template.model.UserModel;

import java.util.List;

/**
 * 功能说明：
 * 作者：huangx on 2016/11/26 10:28
 * 邮箱：549631030@qq.com
 */

public class GetUserListInfoCase extends BaseUseCase<GetUserListInfoCase.RequestValues, GetUserListInfoCase.ResponseValue> {

    private final UserModel userModel;

    public GetUserListInfoCase(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {
        userModel.getUserListInfo(requestValues.getFieldValues(), requestValues.getFieldName(), new Callback() {
            @Override
            public void onSuccess(Object... data) {
                if (data != null && data.length > 0 && data[0] instanceof List) {
                    getUseCaseCallback().onSuccess(new ResponseValue((List<User>) data[0]));
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

    public static final class RequestValues<T> implements BaseUseCase.RequestValues {
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

    public static final class ResponseValue implements BaseUseCase.ResponseValue {
        private final List<User> userList;

        public ResponseValue(List<User> userList) {
            this.userList = userList;
        }

        public List<User> getUserList() {
            return userList;
        }
    }
}
