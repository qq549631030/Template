package com.hx.template.http;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.hx.template.CustomApplication;
import com.hx.template.R;
import com.hx.template.utils.NetWorkUtils;
import com.hx.template.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HttpPostUtils {

    /**
     * 执行HTTP请求
     *
     * @param request HTTP请求
     * @return void
     */
    public static <T> void doRequest(Request<T> request) {
        //设置重连策略
        request.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 0, 1.0f)); //超时5s,超时后重连0次
        CustomApplication.getInstance().addToRequestQueue(request);
    }

    /**
     * 以表单形式执行HTTP请求
     *
     * @param request
     * @param <T>
     */
    public static <T> void doBaseFormRequest(BaseFormRequest<T> request) {
        //通用请求头设置
        Map<String, String> addHeaders = new HashMap<String, String>();
        CustomApplication.addSessionCookie(addHeaders); //请求头加上jsession ID
        request.setAdditionalHeaders(addHeaders);//设置请求头

        request.setResponseCacheListener(new ResponseCacheListener() {
            @Override
            public void onResponse(Cache.Entry cache) {
                //保存jsession ID
                CustomApplication.checkSessionCookie(cache.responseHeaders);
            }
        });
        //for debug
        HXLog.i("request url = " + request.getOriginUrl());
        try {
            HXLog.i("request headers = " + request.getHeaders().toString());
            HXLog.i("request params = " + request.getParams().toString());
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
        //debug end
        doRequest(request);
    }


    /**
     * 以表单形式执行POST请求
     *
     * @param url           请求地址
     * @param params        请求参数
     * @param listener      请求监听
     * @param errorListener 请求错误监听
     * @return void
     */
    public static void doFormPostRequest(String url,
                                         Map<String, String> params, Listener<JSONObject> listener,
                                         ErrorListener errorListener) {
        FormPostRequest mRequest = new FormPostRequest(url, listener,
                errorListener);
        mRequest.setAdditionalParams(params);//设置请求参数
        doBaseFormRequest(mRequest);
    }

    /**
     * 以Json形式执行POST请求
     *
     * @param url
     * @param params
     * @param listener
     * @param errorListener
     */
    public static void doJsonPostRequest(String url, JSONObject params,
                                         Listener<JSONObject> listener, ErrorListener errorListener) {

        BaseJsonObjectRequest request = new BaseJsonObjectRequest(url, params, listener,
                errorListener);
        Map<String, String> addHeaders = new HashMap<String, String>();
        CustomApplication.addSessionCookie(addHeaders); //请求头加上jsession ID
        request.setAdditionalHeaders(addHeaders);//设置请求头
        request.setResponseCacheListener(new ResponseCacheListener() {
            @Override
            public void onResponse(Cache.Entry cache) {
                //保存jsession ID
                CustomApplication.checkSessionCookie(cache.responseHeaders);
            }
        });
        //for debug
        HXLog.i("request url = " + request.getOriginUrl());
        try {
            HXLog.i("request headers = " + request.getHeaders().toString());
            HXLog.i("request params = " + params.toString());
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
        //debug end
        doRequest(request);
    }

    /**
     * 上传文件
     *
     * @param url           请求地址
     * @param params        请求参数
     * @param listener      请求监听
     * @param errorListener 请求错误监听
     * @return void
     */
    public static void doUploadRequest(String url,
                                       MultipartRequestParams params, Listener<JSONObject> listener,
                                       ErrorListener errorListener) {
        MultipartRequest mRequest = new MultipartRequest(url, params, listener,
                errorListener);
        HXLog.i("request urlParams = " + params.urlParams.toString());
        HXLog.i("request fileParams = " + params.fileParams.toString());
        doBaseFormRequest(mRequest);
    }

    /**
     * 上传文件懒调用(可设置是否显示默认错误提示语)
     *
     * @param context            内容提供者
     * @param url                接口地址
     * @param params             接口参数
     * @param listener           回调监听
     * @param showCommanErrorMsg 是否显示默认提示语
     */
    public static void dolazyUploadRequest(final Context context, String url,
                                           MultipartRequestParams params, final HttpListener listener,
                                           final boolean showCommanErrorMsg) {
        if (context != null) {
            if (NetWorkUtils.isNetWorkConnect(context)) {
                doUploadRequest(url, params, new Listener<JSONObject>() {

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
     * Form懒调用(可设置是否显示默认错误提示语)
     *
     * @param context            内容提供者
     * @param url                接口地址
     * @param params             接口参数
     * @param listener           回调监听
     * @param showCommanErrorMsg 是否显示默认提示语
     */
    public static void doLazyFromPostRequest(final Context context, String url,
                                             Map<String, String> params, final HttpListener listener,
                                             final boolean showCommanErrorMsg) {
        if (context != null) {
            if (NetWorkUtils.isNetWorkConnect(context)) {
                doFormPostRequest(url, params, new Listener<JSONObject>() {

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

    /**
     * Json懒调用(可设置是否显示默认错误提示语) 参数为Map<String, String>
     *
     * @param context
     * @param url
     * @param params
     * @param listener
     * @param showCommanErrorMsg
     * @throws JSONException
     */
    public static void doLazyJsonPostRequest(final Context context, String url,
                                             Map<String, String> params, final HttpListener listener,
                                             final boolean showCommanErrorMsg) throws JSONException {
        JSONObject paramsObject = new JSONObject();
        for (String key : params.keySet()) {
            paramsObject.put(key, params.get(key));
        }
        doLazyJsonPostRequest(context, url, paramsObject, listener,
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
    public static void doLazyJsonPostRequest(final Context context, String url,
                                             JSONObject params, final HttpListener listener,
                                             final boolean showCommanErrorMsg) {
        if (context != null) {
            if (NetWorkUtils.isNetWorkConnect(context)) {
                doJsonPostRequest(url, params, new Listener<JSONObject>() {

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
