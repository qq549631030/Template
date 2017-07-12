package com.hx.template.dagger2;

import com.hx.template.mvp.usecase.complex.BindPhoneCase;
import com.hx.template.mvp.usecase.complex.UpdateAvatarCase;
import com.hx.template.mvp.usecase.single.file.UploadFileCase;
import com.hx.template.mvp.usecase.single.sms.RequestSMSCodeCase;
import com.hx.template.mvp.usecase.single.sms.VerifySmsCodeCase;
import com.hx.template.mvp.usecase.single.user.GetUserInfoCase;
import com.hx.template.mvp.usecase.single.user.LoginCase;
import com.hx.template.mvp.usecase.single.user.ModifyPwdCase;
import com.hx.template.mvp.usecase.single.user.RegisterCase;
import com.hx.template.mvp.usecase.single.user.RequestEmailVerifyCase;
import com.hx.template.mvp.usecase.single.user.ResetPwdByEmailCase;
import com.hx.template.mvp.usecase.single.user.ResetPwdBySMSCodeCase;
import com.hx.template.mvp.usecase.single.user.UpdateUserInfoCase;
import com.hx.template.model.FileModel;
import com.hx.template.model.SMSModel;
import com.hx.template.model.UserModel;

import dagger.Module;
import dagger.Provides;

/**
 * 功能说明：com.hx.template.dagger2
 * 作者：huangx on 2016/8/30 14:21
 * 邮箱：549631030@qq.com
 */
@Module
public class UseCaseModule {
    //=========single============//
    @Provides
    public static LoginCase provideLoginCase(UserModel userModel) {
        return new LoginCase(userModel);
    }

    @Provides
    public static RegisterCase provideRegisterCase(UserModel userModel) {
        return new RegisterCase(userModel);
    }

    @Provides
    public static ModifyPwdCase provideModifyPwdCase(UserModel userModel) {
        return new ModifyPwdCase(userModel);
    }

    @Provides
    public static GetUserInfoCase provideGetUserInfoCase(UserModel userModel) {
        return new GetUserInfoCase(userModel);
    }

    @Provides
    public static UpdateUserInfoCase provideUpdateUserInfoCase(UserModel userModel) {
        return new UpdateUserInfoCase(userModel);
    }

    @Provides
    public static RequestEmailVerifyCase provideRequestEmailVerifyCase(UserModel userModel) {
        return new RequestEmailVerifyCase(userModel);
    }

    @Provides
    public static ResetPwdBySMSCodeCase provideResetPwdBySMSCodeCase(UserModel userModel) {
        return new ResetPwdBySMSCodeCase(userModel);
    }

    @Provides
    public static ResetPwdByEmailCase provideResetPwdByEmailCase(UserModel userModel) {
        return new ResetPwdByEmailCase(userModel);
    }

    @Provides
    public static RequestSMSCodeCase provideRequestSMSCodeCase(SMSModel smsModel) {
        return new RequestSMSCodeCase(smsModel);
    }

    @Provides
    public static VerifySmsCodeCase provideVerifySmsCodeCase(SMSModel smsModel) {
        return new VerifySmsCodeCase(smsModel);
    }

    @Provides
    public static UploadFileCase provideUploadFileCase(FileModel fileModel) {
        return new UploadFileCase(fileModel);
    }

    //==============complex============//

    @Provides
    public static BindPhoneCase provideBindPhoneCase(UserModel userModel, SMSModel smsModel) {
        return new BindPhoneCase(userModel, smsModel);
    }

    @Provides
    public static UpdateAvatarCase provideUpdateAvatarCase(FileModel fileModel, UserModel userModel) {
        return new UpdateAvatarCase(fileModel, userModel);
    }
}
