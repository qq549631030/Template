package com.hx.template.mvp.usecase.single.sms;

import com.hx.mvp.Callback;
import com.hx.mvp.usecase.BaseUseCase;
import com.hx.template.model.SMSModel;

import javax.inject.Inject;

/**
 * Created by huangxiang on 16/8/29.
 */
public class VerifySmsCodeCase extends BaseUseCase<VerifySmsCodeCase.RequestValues, VerifySmsCodeCase.ResponseValue> {
    private final SMSModel smsModel;

    @Inject
    public VerifySmsCodeCase(SMSModel smsModel) {
        this.smsModel = smsModel;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {
        String phoneNumber = requestValues.getPhoneNumber();
        String smsCode = requestValues.getSmsCode();
        smsModel.verifySmsCode(phoneNumber, smsCode, new Callback() {
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
        private final String phoneNumber;
        private final String smsCode;

        public RequestValues(String phoneNumber, String smsCode) {
            this.phoneNumber = phoneNumber;
            this.smsCode = smsCode;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getSmsCode() {
            return smsCode;
        }
    }

    public static final class ResponseValue implements BaseUseCase.ResponseValue {

    }
}
