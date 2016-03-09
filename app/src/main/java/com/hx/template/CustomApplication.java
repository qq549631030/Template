package com.hx.template;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
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
import com.hx.template.http.OkHttpStack;
import com.hx.template.http.StethoOkHttpStack;
import com.hx.template.utils.NetWorkUtils;
import com.hx.template.utils.SharedPreferencesUtil;
import com.hx.template.utils.ToastUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.utils.L;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.Map;

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

    public static ArrayList<Activity> activityList;

    public static String sessionId = "";

    private static RequestQueue mRequestQueue;

    private static CustomApplication instance;

    public static DisplayImageOptions defaultOptions;//默认图片配置
    public static DisplayImageOptions avatarOptions;//头像图片配置


    public static long currentLoginId;

    public static User currentUser;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        activityList = new ArrayList<Activity>();
        initImageLoader(instance);
        enabledStrictMode();
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


    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity);
        }
    }

    public static void finishAllActivity() {
        for (Activity a : activityList) {
            a.finish();
        }
        activityList.clear();
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

    public static void initImageLoader(Context context) {
        defaultOptions = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.default_image).showImageOnFail(R.drawable.default_image).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).displayer(new SimpleBitmapDisplayer()).bitmapConfig(Bitmap.Config.RGB_565).build();
        avatarOptions = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.default_avatar).showImageOnFail(R.drawable.default_avatar).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).displayer(new SimpleBitmapDisplayer()).bitmapConfig(Bitmap.Config.RGB_565).build();

        try {// 缓存目录
            ImageLoaderConfiguration config;
            config = new ImageLoaderConfiguration.Builder(context).threadPriority(Thread
                    .NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                    // 使用1/8 (13%)APP内存
                    .memoryCacheSizePercentage(13).diskCacheFileNameGenerator(new ImageNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
            ImageLoader.getInstance().init(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
        L.disableLogging();
    }

    private static class ImageNameGenerator extends Md5FileNameGenerator {

        @Override
        public String generate(String imageUri) {
            return super.generate(imageUri) + ".jpg";
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
                    mRequestQueue = Volley.newRequestQueue(instance, stack);
                }
            }
        }
        return mRequestQueue;
    }

    public static HttpStack initDebugHttpStack() {
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());
        StethoOkHttpStack stack = new StethoOkHttpStack(client);
        return stack;
    }

    public static HttpStack initReleaseHttpStack() {
        OkHttpStack stack = new OkHttpStack();
        return stack;
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
        req.setTag(VOLLEY_TAG);

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
