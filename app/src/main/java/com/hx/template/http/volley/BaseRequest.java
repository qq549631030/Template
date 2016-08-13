package com.hx.template.http.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Created by huangxiang on 16/6/4.
 */
public abstract class BaseRequest<T> extends Request<T> {

    private static final String TAG = "BaseRequest";

    private Map<String, String> headers;

    private Map<String, String> params;

    private String paramEncoding;

    private String contentType;

    private byte[] body;

    public BaseRequest(int method, String url, Response.ErrorListener listener) {
        super(method, url, listener);
    }

    public BaseRequest headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public BaseRequest contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public BaseRequest params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public BaseRequest paramEncoding(String paramEncoding) {
        this.paramEncoding = paramEncoding;
        return this;
    }

    public BaseRequest body(byte[] body) {
        this.body = body;
        return this;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> finalHeaders = super.getHeaders();
        if (null == finalHeaders || finalHeaders.equals(Collections.emptyMap())) {
            finalHeaders = new HashMap<String, String>();
        }
        if (headers != null) {
            finalHeaders.putAll(headers);
        }
        Log.d(TAG, "request headers " + finalHeaders.toString());
        return finalHeaders;
    }

    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> finalParams = super.getParams();
        if (null == finalParams || finalParams.equals(Collections.emptyMap())) {
            finalParams = new HashMap<String, String>();
        }
        if (params != null) {
            finalParams.putAll(params);
        }
        Log.d(TAG, "request params: " + finalParams.toString());
        return finalParams;
    }

    public String getParamEncoding() {
        if (paramEncoding != null) {
            return paramEncoding;
        }
        return super.getParamsEncoding();
    }

    @Override
    public String getBodyContentType() {
        if (contentType != null) {
            return contentType;
        }
        return super.getBodyContentType();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (body != null) {
            return body;
        }
        return super.getBody();
    }

    public NetworkResponse handleGZip(NetworkResponse resopnse) throws IOException {
        Map<String, String> headers = resopnse.headers;
        String contentEncoding = headers.get("Content-Encoding");
        if (contentEncoding != null && contentEncoding.equals("gzip")) {
            byte[] decodedData = decodeGZip(resopnse.data);
            headers.remove("Content-Encoding");
            return new NetworkResponse(resopnse.statusCode, decodedData, headers, resopnse.notModified, resopnse.networkTimeMs);
        }
        return resopnse;
    }


    public byte[] decodeGZip(byte[] data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[10240];
        int readLen = 0;
        try {
            GZIPInputStream gzipIs = new GZIPInputStream(new ByteArrayInputStream(data));
            while ((readLen = gzipIs.read(buffer, 0, buffer.length)) > 0) {
                baos.write(buffer, 0, readLen);
            }
            baos.flush();
            byte[] dataBytes = baos.toByteArray();
            return Arrays.copyOf(dataBytes, dataBytes.length);
        } finally {
            baos.close();
        }
    }
}
