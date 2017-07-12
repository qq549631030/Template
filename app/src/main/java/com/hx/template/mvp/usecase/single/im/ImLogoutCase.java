package com.hx.template.mvp.usecase.single.im;

import android.support.annotation.NonNull;

import com.hx.mvp.Callback;
import com.hx.mvp.usecase.BaseUseCase;
import com.hx.template.model.IMModel;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/20 15:10
 * 邮箱：549631030@qq.com
 */

public class ImLogoutCase extends BaseUseCase<ImLogoutCase.RequestValues, ImLogoutCase.ResponseValue> {
    private final IMModel imModel;

    public ImLogoutCase(@NonNull IMModel imModel) {
        this.imModel = imModel;
    }

    @Override
    protected void executeUseCase(ImLogoutCase.RequestValues requestValues) {
        imModel.logout(new Callback() {
            @Override
            public void onSuccess(Object... data) {
                getUseCaseCallback().onSuccess(new ImLogoutCase.ResponseValue());
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
    }

    public static final class ResponseValue implements BaseUseCase.ResponseValue {
    }
}
