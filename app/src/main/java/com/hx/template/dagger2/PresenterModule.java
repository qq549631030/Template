package com.hx.template.dagger2;

import com.hx.template.mvp.usecase.complex.BindPhoneCase;
import com.hx.template.mvp.usecase.complex.UpdateAvatarCase;
import com.hx.template.mvp.usecase.single.sms.RequestSMSCodeCase;
import com.hx.template.mvp.usecase.single.user.LoginCase;
import com.hx.template.mvp.usecase.single.user.ModifyPwdCase;
import com.hx.template.mvp.usecase.single.user.RegisterCase;
import com.hx.template.mvp.usecase.single.user.ResetPwdByEmailCase;
import com.hx.template.mvp.usecase.single.user.ResetPwdBySMSCodeCase;
import com.hx.template.mvp.usecase.single.user.UpdateUserInfoCase;
import com.hx.template.mvp.presenter.BindEmailPresenter;
import com.hx.template.mvp.presenter.BindPhonePresenter;
import com.hx.template.mvp.presenter.LoginPresenter;
import com.hx.template.mvp.presenter.ModifyPwdPresenter;
import com.hx.template.mvp.presenter.PersonalInfoPresenter;
import com.hx.template.mvp.presenter.PersonalInfoUpdatePresenter;
import com.hx.template.mvp.presenter.RegisterPresenter;
import com.hx.template.mvp.presenter.ResetPwdByEmailPresenter;
import com.hx.template.mvp.presenter.ResetPwdByPhonePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * 功能说明：com.hx.template.dagger2
 * 作者：huangx on 2016/8/30 14:21
 * 邮箱：huangx@pycredit.cn
 */
@Module
public class PresenterModule {

    @Provides
    public LoginPresenter provideLoginPresenter(LoginCase loginCase) {
        return new LoginPresenter(loginCase);
    }

    @Provides
    public RegisterPresenter provideRegisterPresenter(RegisterCase registerCase) {
        return new RegisterPresenter(registerCase);
    }

    @Provides
    public ModifyPwdPresenter provideModifyPwdPresenter(ModifyPwdCase modifyPwdCase) {
        return new ModifyPwdPresenter(modifyPwdCase);
    }

    @Provides
    public PersonalInfoUpdatePresenter providePersonalInfoUpdatePresenter(UpdateUserInfoCase updateUserInfoCase) {
        return new PersonalInfoUpdatePresenter(updateUserInfoCase);
    }

    @Provides
    public BindEmailPresenter provideBindEmailPresenter(UpdateUserInfoCase updateUserInfoCase) {
        return new BindEmailPresenter(updateUserInfoCase);
    }

    @Provides
    public BindPhonePresenter provideBindPhonePresenter(RequestSMSCodeCase requestSMSCodeCase, BindPhoneCase bindPhoneCase) {
        return new BindPhonePresenter(requestSMSCodeCase, bindPhoneCase);
    }


    @Provides
    public ResetPwdByPhonePresenter provideResetPwdByPhonePresenter(RequestSMSCodeCase requestSMSCodeCase, ResetPwdBySMSCodeCase resetPwdBySMSCodeCase) {
        return new ResetPwdByPhonePresenter(requestSMSCodeCase, resetPwdBySMSCodeCase);
    }


    @Provides
    public ResetPwdByEmailPresenter provideResetPwdByEmailPresenter(ResetPwdByEmailCase resetPwdByEmailCase) {
        return new ResetPwdByEmailPresenter(resetPwdByEmailCase);
    }


    @Provides
    public PersonalInfoPresenter providePersonalInfoPresenter(UpdateAvatarCase updateAvatarCase) {
        return new PersonalInfoPresenter(updateAvatarCase);
    }
}
