package com.hx.template.http.volley;

import com.android.volley.Response;

/**
 * Created by huangx on 2016/4/29.
 */
public class FormGetBackStringRequest extends BaseFormRequest<String> {
    public FormGetBackStringRequest(String url, Response.Listener listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, listener, errorListener);
    }

    @Override
    protected String converResult(String result) {
        return result;
    }
}
