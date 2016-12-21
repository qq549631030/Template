package com.hx.template.mvp.usecase.single.im;

import android.support.annotation.NonNull;

import com.hx.mvp.Callback;
import com.hx.mvp.usecase.BaseUseCase;
import com.hx.template.model.IMModel;

import javax.inject.Inject;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/20 14:59
 * 邮箱：huangx@pycredit.cn
 */

public class IMRegisterCase extends BaseUseCase<IMRegisterCase.RequestValues, IMRegisterCase.ResponseValue> {

    private final IMModel imModel;

    @Inject
    public IMRegisterCase(@NonNull IMModel imModel) {
        this.imModel = imModel;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        imModel.register(requestValues.getUsername(), requestValues.getPassword(), new Callback() {
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
        return imModel.cancel(args);
    }

    public static final class RequestValues implements BaseUseCase.RequestValues {
        private final String username;
        private final String password;

        public RequestValues(@NonNull String username, @NonNull String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    public static final class ResponseValue implements BaseUseCase.ResponseValue {
    }
}
