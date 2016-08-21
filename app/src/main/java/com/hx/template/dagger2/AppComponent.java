package com.hx.template.dagger2;

import com.hx.template.mvpview.impl.ModifyPwdMvpView;
import com.hx.template.presenter.impl.BindEmailPresenter;
import com.hx.template.presenter.impl.BindPhonePresenter;
import com.hx.template.presenter.impl.LoginPresenter;
import com.hx.template.presenter.impl.ModifyPwdPresenter;
import com.hx.template.presenter.impl.PersonalInfoPresenter;
import com.hx.template.presenter.impl.PersonalInfoUpdatePresenter;
import com.hx.template.presenter.impl.RegisterPresenter;
import com.hx.template.presenter.impl.ResetPwdByEmailPresenter;
import com.hx.template.presenter.impl.ResetPwdByPhonePresenter;

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
