package com.hx.template.mvp.usecase.single.im;

import android.support.annotation.NonNull;

import com.hx.mvp.Callback;
import com.hx.mvp.usecase.BaseUseCase;
import com.hx.template.model.IMModel;

import javax.inject.Inject;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/22 17:04
 * 邮箱：huangx@pycredit.cn
 */

public class IMAddContact extends BaseUseCase<IMAddContact.RequestValues, IMAddContact.ResponseValue> {

    private final IMModel imModel;

    @Inject
    public IMAddContact(@NonNull IMModel imModel) {
        this.imModel = imModel;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        imModel.addContact(requestValues.getUsername(), requestValues.getInviteMsg(), new Callback() {
            @Override
            public void onSuccess(Object... data) {
                getUseCaseCallback().onSuccess(new IMAddContact.ResponseValue());
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
        private final String inviteMsg;

        public RequestValues(@NonNull String username, String inviteMsg) {
            this.username = username;
            this.inviteMsg = inviteMsg;
        }

        public String getUsername() {
            return username;
        }

        public String getInviteMsg() {
            return inviteMsg;
        }
    }

    public static final class ResponseValue implements BaseUseCase.ResponseValue {
    }
}
