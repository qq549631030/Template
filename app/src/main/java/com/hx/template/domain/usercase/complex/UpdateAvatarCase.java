package com.hx.template.domain.usercase.complex;

import com.hx.template.domain.usercase.UseCase;
import com.hx.template.model.Callback;
import com.hx.template.model.FileModel;
import com.hx.template.model.FileUploadCallback;
import com.hx.template.model.TaskManager;
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
public class UpdateAvatarCase extends UseCase<UpdateAvatarCase.RequestValues, UpdateAvatarCase.ResponseValue> {
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
            public void onProgress(int taskId, float progress) {
                if (taskId == TaskManager.TASK_ID_UPLOAD_FILE) {

                }
            }

            @Override
            public void onSuccess(int taskId, Object... data) {
                if (taskId == TaskManager.TASK_ID_UPLOAD_FILE) {
                    if (data != null && data.length > 0 && data[0] instanceof String) {
                        String url = (String) data[0];
                        Map<String, Object> updateMap = new HashMap<String, Object>();
                        updateMap.put("avatar", url);
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
                    } else {
                        getUseCaseCallback().onError("-1", "文件上传失败");
                    }
                }
            }

            @Override
            public void onFailure(int taskId, String errorCode, String errorMsg) {
                if (taskId == TaskManager.TASK_ID_UPLOAD_FILE) {
                    getUseCaseCallback().onError(errorCode, errorMsg);
                }
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {
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

    public static final class ResponseValue implements UseCase.ResponseValue {

    }
}
