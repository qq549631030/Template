package com.hx.template.http;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * 以表单形式提交GET请求
 * 返回结果为JSONObject
 *
 * @author huangxiang
 */
public class FormGetBackJsonRequest extends BaseFormRequest<JSONObject> {

    public FormGetBackJsonRequest(String url, Listener listener, ErrorListener errorListener) {
        super(Method.GET, url, listener, errorListener);
    }

    @Override
    protected JSONObject converResult(String result) {
        try {
            return new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return null;
    }
}
