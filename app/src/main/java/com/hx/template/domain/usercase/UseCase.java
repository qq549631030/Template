package com.hx.template.domain.usercase;

import com.hx.template.model.Cancelable;

/**
 * 功能说明：用例基类
 * 作者：huangx on 2016/8/26 17:01
 * 邮箱：huangx@pycredit.cn
 */
public abstract class UseCase<Q extends UseCase.RequestValues, P extends UseCase.ResponseValue> implements Cancelable{

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

    public abstract void executeUseCase(Q requestValues);

    /**
     * 取消操作
     *
     * @param args
     * @return
     */
    @Override
    public boolean cancel(Object... args) {
        return true;
    }

    /**
     * Data passed to a request.
     */
    public interface RequestValues {
    }

    /**
     * Data received from a request.
     */
    public interface ResponseValue {
    }

    public interface UseCaseCallback<R> {
        void onSuccess(R response);

        void onError(String errorCode, String errorMsg);
    }
}
