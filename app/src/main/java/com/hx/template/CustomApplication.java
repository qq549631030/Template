package com.hx.template;

import android.app.Application;
import android.os.StrictMode;

//import com.squareup.leakcanary.LeakCanary;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.GINGERBREAD;

/**
 * Created by huangxiang on 15/10/14.
 */
public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        enabledStrictMode();
        //内存泄露检测(debug时候用就好)
//        LeakCanary.install(this);
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
}
