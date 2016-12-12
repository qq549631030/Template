package com.hx.template.mvp.usecase.complex;

import com.hx.mvp.Callback;
import com.hx.mvp.usecase.BaseUseCase;
import com.hx.template.model.SMSModel;
import com.hx.template.model.UserModel;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * 功能说明：com.hx.template.domain.usercase.complex
 * 作者：huangx on 2016/8/30 10:58
 * 邮箱：huangx@pycredit.cn
 */
public class BindPhoneCase extends BaseUseCase<BindPhoneCase.RequestValues, BindPhoneCase.ResponseValue> {
    private final UserModel userModel;
    private final SMSModel smsModel;

    @Inject
    public BindPhoneCase(UserModel userModel, SMSModel smsModel) {
        this.userModel = userModel;
        this.smsModel = smsModel;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {
        final String phoneNumber = requestValues.getPhoneNumber();
        String smsCode = requestValues.getSmsCode();
        final String userId = requestValues.getUserId();
        smsModel.verifySmsCode(phoneNumber, smsCode, new Callback() {
            @Override
            public void onSuccess(Object... data) {
                Map<String, Object> updateMap = new HashMap<String, Object>();
                updateMap.put("mobilePhoneNumber", phoneNumber);
                updateMap.put("mobilePhoneNumberVerified", Boolean.TRUE);
                userModel.updateUserInfo(userId, updateMap, new Callback() {
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
        private final String userId;

        public RequestValues(String phoneNumber, String smsCode, String userId) {
            this.phoneNumber = phoneNumber;
            this.smsCode = smsCode;
            this.userId = userId;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getSmsCode() {
            return smsCode;
        }

        public String getUserId() {
            return userId;
        }
    }

    public static final class ResponseValue implements BaseUseCase.ResponseValue {

    }
}
