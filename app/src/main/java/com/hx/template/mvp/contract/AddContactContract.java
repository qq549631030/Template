package com.hx.template.mvp.contract;

import com.hx.mvp.presenter.Presenter;
import com.hx.mvp.view.BaseMvpView;

/**
 * 功能说明：
 * 作者：huangx on 2016/12/22 17:10
 * 邮箱：549631030@qq.com
 */

public interface AddContactContract {
    interface View extends BaseMvpView {
        /**
         * 获取用户名
         *
         * @return
         */
        String getUserName();

        /**
         * 请求成功
         */
        void onRequestSuccess();

        /**
         * 请求失败
         *
         * @param errorCode 错误码
         * @param errorMsg  错误信息
         */
        void onRequestFail(String errorCode, String errorMsg);
    }

    interface MvpPresenter extends Presenter<BindEmailContract.View> {
        /**
         * 添加联系人
         */
        void addContact(String userId);
    }
}
