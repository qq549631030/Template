package com.hx.template.mvp.contract;

import com.hx.mvp.presenter.Presenter;
import com.hx.mvp.view.BaseMvpView;

/**
 * Created by huangx on 2016/8/22.
 */
public interface ModifyPwdContract {
    interface View extends BaseMvpView {
        /**
         * 获取旧密码
         *
         * @return
         */
        String getOldPwd();

        /**
         * 获取新密码
         *
         * @return
         */
        String getNewPwd();

        /**
         * 获取新密码
         *
         * @return
         */
        String getConfirmPwd();

        /**
         * 修改成功
         */
        void modifySuccess();

        /**
         * 修改失败
         *
         * @param errorCode 错误码
         * @param errorMsg  错误信息
         */
        void modifyFail(String errorCode, String errorMsg);
    }

    interface MvpPresenter extends Presenter<View> {
        /**
         * 修改密码
         */
        void modifyPwd();
    }
}
