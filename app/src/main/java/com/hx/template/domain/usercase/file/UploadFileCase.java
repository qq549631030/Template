package com.hx.template.domain.usercase.file;

import com.hx.template.domain.usercase.UseCase;
import com.hx.template.model.Callback;
import com.hx.template.model.FileModel;
import com.hx.template.model.TaskManager;

import java.io.File;

/**
 * Created by huangxiang on 16/8/29.
 */
public class UploadFileCase extends UseCase<UploadFileCase.RequestValues, UploadFileCase.ResponseValue> {

    private final FileModel fileModel;

    public UploadFileCase(FileModel fileModel) {
        this.fileModel = fileModel;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {
        File file = requestValues.getFile();
        fileModel.uploadFile(file, new Callback() {
            @Override
            public void onSuccess(int taskId, Object... data) {
                if (taskId == TaskManager.TASK_ID_UPLOAD_FILE) {
                    getUseCaseCallback().onSuccess(new ResponseValue());
                }
            }

            @Override
            public void onFailure(int taskId, String errorCode, Object... errorMsg) {
                if (taskId == TaskManager.TASK_ID_UPLOAD_FILE) {
                    getUseCaseCallback().onError(errorCode, errorMsg.toString());
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

    }
}
