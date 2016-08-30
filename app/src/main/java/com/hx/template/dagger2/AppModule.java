package com.hx.template.dagger2;

import android.content.Context;

import com.hx.template.domain.usercase.single.user.LoginCase;
import com.hx.template.domain.usercase.single.user.RegisterCase;
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
}
