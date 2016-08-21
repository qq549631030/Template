package com.hx.template;

import android.os.Environment;

import com.hx.template.dagger2.AppComponent;
import com.hx.template.dagger2.AppModule;
import com.hx.template.dagger2.ComponentHolder;

import org.robolectric.RuntimeEnvironment;

import it.cosenonjaviste.daggermock.DaggerMockRule;

/**
 * Created by huangxiang on 16/8/21.
 */
public class DaggerRule extends DaggerMockRule<AppComponent> {
    public DaggerRule() {
        super(AppComponent.class, new AppModule(RuntimeEnvironment.application));
        set(new ComponentSetter<AppComponent>() {
            @Override
            public void setComponent(AppComponent appComponent) {
                ComponentHolder.setAppComponent(appComponent);
            }
        });
    }
}
