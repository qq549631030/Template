package com.hx.template.mvp.usecase.single.sms;

import com.hx.mvp.Callback;
import com.hx.mvp.usecase.BaseUseCase;
import com.hx.template.model.SMSModel;

import javax.inject.Inject;

/**
 * Created by huangxiang on 16/8/29.
 */
public class RequestSMSCodeCase extends BaseUseCase<RequestSMSCodeCase.RequestValues, RequestSMSCodeCase.ResponseValue> {
    private final SMSModel smsModel;

    @Inject
    public RequestSMSCodeCase(SMSModel smsModel) {
        this.smsModel = smsModel;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {
        String phoneNumber = requestValues.getPhoneNumber();
        String template = requestValues.getTemplate();
        smsModel.requestSMSCode(phoneNumber, template, new Callback() {
            @Override
            public void onSuccess(Object... data) {
                if (data != null && data.length > 0 && data[0] instanceof Integer) {
                    getUseCaseCallback().onSuccess(new ResponseValue((Integer) data[0]));
                } else {
                    getUseCaseCallback().onSuccess(new ResponseValue(null));
                }
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

    public static final class ResponseValue implements BaseUseCase.ResponseValue {
        private final Integer smsId;

        public ResponseValue(Integer smsId) {
            this.smsId = smsId;
        }

        public Integer getSmsId() {
            return smsId;
        }
    }
}
