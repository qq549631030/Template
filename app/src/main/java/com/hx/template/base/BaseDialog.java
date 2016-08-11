package com.hx.template.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hx.template.R;

import java.util.HashMap;
import java.util.Map;

public class BaseDialog {
    private static final Map<Activity, Dialog> dialogMap = new HashMap<Activity, Dialog>();
    private static final Map<Activity, BaseDialogConfig> configMap = new HashMap<Activity, BaseDialogConfig>();

    public static synchronized void removeDialog(BaseActivity activity) {
        if (dialogMap.containsKey(activity)) {
            Dialog dialog = dialogMap.remove(activity);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        configMap.remove(activity);
    }

    private static void setDialogWidthHeight(Dialog dialog, int width, int height) {
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = width;
        params.height = height;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    private static void setContentView(Dialog dialog, View view) {
        FrameLayout frameLayout = (FrameLayout) dialog.findViewById(R.id.base_dialog_frame);
        frameLayout.removeAllViews();
        frameLayout.addView(view);
    }

    public static void setTitle(Dialog dialog, CharSequence text) {
        setTitle(dialog, text, false);
    }

    public static void setTitle(Dialog dialog, CharSequence text, boolean updateConfig) {
        View title = dialog.findViewById(R.id.base_dialog_title);
        if (title instanceof TextView) {
            if (TextUtils.isEmpty(text)) {
                title.setVisibility(View.GONE);
            } else {
                title.setVisibility(View.VISIBLE);
                ((TextView) title).setText(text);
            }
        }
        if (updateConfig) {
            Context context = dialog.getContext();
            if (context instanceof ContextThemeWrapper) {
                context = ((ContextThemeWrapper) context).getBaseContext();
            }
            BaseDialogConfig config = configMap.get(context);
            if (config != null) {
                config.setTitle(text);
            }
        }
    }

    public static void setProgress(Dialog dialog, int max, int curr) {
        View progress = dialog.findViewById(R.id.base_dialog_progressBar);
        if (progress instanceof ProgressBar) {
            ((ProgressBar) progress).setIndeterminate(max == 0);
            ((ProgressBar) progress).setMax(max);
            ((ProgressBar) progress).setProgress(curr);
        }
    }

    public static void setMessage(Dialog dialog, CharSequence text) {
        setMessage(dialog, text, false);
    }

    public static void setMessage(Dialog dialog, CharSequence text, boolean updateConfig) {
        View message = dialog.findViewById(R.id.base_dialog_message);
        if (message instanceof TextView) {
            ((TextView) message).setText(text);
            ((TextView) message).setMovementMethod(ScrollingMovementMethod.getInstance());
        }
        if (updateConfig) {
            Context context = dialog.getContext();
            if (context instanceof ContextThemeWrapper) {
                context = ((ContextThemeWrapper) context).getBaseContext();
            }
            BaseDialogConfig config = configMap.get(context);
            if (config != null) {
                config.setMessage(text);
            }
        }
    }

    public static synchronized void dismissDialog(BaseActivity activity) {
        Dialog dialog = getDialogWithoutCreate(activity);
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        configMap.remove(activity);
    }

    public static synchronized Dialog getDialogWithoutCreate(BaseActivity activity) {
        if (dialogMap.containsKey(activity)) {
            return dialogMap.get(activity);
        }
        return null;
    }

    public static synchronized Dialog getDialog(BaseActivity activity, BaseDialogConfig config) {
        return getDialog(activity, config, true);
    }

    public static synchronized Dialog getDialog(BaseActivity activity, BaseDialogConfig config, boolean storeConfig) {
        Dialog dialog = null;
        if (config == null) {
            throw new NullPointerException("BaseDialogConfig can not be null!");
        }
        dialog = getDialogWithoutCreate(activity);
        if (dialog == null) {
            int themeId = config.getThemeId();
            if (themeId == -1) {
                themeId = R.style.baseDialogStyle;
            }
            dialog = new Dialog(activity, themeId);
            dialog.setContentView(R.layout.layout_base_dialog);
            dialogMap.put(activity, dialog);
        }
        if (storeConfig) {
            configMap.put(activity, config);
        }
        config.setDialog(dialog);
        dialog.setCancelable(config.getCancelable());
        dialog.setCanceledOnTouchOutside(config.getCanceledOnTouchOutside());
        dialog.setOnCancelListener(config.getOnCancleListener());
        setDialogWidthHeight(dialog, config.getWidth(), config.getHeight());
        setContentView(dialog, config.getView());
        setTitle(dialog, config.getTitle());
        setMessage(dialog, config.getMessage());
        setProgress(dialog, config.getMaxProgress(), config.getCurrProgress());
        return dialog;
    }

    public static boolean restoreLastDialog(BaseActivity activity) {
        BaseDialogConfig config = getConfigByActivity(activity);
        if (config == null) {
            return false;
        }
        Dialog dialog = getDialog(activity, config, false);
        if (!dialog.isShowing()) {
            dialog.show();
        }
        return true;
    }

    public static BaseDialogConfig getConfigByActivity(BaseActivity activity) {
        return configMap.get(activity);
    }

}
