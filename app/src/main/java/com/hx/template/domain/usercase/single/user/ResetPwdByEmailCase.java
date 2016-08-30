package com.hx.template.domain.usercase.single.user;

import com.hx.template.domain.usercase.UseCase;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;

import javax.inject.Inject;

/**
 * Created by huangxiang on 16/8/29.
 */
public class ResetPwdByEmailCase extends UseCase<ResetPwdByEmailCase.RequestValues, ResetPwdByEmailCase.ResponseValue> {

    private final UserModel userModel;

    @Inject
    public ResetPwdByEmailCase(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {
        String email = requestValues.getEmail();

        userModel.resetPasswordByEmail(email, new Callback() {
            @Override
            public void onSuccess(int taskId, Object... data) {
                if (taskId == TaskManager.TASK_ID_RESET_PASSWORD_BY_EMAIL) {
                    getUseCaseCallback().onSuccess(new ResponseValue());
                }
            }

            @Override
            public void onFailure(int taskId, String errorCode, String errorMsg) {
                if (taskId == TaskManager.TASK_ID_RESET_PASSWORD_BY_EMAIL) {
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
