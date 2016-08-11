package com.hx.template.http.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 文件上传
 *
 * @author huangxiang
 */
public class MultipartRequest extends BaseFormRequest<JSONObject> {

    private MultipartRequestParams params = null;
    private MultipartEntity httpEntity = null;

    public MultipartRequest(String url, MultipartRequestParams params, Listener listener, ErrorListener errorListener) {
        super(Method.POST, url, listener, errorListener);
        this.params = params;
    }


    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (params != null) {
            httpEntity = params.getEntity();
            try {
                if (httpEntity != null) {
                    httpEntity.writeTo(baos);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return baos.toByteArray();
    }


    @Override
    public String getBodyContentType() {
        return httpEntity.getContentType();
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
