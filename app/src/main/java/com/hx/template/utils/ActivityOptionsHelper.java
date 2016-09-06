package com.hx.template.utils;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

/**
 * 功能说明：com.hx.template.utils
 * 作者：huangx on 2016/8/31 17:41
 * 邮箱：huangx@pycredit.cn
 */
public class ActivityOptionsHelper {
    public static Bundle makeSceneTransitionAnimationBundle(Activity activity,
                                                            Pair<View, String>... sharedElements) {
        return makeSceneTransitionAnimationBundle(activity, false, sharedElements);
    }

    public static Bundle makeSceneTransitionAnimationBundle(Activity activity, boolean includeStatusBar,
                                                            Pair<View, String>... sharedElements) {

        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, TransitionHelper.createSafeTransitionParticipants(activity, includeStatusBar, sharedElements));
        return transitionActivityOptions.toBundle();
    }
}
