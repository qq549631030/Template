package com.hx.template.dagger2;

import android.content.Context;

import com.hx.template.domain.usecase.UseCaseManager;
import com.hx.template.domain.usercase.user.RegisterCase;
import com.hx.template.model.FileModel;
import com.hx.template.model.ModelManager;
import com.hx.template.model.SMSModel;
import com.hx.template.model.UserModel;
import com.hx.template.mvp.presenter.LoginPresenter;
import com.hx.template.mvp.presenter.BindEmailPresenter;
import com.hx.template.mvp.presenter.BindPhonePresenter;
import com.hx.template.mvp.presenter.ModifyPwdPresenter;
import com.hx.template.mvp.presenter.PersonalInfoPresenter;
import com.hx.template.mvp.presenter.PersonalInfoUpdatePresenter;
import com.hx.template.mvp.presenter.RegisterPresenter;
import com.hx.template.mvp.presenter.ResetPwdByEmailPresenter;
import com.hx.template.mvp.presenter.ResetPwdByPhonePresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by huangxiang on 16/8/21.
 */
@Module
public class AppModule {

    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext() {
        return context;
    }

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

    @Provides
    public LoginPresenter provideLoginPresenter() {
        return new LoginPresenter(UseCaseManager.provideLoginCase());
    }

    @Provides
    public RegisterPresenter provideRegisterPresenter() {
        return new RegisterPresenter(UseCaseManager.provideRegisterCase());
    }

    @Provides
    public ModifyPwdPresenter provideModifyPwdPresenter(UserModel userModel) {
        return new ModifyPwdPresenter(userModel);
    }

    @Provides
    public PersonalInfoUpdatePresenter providePersonalInfoUpdatePresenter(UserModel userModel) {
        return new PersonalInfoUpdatePresenter(userModel);
    }

    @Provides
    public BindEmailPresenter provideBindEmailPresenter(UserModel userModel) {
        return new BindEmailPresenter(userModel);
    }

    @Provides
    public BindPhonePresenter provideBindPhonePresenter(SMSModel smsModel, UserModel userModel) {
        return new BindPhonePresenter(smsModel, userModel);
    }


    @Provides
    public ResetPwdByPhonePresenter provideResetPwdByPhonePresenter(SMSModel smsModel, UserModel userModel) {
        return new ResetPwdByPhonePresenter(smsModel, userModel);
    }


    @Provides
    public ResetPwdByEmailPresenter provideResetPwdByEmailPresenter(UserModel userModel) {
        return new ResetPwdByEmailPresenter(userModel);
    }


    @Provides
    public PersonalInfoPresenter providePersonalInfoPresenter(UserModel userModel, FileModel fileModel) {
        return new PersonalInfoPresenter(userModel, fileModel);
    }
}
