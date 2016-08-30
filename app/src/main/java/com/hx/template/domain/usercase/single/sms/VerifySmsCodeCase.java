package com.hx.template.domain.usercase.single.sms;

import com.hx.template.domain.usercase.UseCase;
import com.hx.template.model.Callback;
import com.hx.template.model.SMSModel;
import com.hx.template.model.TaskManager;

import javax.inject.Inject;

/**
 * Created by huangxiang on 16/8/29.
 */
public class VerifySmsCodeCase extends UseCase<VerifySmsCodeCase.RequestValues, VerifySmsCodeCase.ResponseValue> {
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
            public void onSuccess(int taskId, Object... data) {
                if (taskId == TaskManager.TASK_ID_VERIFY_SMS_CODE) {
                    getUseCaseCallback().onSuccess(new ResponseValue());
                }
            }

            @Override
            public void onFailure(int taskId, String errorCode, String errorMsg) {
                if (taskId == TaskManager.TASK_ID_VERIFY_SMS_CODE) {
                    getUseCaseCallback().onError(errorCode, errorMsg);
                }
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
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

    public static final class ResponseValue implements UseCase.ResponseValue {

    }
}