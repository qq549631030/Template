package com.hx.template.http;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.hx.template.utils.LogUtils;

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
 * 以表单形式提交GET请求
 * 
 * @author huangxiang
 *
 */
public class FormGetRequest extends Request<JSONObject> {

	private final String TAG = FormGetRequest.class.getSimpleName();

	private Listener<JSONObject> mListener;

	public FormGetRequest(String url, Listener<JSONObject> listener,
			ErrorListener errorListener) {
		super(Request.Method.GET, url, errorListener);
		mListener = listener;
	}

	@Override
	protected void deliverResponse(JSONObject response) {
		mListener.onResponse(response);
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = super.getHeaders();
		if (null == headers || headers.equals(Collections.emptyMap())) {
			headers = new HashMap<String, String>();
		}
		headers.put("Charset", "UTF-8");
		headers.put("Accept-Encoding", "gzip,deflate");
		LogUtils.i(TAG, "send headers = " + headers.toString());
		return headers;
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		try {
			LogUtils.i(TAG, "receive headers = " + response.headers.toString());
			String jsonString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			// 解析gzip传输
			if (response.headers.containsKey("Content-Encoding")) {
				String encoding = response.headers.get("Content-Encoding");
				if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
					jsonString = decodeGZip(response.data);
				}
			}
			LogUtils.i(TAG, "response = " + jsonString);
			return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
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
