package com.hx.template;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.facebook.stetho.Stetho;
import com.github.anrwatchdog.ANRWatchDog;
import com.hx.easemob.DefaultSDKHelper;
import com.hx.easemob.HXSDKHelper;
import com.hx.mvp.Callback;
import com.hx.template.acra.MainReportSenderFactory;
import com.hx.template.dagger2.AppComponent;
import com.hx.template.dagger2.AppModule;
import com.hx.template.dagger2.ComponentHolder;
import com.hx.template.dagger2.DaggerAppComponent;
import com.hx.template.dagger2.ModelModule;
import com.hx.template.dagger2.PresenterModule;
import com.hx.template.dagger2.UseCaseModule;
import com.hx.template.entity.User;
import com.hx.template.event.UserInfoUpdateEvent;
import com.hx.template.global.GlobalActivityManager;
import com.hx.template.global.HXLog;
import com.hx.template.http.bmob.BmobDataChangeListener;
import com.hx.template.http.bmob.BmobManager;
import com.hx.template.model.ModelManager;
import com.hx.template.model.UserModel;
import com.hx.template.utils.SharedPreferencesUtil;

import net.sqlcipher.database.SQLiteDatabase;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.helper.GsonUtil;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.GINGERBREAD;

/**
 * Created by huangxiang on 15/10/14.
 */
@ReportsCrashes(
        reportSenderFactoryClasses = MainReportSenderFactory.class,
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.acra_toast_string
)
public class CustomApplication extends Application {

    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "JSESSIONID";

    public static String sessionId = "";

    private static CustomApplication instance;

    private ActivityLifecycleCallbacks activityLifecycleCallbacks = null;

    private static HXSDKHelper hxsdkHelper = new CustomSDKHelper();

    @Override
    protected void attachBaseContext(Context base) {
        try {
            super.attachBaseContext(base);
//            RocooFix.init(this);
//            RocooFix.initPathFromAssets(this, "patch.jar");
            MultiDex.install(this);
            ACRA.DEV_LOGGING = true;
            ACRA.init(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        int pid = android.os.Process.myPid();
        HXLog.d("CustomApplication onCreate pid = " + pid);
        if (!ACRA.isACRASenderServiceProcess()) {
            //初始化加密ormlite数据库
            SQLiteDatabase.loadLibs(this);
            instance = this;
            initDagger2();
            BmobManager.init(instance);
            hxsdkHelper.init(instance);
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
            new ANRWatchDog().setIgnoreDebugger(true).start();
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onTerminate() {
        EventBus.getDefault().unregister(this);
        super.onTerminate();
    }

    public static CustomApplication getInstance() {
        return instance;
    }

    private void enabledStrictMode() {
        if (SDK_INT >= GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                    .detectNetwork() //
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
                .appModule(new AppModule(instance)).modelModule(new ModelModule()).presenterModule(new PresenterModule()).useCaseModule(new UseCaseModule())
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

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(UserInfoUpdateEvent event) {
        User user = User.getCurrentUser();
        if (user != null) {
            ((CustomSDKHelper) CustomSDKHelper.getInstance()).getUserProfileManager().setCurrentUserNick(user.getNickname());
            ((CustomSDKHelper) CustomSDKHelper.getInstance()).getUserProfileManager().setCurrentUserAvatar(user.getAvatar());
        }
    }

    public static void reloadUserInfo() {
        reloadUserInfo(null);
    }

    public static void reloadUserInfo(Callback callback) {
        User user = User.getCurrentUser();
        if (user != null) {
            UserModel userModel = ModelManager.provideUserModel();
            if (callback != null) {
                userModel.getUserInfo(user.getObjectId(), "userId", callback);
            } else {
                userModel.getUserInfo(user.getObjectId(), "userId", new Callback() {
                    @Override
                    public void onSuccess(Object... data) {
                        if (data != null && data.length > 0 && data[0] instanceof User) {
                            String userData = GsonUtil.toJson(data[0]);
                            SharedPreferencesUtil.setParam(instance, "bmob_sp", "user", userData);
                            try {
                                User.setCurrent(new JSONObject(userData));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            EventBus.getDefault().post(new UserInfoUpdateEvent());
                        }
                    }

                    @Override
                    public void onFailure(String errorCode, Object... errorData) {

                    }
                });
            }
        }
    }


}
