package com.hx.template.dagger2;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by huangxiang on 16/8/21.
 */
@Module
public class AppModule {

    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext() {
        return context;
    }
}
