package com.hx.template.http;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.hx.template.CustomApplication;
import com.hx.template.R;
import com.hx.template.utils.LogUtils;
import com.hx.template.utils.NetWorkUtils;
import com.hx.template.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Map;

public class HttpGetUtils {
    private final static String TAG = HttpGetUtils.class.getSimpleName();

    /**
     * 执行HTTP请求
     *
     * @param request HTTP请求
     * @return void
     */
    public static void doRequest(Request<JSONObject> request) {
        request.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 0, 1.0f));//超时5s,超时后重连0次
        CustomApplication.getInstance().addToRequestQueue(request);
    }

    /**
     * 以表单形式执行GET请求
     *
     * @param url           请求地址
     * @param listener      请求监听
     * @param errorListener 请求错误监听
     * @return void
     */
    public static void doFormGetRequest(String url, Map<String, String> params,
                                        Listener<JSONObject> listener, ErrorListener errorListener) {
        if (params != null) {
            url = url + "?";
            for (String key : params.keySet()) {
                url = url + key + "=" + params.get(key) + "&";
            }
            if (url.endsWith("&")) {
                url = url.substring(0, url.length() - 1);
            }
        }
        LogUtils.i(TAG, url);
        FormGetRequest mRequest = new FormGetRequest(url, listener,
                errorListener);

        Map<String, String> addHeaders = Collections.emptyMap();
        CustomApplication.addSessionCookie(addHeaders); //请求头加上jsession ID
        mRequest.setAdditionalHeaders(addHeaders);//设置请求头

        mRequest.setResponseCacheListener(new ResponseCacheListener() {
            @Override
            public void onResponse(Cache.Entry cache) {
                //保存jsession ID
                CustomApplication.checkSessionCookie(cache.responseHeaders);
            }
        });
        doRequest(mRequest);
    }

    /**
     * 以json形式执行GET请求
     *
     * @param url
     * @param params
     * @param listener
     * @param errorListener
     */
    public static void doJsonGetRequest(String url, JSONObject params,
                                        Listener<JSONObject> listener, ErrorListener errorListener) {
        if (params != null) {
            url = url + "?";
            while (params.keys().hasNext()) {
                String key = params.keys().next();
                url = url + key + "=" + params.optString(key) + "&";
            }
            if (url.endsWith("&")) {
                url = url.substring(0, url.length() - 1);
            }
        }
        LogUtils.i(TAG, url);
        BaseJsonObjectRequest mRequest = new BaseJsonObjectRequest(url, params, listener,
                errorListener);

        Map<String, String> addHeaders = Collections.emptyMap();
        CustomApplication.addSessionCookie(addHeaders); //请求头加上jsession ID
        mRequest.setAdditionalHeaders(addHeaders);//设置请求头

        mRequest.setResponseCacheListener(new ResponseCacheListener() {
            @Override
            public void onResponse(Cache.Entry cache) {
                //保存jsession ID
                CustomApplication.checkSessionCookie(cache.responseHeaders);
            }
        });
        doRequest(mRequest);
    }

    /**
     * Form懒调用(可设置是否显示默认错误提示语)
     *
     * @param context            内容提供者
     * @param url                接口地址
     * @param params             接口参数
     * @param listener           回调监听
     * @param showCommanErrorMsg 是否显示默认提示语
     */
    public static void doLazyFormGetRequest(final Context context, String url,
                                            Map<String, String> params, final HttpListener listener,
                                            final boolean showCommanErrorMsg) {
        if (context != null) {
            if (NetWorkUtils.isNetWorkConnect(context)) {
                doFormGetRequest(url, params, new Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject arg0) {
                        listener.onPass(arg0);
                    }
                }, new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError arg0) {
                        if (showCommanErrorMsg) {
                            ToastUtils.showToast(context, context.getResources().getString(R.string.error_network_abnormal));
                        }
                        listener.onError(
                                context.getResources().getString(
                                        R.string.error_network_abnormal), 2);
                    }
                });
            } else {
                if (showCommanErrorMsg) {
                    ToastUtils.showToast(context, context.getResources()
                            .getString(R.string.error_network_available));
                }
                listener.onError(
                        context.getResources().getString(
                                R.string.error_network_available), 1);
            }
        }
    }

    /**
     * Json懒调用(可设置是否显示默认错误提示语) 参数为Map
     *
     * @param context
     * @param url
     * @param params
     * @param listener
     * @param showCommanErrorMsg
     * @throws JSONException
     */
    public static void doLazyJsonGetRequest(final Context context, String url,
                                            Map<String, String> params, final HttpListener listener,
                                            final boolean showCommanErrorMsg) throws JSONException {
        JSONObject paramsObject = new JSONObject();
        for (String key : params.keySet()) {
            paramsObject.put(key, params.get(key));
        }
        doLazyJsonGetRequest(context, url, paramsObject, listener,
                showCommanErrorMsg);
    }

    /**
     * Json懒调用(可设置是否显示默认错误提示语) 参数为JsonObject
     *
     * @param context
     * @param url
     * @param params
     * @param listener
     * @param showCommanErrorMsg
     */
    public static void doLazyJsonGetRequest(final Context context, String url,
                                            JSONObject params, final HttpListener listener,
                                            final boolean showCommanErrorMsg) {
        if (context != null) {
            if (NetWorkUtils.isNetWorkConnect(context)) {
                doJsonGetRequest(url, params, new Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject arg0) {
                        listener.onPass(arg0);
                    }
                }, new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError arg0) {
                        if (showCommanErrorMsg) {
                            ToastUtils.showToast(
                                    context,
                                    context.getResources().getString(
                                            R.string.error_network_abnormal));
                        }
                        listener.onError(
                                context.getResources().getString(
                                        R.string.error_network_abnormal), 2);
                    }
                });
            } else {
                if (showCommanErrorMsg) {
                    ToastUtils.showToast(context, context.getResources()
                            .getString(R.string.error_network_available));
                }
                listener.onError(
                        context.getResources().getString(
                                R.string.error_network_available), 1);
            }
        }
    }
}
