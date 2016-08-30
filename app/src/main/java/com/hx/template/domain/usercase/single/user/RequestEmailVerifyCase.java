package com.hx.template.domain.usercase.single.user;

import com.hx.template.domain.usercase.UseCase;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;

import javax.inject.Inject;

/**
 * Created by huangxiang on 16/8/29.
 */
public class RequestEmailVerifyCase extends UseCase<RequestEmailVerifyCase.RequestValues, RequestEmailVerifyCase.ResponseValue> {
    private final UserModel userModel;

    @Inject
    public RequestEmailVerifyCase(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {
        String email = requestValues.getEmail();
        userModel.requestEmailVerify(email, new Callback() {
            @Override
            public void onSuccess(int taskId, Object... data) {
                if (taskId == TaskManager.TASK_ID_REQUEST_EMAIL_VERIFY) {
                    getUseCaseCallback().onSuccess(new ResponseValue());
                }
            }

            @Override
            public void onFailure(int taskId, String errorCode, String errorMsg) {
                if (taskId == TaskManager.TASK_ID_REQUEST_EMAIL_VERIFY) {
                    getUseCaseCallback().onError(errorCode, errorMsg);
                }
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final String email;

        public RequestValues(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

    }
}
