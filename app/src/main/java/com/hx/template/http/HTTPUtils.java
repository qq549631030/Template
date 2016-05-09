package com.hx.template.http;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.hx.template.CustomApplication;

/**
 * Created by huangx on 2016/5/3.
 */
public class HTTPUtils {
    private static final String TAG = "HTTPUtils";
    /**
     * 执行HTTP请求
     *
     * @param request HTTP请求
     * @return void
     */
    public static <T> void doRequest(Request<T> request) {
        request.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 0, 1.0f));//超时5s,超时后重连0次
        CustomApplication.getInstance().addToRequestQueue(request);
    }
}
