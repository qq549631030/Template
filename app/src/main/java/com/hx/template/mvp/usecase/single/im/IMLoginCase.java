package com.hx.template.mvp.usecase.single.im;

import android.support.annotation.NonNull;

import com.hx.mvp.Callback;
import com.hx.mvp.usecase.BaseUseCase;
import com.hx.template.model.IMModel;

import javax.inject.Inject;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/20 15:08
 * 邮箱：huangx@pycredit.cn
 */

public class IMLoginCase extends BaseUseCase<IMLoginCase.RequestValues, IMLoginCase.ResponseValue> {

    private final IMModel imModel;

    @Inject
    public IMLoginCase(@NonNull IMModel imModel) {
        this.imModel = imModel;
    }

    @Override
    protected void executeUseCase(IMLoginCase.RequestValues requestValues) {
        imModel.login(requestValues.getUsername(), requestValues.getPassword(), new Callback() {
            @Override
            public void onSuccess(Object... data) {
                getUseCaseCallback().onSuccess(new IMLoginCase.ResponseValue());
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
