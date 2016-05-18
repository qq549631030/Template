package com.hx.template.http.retrofit;

import com.hx.template.http.HttpReturn;

import rx.functions.Func1;

/**
 * Created by huangx on 2016/5/18.
 */
public class ErrorHandle implements Func1<HttpReturn.BaseReturn, HttpReturn.BaseReturn> {
    @Override
    public HttpReturn.BaseReturn call(HttpReturn.BaseReturn baseReturn) {

        return null;
    }
}
