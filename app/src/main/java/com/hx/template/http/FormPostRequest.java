package com.hx.template.http;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;

/**
 * 以表单形式提交POST请求
 *
 * @author huangxiang
 */
public class FormPostRequest extends BaseRequest<JSONObject> {

    private final String TAG = FormPostRequest.class.getSimpleName();


    public FormPostRequest(String url, Listener<JSONObject> listener,
                           ErrorListener errorListener) {
        super(Request.Method.POST, url, listener, errorListener);
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
