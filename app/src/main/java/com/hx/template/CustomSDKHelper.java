package com.hx.template;

import com.hx.easemob.DefaultSDKHelper;
import com.hx.easemob.model.UserProfileManager;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/20 20:36
 * 邮箱：huangx@pycredit.cn
 */

public class CustomSDKHelper extends DefaultSDKHelper {
    @Override
    protected UserProfileManager createUserProfileManager() {
        return new CustomUserProfileManager();
    }
}
