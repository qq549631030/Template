package com.hx.mvp;

/**
 * 功能说明：取消操作
 * 作者：huangx on 2016/12/2 15:05
 * 邮箱：549631030@qq.com
 */

public interface Cancelable {
    /**
     * 取消操作
     *
     * @param args 参数
     */
    boolean cancel(Object... args);
}
