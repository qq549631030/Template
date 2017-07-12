package com.hx.template.model;

/**
 * 功能说明：可取消的
 * 作者：huangx on 2016/9/22 10:56
 * 邮箱：549631030@qq.com
 */

public interface Cancelable {
    /**
     * 取消操作
     *
     * @param args
     * @return
     */
    boolean cancel(Object... args);
}
