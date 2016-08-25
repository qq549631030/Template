package com.hx.template;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.facebook.stetho.Stetho;
import com.google.gson.Gson;
import com.hx.template.dagger2.AppComponent;
import com.hx.template.dagger2.AppModule;
import com.hx.template.dagger2.ComponentHolder;
import com.hx.template.dagger2.DaggerAppComponent;
import com.hx.template.entity.User;
import com.hx.template.event.UserInfoUpdateEvent;
import com.hx.template.global.GlobalActivityManager;
import com.hx.template.global.HXLog;
import com.hx.template.http.bmob.BmobManager;
import com.hx.template.http.bmob.BmobDataChangeListener;
import com.hx.template.model.Callback;
import com.hx.template.model.ModelManager;
import com.hx.template.model.UserModel;
import com.hx.template.utils.JSONUtil;
import com.hx.template.utils.SharedPreferencesUtil;
import com.karumi.dexter.Dexter;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.Map;

import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.helper.GsonUtil;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.GINGERBREAD;

/**
 * Created by huangxiang on 15/10/14.
 */
public class CustomApplication extends Application {

    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "JSESSIONID";

    public static String sessionId = "";

    private static CustomApplication instance;

    private ActivityLifecycleCallbacks activityLifecycleCallbacks = null;


    @Override
    protected void attachBaseContext(Context base) {
        try {
            super.attachBaseContext(base);
            MultiDex.install(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化加密ormlite数据库
//        SQLiteDatabase.loadLibs(this);
        instance = this;
        initDagger2();
        Dexter.initialize(instance);
        BmobManager.init(instance);
        initActivityManager();
        if (BuildConfig.DEBUG) {
            enabledStrictMode();
        }
        //Stetho
        if (BuildConfig.DEBUG && Constant.STETHO_DEBUG) {
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                            .build());
        }
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

    public static void initDagger2() {
        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .build();
        ComponentHolder.setAppComponent(appComponent);
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

    private static BmobDataChangeListener userListener;

    public static void startSyncUserInfo() {
        HXLog.d("startSyncUserInfo");
        final User currentUser = User.getCurrentUser(User.class);
        if (currentUser != null) {
            if (userListener == null) {
                userListener = new BmobDataChangeListener("_User", currentUser.getObjectId(), BmobRealTimeData.ACTION_UPDATEROW) {
                    @Override
                    public void onDataChange(JSONObject jsonObject) {
                        if (jsonObject != null) {
                            HXLog.d(jsonObject.toString());
                            if (jsonObject != null) {
                                SharedPreferencesUtil.setParam(instance, "bmob_sp", "user", jsonObject.toString());
                                EventBus.getDefault().post(new UserInfoUpdateEvent());
                            }
                        }
                    }
                };
            }
            BmobManager.subBmobDataChangeListener(userListener);
        }
    }

    public static void stopSyncUserInfo() {
        HXLog.d("stopSyncUserInfo");
        if (userListener != null) {
            BmobManager.unSubBmobDataChangeListener(userListener);
        }
    }

    public static void reloadUserInfo() {
        reloadUserInfo(null);
    }

    public static void reloadUserInfo(Callback callback) {
        User user = User.getCurrentUser();
        if (user != null) {
            UserModel userModel = ModelManager.newUserModel();
            if (callback != null) {
                userModel.getUserInfo(user.getObjectId(), callback);
            } else {
                userModel.getUserInfo(user.getObjectId(), new Callback() {
                    @Override
                    public void onSuccess(int taskId, Object... data) {
                        if (data != null && data.length > 0 && data[0] instanceof User) {
                            String userData = GsonUtil.toJson(data[0]);
                            SharedPreferencesUtil.setParam(instance, "bmob_sp", "user", userData);
                            EventBus.getDefault().post(new UserInfoUpdateEvent());
                        }
                    }

                    @Override
                    public void onFailure(int taskId, String errorCode, Object... errorMsg) {

                    }
                });
            }
        }
    }
}
