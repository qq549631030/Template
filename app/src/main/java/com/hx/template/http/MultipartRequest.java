package com.hx.template.http;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.hx.template.CustomApplication;
import com.hx.template.utils.LogUtils;

import org.apache.http.HttpEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传
 * 
 * @author huangxiang
 * 
 */
public class MultipartRequest extends Request<JSONObject> {

	private final String TAG = MultipartRequest.class.getSimpleName();

	private Listener<JSONObject> mListener;
	private MultipartRequestParams params = null;
	private HttpEntity httpEntity = null;

	public MultipartRequest(String url, Listener<JSONObject> listener,
			ErrorListener errorListener, MultipartRequestParams params) {
		super(Request.Method.POST, url, errorListener);
		this.mListener = listener;
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
				System.err
						.println("IOException writing to ByteArrayOutputStream");
			}
			String str = new String(baos.toByteArray());
			LogUtils.e(TAG, str);
		}
		return baos.toByteArray();
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		try {
			CustomApplication.getInstance()
					.checkSessionCookie(response.headers);
			String jsonString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			LogUtils.i(TAG, "response = " + jsonString);
			return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(response));
		} catch (JSONException je) {
			return Response.error(new ParseError(response));
		}
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = super.getHeaders();
		if (null == headers || headers.equals(Collections.emptyMap())) {
			headers = new HashMap<String, String>();
		}
		// 这里加cookie
		CustomApplication.getInstance().addSessionCookie(headers);
		return headers;
	}

	@Override
	public String getBodyContentType() {
		// String str = httpEntity.getContentType().getValue();
		return httpEntity.getContentType().getValue();
	}

	@Override
	protected void deliverResponse(JSONObject response) {
		mListener.onResponse(response);
	}

}
