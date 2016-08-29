package com.hx.template.domain.usercase.sms;

import com.hx.template.domain.usercase.UseCase;
import com.hx.template.model.Callback;
import com.hx.template.model.SMSModel;
import com.hx.template.model.TaskManager;

/**
 * Created by huangxiang on 16/8/29.
 */
public class RequestSMSCodeCase extends UseCase<RequestSMSCodeCase.RequestValues, RequestSMSCodeCase.ResponseValue> {
    private final SMSModel smsModel;

    public RequestSMSCodeCase(SMSModel smsModel) {
        this.smsModel = smsModel;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {
        String phoneNumber = requestValues.getPhoneNumber();
        String template = requestValues.getTemplate();
        smsModel.requestSMSCode(phoneNumber, template, new Callback() {
            @Override
            public void onSuccess(int taskId, Object... data) {
                if (taskId == TaskManager.TASK_ID_REQUEST_SMS_CODE) {
                    getUseCaseCallback().onSuccess(new ResponseValue());
                }
            }

            @Override
            public void onFailure(int taskId, String errorCode, Object... errorMsg) {
                if (taskId == TaskManager.TASK_ID_REQUEST_SMS_CODE) {
                    getUseCaseCallback().onError(errorCode, errorMsg.toString());
                }
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
        private final String phoneNumber;
        private final String template;

        public RequestValues(String phoneNumber, String template) {
            this.phoneNumber = phoneNumber;
            this.template = template;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getTemplate() {
            return template;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

    }
}
