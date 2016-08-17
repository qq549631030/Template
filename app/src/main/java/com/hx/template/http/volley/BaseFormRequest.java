/*
 *  Copyright &amp;copy; 2014-2016  All rights reserved.
 *  Licensed under the 深圳中盟燧石科技 License, Version 1.0 (the "License");
 *
 */

package com.hx.template.http.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.hx.template.global.HXLog;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Created by huangxiang on 15/12/15.
 */
public abstract class BaseFormRequest<T> extends Request<T> {

    private ResponseCacheListener responseCacheListener;

    private Response.Listener<T> mListener;
    private Map<String, String> additionalHeaders;
    private Map<String, String> additionalParams;

    public BaseFormRequest(int method, String url, Response.Listener listener, Response.ErrorListener errorListener) {
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
        if (additionalParams != null) {
            if (null == params || params.equals(Collections.emptyMap())) {
                params = new HashMap<String, String>();
            }
            params.putAll(additionalParams);
        }
        return params;
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String result = null;
            if (response.headers.containsKey("Content-Encoding")) {
                String encoding = response.headers.get("Content-Encoding");
                if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
                    // 解析gzip传输
                    result = decodeGZip(response.data);
                } else {
                    result = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                }
            } else {
                result = new String(response.data,
                        HttpHeaderParser.parseCharset(response.headers));
            }

            Cache.Entry cache = HttpHeaderParser.parseCacheHeaders(response);
            //通知监听器
            notifyResponseCache(cache);

            HXLog.i("response headers = " + cache.responseHeaders.toString());
            HXLog.i("response body = " + result);

            T rep = converResult(result);
            if (rep != null) {
                return Response.success(rep, cache);
            } else {
                return Response.error(new ParseError(new NullPointerException()));
            }
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
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

    //返回结果转换
    protected abstract T converResult(String result);


    protected String decodeGZip(byte[] data) {
        InputStream in;
        StringBuilder sb = new StringBuilder();
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            in = new GZIPInputStream(bis);
            BufferedReader r = new BufferedReader(new InputStreamReader(in, "UTF-8"),
                    1000);
            for (String line = r.readLine(); line != null; line = r.readLine()) {
                sb.append(line);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
