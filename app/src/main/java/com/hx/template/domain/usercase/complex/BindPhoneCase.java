package com.hx.template.domain.usercase.complex;

import com.hx.template.domain.usercase.UseCase;
import com.hx.template.model.Callback;
import com.hx.template.model.SMSModel;
import com.hx.template.model.TaskManager;
import com.hx.template.model.UserModel;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * 功能说明：com.hx.template.domain.usercase.complex
 * 作者：huangx on 2016/8/30 10:58
 * 邮箱：huangx@pycredit.cn
 */
public class BindPhoneCase extends UseCase<BindPhoneCase.RequestValues, BindPhoneCase.ResponseValue> {
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
            public void onSuccess(int taskId, Object... data) {
                if (taskId == TaskManager.TASK_ID_VERIFY_SMS_CODE) {
                    Map<String, Object> updateMap = new HashMap<String, Object>();
                    updateMap.put("mobilePhoneNumber", phoneNumber);
                    updateMap.put("mobilePhoneNumberVerified", Boolean.TRUE);
                    userModel.updateUserInfo(userId, updateMap, new Callback() {
                        @Override
                        public void onSuccess(int taskId, Object... data) {
                            if (taskId == TaskManager.TASK_ID_UPDATE_USER_INFO) {
                                getUseCaseCallback().onSuccess(new ResponseValue());
                            }
                        }

                        @Override
                        public void onFailure(int taskId, String errorCode, String errorMsg) {
                            if (taskId == TaskManager.TASK_ID_UPDATE_USER_INFO) {
                                getUseCaseCallback().onError(errorCode, errorMsg);
                            }
                        }
                    });
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

    public static final class ResponseValue implements UseCase.ResponseValue {

    }
}
