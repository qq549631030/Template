package com.hx.mvp;

/**
 * 功能说明：通用回调接口
 * 作者：huangx on 2016/12/2 14:57
 * 邮箱：549631030@qq.com
 */

public interface Callback {
    /**
     * 成功回调
     *
     * @param data 成功返回数据
     */
    void onSuccess(Object... data);

    /**
     * 失败回调
     *
     * @param errorCode 错误码
     * @param errorData 错误数据
     */
    void onFailure(String errorCode, Object... errorData);
}
