package com.hx.template.mvp.usecase.complex;

import com.hx.mvp.Callback;
import com.hx.mvp.usecase.BaseUseCase;
import com.hx.template.model.FileModel;
import com.hx.template.model.FileUploadCallback;
import com.hx.template.model.UserModel;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * 功能说明：com.hx.template.domain.usercase.complex
 * 作者：huangx on 2016/8/30 11:18
 * 邮箱：huangx@pycredit.cn
 */
public class UpdateAvatarCase extends BaseUseCase<UpdateAvatarCase.RequestValues, UpdateAvatarCase.ResponseValue> {
    private final FileModel fileModel;
    private final UserModel userModel;

    @Inject
    public UpdateAvatarCase(FileModel fileModel, UserModel userModel) {
        this.fileModel = fileModel;
        this.userModel = userModel;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {
        final String userId = requestValues.getUserId();
        File file = requestValues.getFile();
        fileModel.uploadFile(file, new FileUploadCallback() {

            @Override
            public void onProgress(float progress) {
            }

            @Override
            public void onSuccess(Object... data) {
                if (data != null && data.length > 0 && data[0] instanceof String) {
                    String url = (String) data[0];
                    Map<String, Object> updateMap = new HashMap<String, Object>();
                    updateMap.put("avatar", url);
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
                } else {
                    getUseCaseCallback().onError("-1", "文件上传失败");
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
        private final String userId;
        private final File file;

        public RequestValues(String userId, File file) {
            this.userId = userId;
            this.file = file;
        }

        public String getUserId() {
            return userId;
        }

        public File getFile() {
            return file;
        }
    }

    public static final class ResponseValue implements BaseUseCase.ResponseValue {

    }
}
