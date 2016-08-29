package com.hx.template.domain.usercase.user;

import com.hx.template.domain.usercase.UseCase;
import com.hx.template.model.Callback;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;

/**
 * Created by huangxiang on 16/8/29.
 */
public class ModifyPwdCase extends UseCase<ModifyPwdCase.RequestValues, ModifyPwdCase.ResponseValue> {

    private final UserModel userModel;

    public ModifyPwdCase(UserModel userModel) {
        this.userModel = userModel;
    }


    @Override
    public void executeUseCase(RequestValues requestValues) {
        String oldPwd = requestValues.getOldPwd();
        String newPwd = requestValues.getNewPwd();
        userModel.modifyPwd(oldPwd, newPwd, new Callback() {
            @Override
            public void onSuccess(int taskId, Object... data) {
                if (taskId == TaskManager.TASK_ID_MODIFY_PWD) {
                    getUseCaseCallback().onSuccess(new ResponseValue());
                }
            }

            @Override
            public void onFailure(int taskId, String errorCode, Object... errorMsg) {
                if (taskId == TaskManager.TASK_ID_MODIFY_PWD) {
                    getUseCaseCallback().onError(errorCode, errorMsg.toString());
                }
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final String oldPwd;
        private final String newPwd;

        public RequestValues(String oldPwd, String newPwd) {
            this.oldPwd = oldPwd;
            this.newPwd = newPwd;
        }

        public String getOldPwd() {
            return oldPwd;
        }

        public String getNewPwd() {
            return newPwd;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

    }
}
