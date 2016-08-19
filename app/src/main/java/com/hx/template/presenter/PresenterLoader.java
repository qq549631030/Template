package com.hx.template.presenter;

import android.content.Context;
import android.support.v4.content.Loader;

/**
 * Created by huangx on 2016/8/19.
 */
public class PresenterLoader<T extends Presenter> extends Loader {

    private T presenter;

    public PresenterLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        if (presenter != null) {
            deliverResult(presenter);
            return;
        }
        forceLoad();
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
    }
}
