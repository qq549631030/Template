package cn.huangx.common.utils;

import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by huangx on 2016/8/17.
 */
public class KeyBoardUtils {
    /**
     * 键盘是否显示
     *
     * @param rootView
     * @return
     */
    public static boolean isKeyboardShown(View rootView) {
        /* 128dp = 32dp * 4, minimum button height 32dp and generic 4 rows soft keyboard */
        final int SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD = 128;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        /* heightDiff = rootView height - status bar height (r.top) - visible frame height (r.bottom - r.top) */
        int heightDiff = rootView.getBottom() - r.bottom;
        /* Threshold size: dp to pixels, multiply with display density */
        boolean isKeyboardShown = heightDiff > SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD * dm.density;
        //Log.d("LendMoney", "isKeyboardShown ? " + isKeyboardShown + ", heightDiff:" + heightDiff + ", density:" + dm.density + "root view height:" + rootView.getHeight() + ", rect:" + r);
        return isKeyboardShown;
    }
}
