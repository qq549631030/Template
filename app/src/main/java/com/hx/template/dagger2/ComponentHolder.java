package com.hx.template.dagger2;

/**
 * Created by huangxiang on 16/8/21.
 */
public class ComponentHolder {
    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static void setAppComponent(AppComponent appComponent) {
        ComponentHolder.appComponent = appComponent;
    }
}
