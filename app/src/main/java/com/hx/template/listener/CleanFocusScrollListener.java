package com.hx.template.listener;

import android.app.Activity;
import android.view.View;
import android.widget.AbsListView;

/**
 * Created by huangxiang on 16/7/24.
 */
public class CleanFocusScrollListener implements AbsListView.OnScrollListener {
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (SCROLL_STATE_TOUCH_SCROLL == scrollState) {

            View currentFocus = null;
            if (view.getContext() instanceof Activity) {
                currentFocus = ((Activity) view.getContext()).getCurrentFocus();
            }
            if (currentFocus != null) {
                currentFocus.clearFocus();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
