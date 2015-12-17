package com.hx.template.http;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 以表单形式提交POST请求
 *
 * @author huangxiang
 */
public class FormPostRequest extends BaseFormRequest<JSONObject> {

    private final String TAG = FormPostRequest.class.getSimpleName();


    public FormPostRequest(String url, Listener<JSONObject> listener,
                           ErrorListener errorListener) {
        super(Request.Method.POST, url, listener, errorListener);
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
