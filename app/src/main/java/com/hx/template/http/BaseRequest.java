/*
 *  Copyright &amp;copy; 2014-2016  All rights reserved.
 *  Licensed under the 深圳中盟燧石科技 License, Version 1.0 (the "License");
 *
 */

package com.hx.template.http;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangxiang on 15/12/15.
 */
public abstract class BaseRequest<T> extends Request<T> {

    private ResponseCacheListener responseCacheListener;

    private Response.Listener<T> mListener;
    private Map<String, String> additionalHeaders;
    private Map<String, String> additionalParams;

    public BaseRequest(int method, String url, Response.Listener listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    public void setResponseCacheListener(ResponseCacheListener responseCacheListener) {
        this.responseCacheListener = responseCacheListener;
    }

    public void setAdditionalHeaders(Map<String, String> additionalHeaders) {
        this.additionalHeaders = additionalHeaders;
    }

    public void setAdditionalParams(Map<String, String> additionalParams) {
        this.additionalParams = additionalParams;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();
        if (null == headers || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }
        if (additionalHeaders != null) {
            headers.putAll(additionalHeaders);
        }
        return headers;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = super.getParams();
        if (null == params || params.equals(Collections.emptyMap())) {
            params = new HashMap<String, String>();
        }
        if (additionalParams != null) {
            params.putAll(additionalParams);
        }
        return params;
    }

    @Override
    protected void deliverResponse(T response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }

    protected void notifyResponseCache(Cache.Entry cache) {
        if (responseCacheListener != null) {
            responseCacheListener.onResponse(cache);
        }
    }

    @Override
    protected void onFinish() {
        super.onFinish();
        mListener = null;
        responseCacheListener = null;
    }
}
