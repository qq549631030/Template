package com.hx.template.dagger2;

import com.hx.template.model.FileModel;
import com.hx.template.model.ModelManager;
import com.hx.template.model.SMSModel;
import com.hx.template.model.UserModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 功能说明：com.hx.template.dagger2
 * 作者：huangx on 2016/8/30 14:21
 * 邮箱：huangx@pycredit.cn
 */
@Module
public class ModelModule {
    @Provides
    @Singleton
    public UserModel provideUserModel() {
        return ModelManager.provideUserModel();
    }

    @Provides
    @Singleton
    public SMSModel provideSMSModel() {
        return ModelManager.provideSMSModel();
    }

    @Provides
    @Singleton
    public FileModel provideFileModel() {
        return ModelManager.provideFileModel();
    }
}
