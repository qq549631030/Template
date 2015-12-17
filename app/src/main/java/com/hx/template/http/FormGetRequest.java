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
 *
 * @author huangxiang
 */
public class FormGetRequest extends BaseFormRequest<JSONObject> {

    public FormGetRequest( String url, Listener listener, ErrorListener errorListener) {
        super(Method.GET, url, listener, errorListener);
    }

    @Override
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
