/*
 *  Copyright &amp;copy; 2014-2016  All rights reserved.
 *  Licensed under the 深圳中盟燧石科技 License, Version 1.0 (the "License");
 *
 */

package com.hx.template.http;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

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
public class BaseJsonObjectRequest extends JsonObjectRequest {

    private ResponseCacheListener responseCacheListener;

    private Map<String, String> additionalHeaders;

    public BaseJsonObjectRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }


    public void setResponseCacheListener(ResponseCacheListener responseCacheListener) {
        this.responseCacheListener = responseCacheListener;
    }

    public void setAdditionalHeaders(Map<String, String> additionalHeaders) {
        this.additionalHeaders = additionalHeaders;
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

    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
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
            }
            Cache.Entry cache = HttpHeaderParser.parseCacheHeaders(response);
            //通知监听器
            notifyResponseCache(cache);
            return Response.success(new JSONObject(result), cache);
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
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
        responseCacheListener = null;
    }

    private String decodeGZip(byte[] data) {
        InputStream in;
        StringBuilder sb = new StringBuilder();
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            in = new GZIPInputStream(bis);
            BufferedReader r = new BufferedReader(new InputStreamReader(in),
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
