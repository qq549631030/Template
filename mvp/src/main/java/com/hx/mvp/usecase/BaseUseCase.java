package com.hx.mvp.usecase;

import com.hx.mvp.Cancelable;

/**
 * 功能说明：用例
 * 作者：huangx on 2016/12/2 15:34
 * 邮箱：huangx@pycredit.cn
 */

public abstract class BaseUseCase<Q, P> implements Cancelable {
    private Q mRequestValues;

    private UseCaseCallback<P> mUseCaseCallback;

    public void setRequestValues(Q requestValues) {
        mRequestValues = requestValues;
    }

    public Q getRequestValues() {
        return mRequestValues;
    }

    public UseCaseCallback<P> getUseCaseCallback() {
        return mUseCaseCallback;
    }

    public void setUseCaseCallback(UseCaseCallback<P> useCaseCallback) {
        mUseCaseCallback = useCaseCallback;
    }

    public void run() {
        executeUseCase(mRequestValues);
    }

    protected abstract void executeUseCase(Q requestValues);

    public interface RequestValues {
    }

    public interface ResponseValue {
    }

    public interface UseCaseCallback<R> {
        /**
         * 用例执行成功
         *
         * @param response 用例返回结果
         * @return
         */
        void onSuccess(R response);

        /**
         * 用例执行失败
         *
         * @param errorCode 错误码
         * @param errorData 错误数据
         * @return
         */
        void onError(String errorCode, Object... errorData);

    }

}
