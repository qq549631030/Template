package com.hx.template.domain.usercase.single.file;

import com.hx.template.domain.usercase.UseCase;
import com.hx.template.model.FileModel;
import com.hx.template.model.FileUploadCallback;
import com.hx.template.model.TaskManager;

import java.io.File;

import javax.inject.Inject;

/**
 * Created by huangxiang on 16/8/29.
 */
public class UploadFileCase extends UseCase<UploadFileCase.RequestValues, UploadFileCase.ResponseValue> {

    private final FileModel fileModel;

    @Inject
    public UploadFileCase(FileModel fileModel) {
        this.fileModel = fileModel;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {
        File file = requestValues.getFile();
        fileModel.uploadFile(file, new FileUploadCallback() {
            @Override
            public void onProgress(int taskId, float progress) {

            }

            @Override
            public void onSuccess(int taskId, Object... data) {
                if (taskId == TaskManager.TASK_ID_UPLOAD_FILE) {
                    if (data != null && data.length > 0 && data[0] instanceof String) {
                        getUseCaseCallback().onSuccess(new ResponseValue((String) data[0]));
                    } else {
                        getUseCaseCallback().onError("-1", "上传失败");
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
        private final File file;

        public RequestValues(File file) {
            this.file = file;
        }

        public File getFile() {
            return file;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {
        private final String fileUrl;

        public ResponseValue(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        public String getFileUrl() {
            return fileUrl;
        }
    }
}
