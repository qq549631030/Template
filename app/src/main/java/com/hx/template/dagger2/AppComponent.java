package com.hx.template.dagger2;

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

import dagger.Component;

/**
 * Created by huangxiang on 16/8/21.
 */
@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    LoginPresenter loginPresenter();

    RegisterPresenter registerPresenter();

    ModifyPwdPresenter modifyPwdPresenter();

    PersonalInfoUpdatePresenter personalInfoUpdatePresenter();

    BindEmailPresenter bindEmailPresenter();

    BindPhonePresenter bindPhonePresenter();

    ResetPwdByPhonePresenter resetPwdByPhonePresenter();

    ResetPwdByEmailPresenter resetPwdByEmailPresenter();

    PersonalInfoPresenter personalInfoPresenter();
}
