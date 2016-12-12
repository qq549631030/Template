package com.hx.template.mvp.usecase.single.user;

import com.hx.mvp.Callback;
import com.hx.mvp.usecase.BaseUseCase;
import com.hx.template.model.UserModel;

import javax.inject.Inject;

/**
 * Created by huangxiang on 16/8/29.
 */
public class ResetPwdBySMSCodeCase extends BaseUseCase<ResetPwdBySMSCodeCase.RequestValues, ResetPwdBySMSCodeCase.ResponseValue> {
    private final UserModel userModel;

    @Inject
    public ResetPwdBySMSCodeCase(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {
        String code = requestValues.getCode();
        String newPwd = requestValues.getNewPwd();
        userModel.resetPasswordBySMSCode(code, newPwd, new Callback() {
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

    public static final class ResponseValue implements BaseUseCase.ResponseValue {

    }
}
