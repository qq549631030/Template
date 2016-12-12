package com.hx.template.mvp.usecase.single.file;

import com.hx.mvp.usecase.BaseUseCase;
import com.hx.template.model.FileModel;
import com.hx.template.model.FileUploadCallback;

import java.io.File;

import javax.inject.Inject;

/**
 * Created by huangxiang on 16/8/29.
 */
public class UploadFileCase extends BaseUseCase<UploadFileCase.RequestValues, UploadFileCase.ResponseValue> {

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
            public void onProgress(float progress) {

            }

            @Override
            public void onSuccess(Object... data) {
                if (data != null && data.length > 0 && data[0] instanceof String) {
                    getUseCaseCallback().onSuccess(new ResponseValue((String) data[0]));
                } else {
                    getUseCaseCallback().onError("-1", "上传失败");
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
        private final File file;

        public RequestValues(File file) {
            this.file = file;
        }

        public File getFile() {
            return file;
        }
    }

    public static final class ResponseValue implements BaseUseCase.ResponseValue {
        private final String fileUrl;

        public ResponseValue(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        public String getFileUrl() {
            return fileUrl;
        }
    }
}
