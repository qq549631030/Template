package com.hx.template.http.volley;

import com.android.volley.Request;
import com.android.volley.Response;

/**
 * Created by huangx on 2016/4/29.
 */
public class FormPostBackStringRequest extends BaseFormRequest<String> {

    public FormPostBackStringRequest( String url, Response.Listener listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST, url, listener, errorListener);
    }

    @Override
    protected String converResult(String result) {
        return result;
    }
}
