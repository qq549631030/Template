package com.hx.template;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.Volley;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.hx.template.entity.User;
import com.hx.template.global.GlobalActivityManager;
import com.hx.template.http.DefaultSSLSocketFactory;
import com.hx.template.http.volley.HttpsTrustManager;
import com.hx.template.http.volley.OkHttpStack;
import com.hx.template.http.volley.StethoOkHttpStack;
import com.hx.template.utils.NetWorkUtils;
import com.hx.template.utils.SecretUtils;
import com.hx.template.utils.SerializeUtil;
import com.hx.template.utils.SharedPreferencesUtil;
import com.hx.template.utils.ToastUtils;
import com.karumi.dexter.Dexter;
import com.squareup.okhttp.OkHttpClient;

import net.sqlcipher.database.SQLiteDatabase;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import cn.bmob.v3.Bmob;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.GINGERBREAD;

/**
 * Created by huangxiang on 15/10/14.
 */
public class CustomApplication extends Application {

    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "JSESSIONID";
    /**
     * Log or request TAG
     */
    public static final String VOLLEY_TAG = "VolleyPatterns";

    public static String sessionId = "";

    private static RequestQueue mRequestQueue;

    private static CustomApplication instance;

    public static String currentLoginId;

    public static User currentUser;

    private ActivityLifecycleCallbacks activityLifecycleCallbacks = null;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化加密ormlite数据库
        SQLiteDatabase.loadLibs(this);
        instance = this;
        Dexter.initialize(instance);
        Bmob.initialize(instance,"0dffa5dd0fb6b49c5dbcd57971946e0b");
        initActivityManager();
//        enabledStrictMode();
        //内存泄露检测
        if (Constant.DEBUG) {
//            LeakCanary.install(this);
        }
        //Stetho
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }

    public static CustomApplication getInstance() {
        return instance;
    }

    private void enabledStrictMode() {
        if (SDK_INT >= GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                    .detectAll() //
                    .penaltyLog() //
                    .penaltyDeath() //
                    .build());
        }
    }

    private void initActivityManager() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
                && activityLifecycleCallbacks == null) {
            activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
                @Override
                public void onActivityStopped(Activity activity) {
                }

                @Override
                public void onActivityStarted(Activity activity) {
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                }

                @Override
                public void onActivityResumed(Activity activity) {
                }

                @Override
                public void onActivityPaused(Activity activity) {
                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    GlobalActivityManager.remove(activity);
                }

                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    GlobalActivityManager.push(activity);
                }
            };
            registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
        }
    }

    public synchronized RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            synchronized (CustomApplication.class) {
                if (mRequestQueue == null) {
                    HttpStack stack = null;
                    if (Constant.DEBUG) {
                        stack = initDebugHttpStack();
                    } else {
                        stack = initReleaseHttpStack();
                    }
                    HttpsTrustManager.allowAllSSL();
                    mRequestQueue = Volley.newRequestQueue(instance, null);
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
                return true;
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
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? VOLLEY_TAG : tag);

        getRequestQueue().add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req
     */
    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        if (req.getTag() == null) {
            req.setTag(VOLLEY_TAG);
        }
        getRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important to
     * specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


    /**
     * 保存session
     *
     * @param headers
     */
    public static void checkSessionCookie(Map<String, String> headers) {
        if (headers.containsKey(SET_COOKIE_KEY) && headers.get(SET_COOKIE_KEY).startsWith(SESSION_COOKIE)) {
            String cookie = headers.get(SET_COOKIE_KEY);
            if (cookie.length() > 0) {
                String[] splitCookie = cookie.split(";");
                String[] splitSessionId = splitCookie[0].split("=");
                cookie = splitSessionId[1];
                if (!cookie.equals(sessionId)) {
                    sessionId = cookie;
                    SharedPreferencesUtil.setParam(instance, SESSION_COOKIE, sessionId);
                }
            }
        }
    }

    /**
     * 请求头中加上保存的session
     *
     * @param headers
     */
    public static void addSessionCookie(Map<String, String> headers) {
        if (sessionId.length() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(SESSION_COOKIE);
            builder.append("=");
            builder.append(sessionId);
            if (headers.containsKey(COOKIE_KEY)) {
                builder.append("; ");
                builder.append(headers.get(COOKIE_KEY));
            }
            headers.put(COOKIE_KEY, builder.toString());
        }
    }

    /**
     * 检查网络是否连接
     *
     * @param showToast 是否显示提示
     * @return
     */
    public static boolean isNetworkConnected(boolean showToast) {
        boolean connect = NetWorkUtils.isNetWorkConnect(getInstance());
        if (!connect && showToast) {
            ToastUtils.showToast(getInstance(), getInstance().getResources()
                    .getString(R.string.error_network_available));
        }
        return connect;
    }
}
