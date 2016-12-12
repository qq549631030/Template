package com.hx.template.mvp.usecase.single.user;

import com.hx.mvp.Callback;
import com.hx.mvp.usecase.BaseUseCase;
import com.hx.template.model.UserModel;

import javax.inject.Inject;

/**
 * Created by huangxiang on 16/8/29.
 */
public class ModifyPwdCase extends BaseUseCase<ModifyPwdCase.RequestValues, ModifyPwdCase.ResponseValue> {

    private final UserModel userModel;

    @Inject
    public ModifyPwdCase(UserModel userModel) {
        this.userModel = userModel;
    }


    @Override
    public void executeUseCase(RequestValues requestValues) {
        String oldPwd = requestValues.getOldPwd();
        String newPwd = requestValues.getNewPwd();
        userModel.modifyPwd(oldPwd, newPwd, new Callback() {
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

    public static final class ResponseValue implements BaseUseCase.ResponseValue {

    }
}
