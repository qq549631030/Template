package com.hx.template.mvp.contract;

import com.hx.template.mvp.MvpView;
import com.hx.template.mvp.Presenter;

/**
 * Created by huangx on 2016/8/22.
 */
public interface PersonalInfoUpdateContract {
    interface View extends MvpView {
        /**
         * 获取要修改的用户id
         *
         * @return
         */
        String getUserId();

        /**
         * 获取要修改的值
         *
         * @return
         */
        String getNewData();

        /**
         * 获取要修改的字段类型
         *
         * @return
         */
        int getDataType();

        /**
         * 修改成功
         */
        void updateSuccess();

        /**
         * 修改失败
         *
         * @param errorCode 错误码
         * @param errorMsg  错误信息
         */
        void updateFail(String errorCode, String errorMsg);
    }

    interface MvpPresenter extends Presenter<View> {
        /**
         * 修改个人信息
         */
        void updateInfo();
    }
}
