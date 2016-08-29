package com.hx.template.domain.usercase.user;

import com.hx.template.domain.usercase.UseCase;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;

/**
 * Created by huangxiang on 16/8/29.
 */
public class ResetPwdBySMSCodeCase extends UseCase<ResetPwdBySMSCodeCase.RequestValues, ResetPwdBySMSCodeCase.ResponseValue> {
    private final UserModel userModel;

    public ResetPwdBySMSCodeCase(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {
        String code = requestValues.getCode();
        String newPwd = requestValues.getNewPwd();
        userModel.resetPasswordBySMSCode(code, newPwd, new Callback() {
            @Override
            public void onSuccess(int taskId, Object... data) {
                if (taskId == TaskManager.TASK_ID_RESET_PASSWORD_BY_SMS_CODE) {
                    getUseCaseCallback().onSuccess(new ResponseValue());
                }
            }

            @Override
            public void onFailure(int taskId, String errorCode, Object... errorMsg) {
                if (taskId == TaskManager.TASK_ID_RESET_PASSWORD_BY_SMS_CODE) {
                    getUseCaseCallback().onError(errorCode, errorMsg.toString());
                }
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final String code;
        private final String newPwd;

        public RequestValues(String code, String newPwd) {
            this.code = code;
            this.newPwd = newPwd;
        }

        public String getCode() {
            return code;
        }

        public String getNewPwd() {
            return newPwd;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

    }
}
