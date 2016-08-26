package com.hx.template.domain.usecase;

import com.hx.template.domain.usercase.user.LoginCase;
import com.hx.template.model.ModelManager;
import com.hx.template.model.UserModel;

/**
 * 功能说明：com.hx.template.domain.usecase
 * 作者：huangx on 2016/8/26 17:59
 * 邮箱：huangx@pycredit.cn
 */
public class UseCaseManager {
    public static LoginCase provideLoginCase() {
        return new LoginCase(ModelManager.newUserModel());
    }
}
