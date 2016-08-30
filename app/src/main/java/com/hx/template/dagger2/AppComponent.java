package com.hx.template.dagger2;

import com.hx.template.mvp.presenter.EmailStatePresenter;
import com.hx.template.mvp.presenter.LoginPresenter;
import com.hx.template.mvp.presenter.BindEmailPresenter;
import com.hx.template.mvp.presenter.BindPhonePresenter;
import com.hx.template.mvp.presenter.ModifyPwdPresenter;
import com.hx.template.mvp.presenter.PersonalInfoPresenter;
import com.hx.template.mvp.presenter.PersonalInfoUpdatePresenter;
import com.hx.template.mvp.presenter.RegisterPresenter;
import com.hx.template.mvp.presenter.ResetPwdByEmailPresenter;
import com.hx.template.mvp.presenter.ResetPwdByPhonePresenter;
import com.hx.template.mvp.presenter.VerifyPhonePresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by huangxiang on 16/8/21.
 */
@Component(modules = {AppModule.class, ModelModule.class, PresenterModule.class, UseCaseModule.class})
@Singleton
public interface AppComponent {

    LoginPresenter loginPresenter();

    RegisterPresenter registerPresenter();

    ModifyPwdPresenter modifyPwdPresenter();

    PersonalInfoUpdatePresenter personalInfoUpdatePresenter();

    BindEmailPresenter bindEmailPresenter();

    EmailStatePresenter emailStatePresenter();

    BindPhonePresenter bindPhonePresenter();

    VerifyPhonePresenter verifyPhonePresenter();

    ResetPwdByPhonePresenter resetPwdByPhonePresenter();

    ResetPwdByEmailPresenter resetPwdByEmailPresenter();

    PersonalInfoPresenter personalInfoPresenter();
}
