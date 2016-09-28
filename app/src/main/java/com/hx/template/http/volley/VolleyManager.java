package com.hx.template.http.volley;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.Volley;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.hx.template.BuildConfig;
import com.hx.template.CustomApplication;
import com.hx.template.http.DefaultSSLSocketFactory;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

/**
 * Created by huangx on 2016/8/18.
 */
public class VolleyManager {
    /**
     * Log or request TAG
     */
    public static final String VOLLEY_TAG = "VolleyPatterns";

    private static RequestQueue mRequestQueue;

    public static synchronized RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            synchronized (VolleyManager.class) {
                if (mRequestQueue == null) {
                    HttpStack stack = null;
                    if (BuildConfig.DEBUG) {
                        stack = initDebugHttpStack();
                    } else {
                        stack = initReleaseHttpStack();
                    }
                    HttpsTrustManager.allowAllSSL();
                    mRequestQueue = Volley.newRequestQueue(CustomApplication.getInstance(), stack);
                }
            }
        }
        return mRequestQueue;
    }


    public static HttpStack initDebugHttpStack() {
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());
        return new StethoOkHttpStack(client);
    }

    public static HttpStack initReleaseHttpStack() {
        okhttp3.OkHttpClient.Builder httpClient = new okhttp3.OkHttpClient.Builder();
        try {
            httpClient.sslSocketFactory(new DefaultSSLSocketFactory());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        httpClient.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;//信任所有host
            }
        });
        return new OkHttpStack(httpClient.build());
    }

    /**
     * Adds the specified request to the global queue, if tag is specified then
     * it is used else Default TAG is used.
     *
     * @param req
     */
    public static <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? VOLLEY_TAG : tag);

        getRequestQueue().add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req
     */
    public static <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        if (req.getTag() == null) {
            req.setTag(VOLLEY_TAG);
        }
        getRequestQueue().add(req);
    }

    public static void cancelPendingRequests() {
        cancelPendingRequests(VOLLEY_TAG);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important to
     * specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */
    public static void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
