package com.hx.template.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.view.WindowManager;

import com.hx.template.CustomApplication;
import com.hx.template.R;

/**
 * Created by huangx on 2016/5/17.
 */
public class BaseDialogConfigFactory {
    private static final int DEFAULT_INFORMATION_WIDTH;
    private static final int DEFAULT_INFORMATION_HEIGHT;
    private static final int DEFAULT_CONFIRM_WIDTH;
    private static final int DEFAULT_CONFIRM_HEIGHT;
    private static final int DEFAULT_LOADING_WIDTH;
    private static final int DEFAULT_LOADING_HEIGHT;
    private static final int DEFAULT_PROGRESS_WIDTH;
    private static final int DEFAULT_PROGRESS_HEIGHT;

    static {
        int screenWidth = CustomApplication.getInstance().getResources().getDisplayMetrics().widthPixels;
        DEFAULT_INFORMATION_WIDTH = (screenWidth * 2) / 3;
        DEFAULT_INFORMATION_HEIGHT = (DEFAULT_INFORMATION_WIDTH * 3) / 4;
        DEFAULT_CONFIRM_WIDTH = (screenWidth * 2) / 3;
        DEFAULT_CONFIRM_HEIGHT = (DEFAULT_CONFIRM_WIDTH * 3) / 4;
        DEFAULT_LOADING_WIDTH = screenWidth / 2;
        DEFAULT_LOADING_HEIGHT = (DEFAULT_LOADING_WIDTH * 7) / 8;
        DEFAULT_PROGRESS_WIDTH = (screenWidth * 3) / 4;
        DEFAULT_PROGRESS_HEIGHT = (DEFAULT_PROGRESS_WIDTH * 1) / 2;
    }

    public static enum BaseDialogType {
        BASE_DIALOG_INFORMATION,
        BASE_DIALOG_CONFIRM,
        BASE_DIALOG_LOADING,
        BASE_DIALOG_PROGRESS
    }

    public static BaseDialogConfig getDialogConfig(BaseDialogType type,
                                                   Activity context,
                                                   CharSequence message) {
        return getDialogConfig(type, context, 0, 0, null, message, false, false, null, null);
    }

    public static BaseDialogConfig getDialogConfig(BaseDialogType type,
                                                   Activity context,
                                                   CharSequence message,
                                                   DialogInterface.OnClickListener clickListener) {
        return getDialogConfig(type, context, 0, 0, null, message, false, false, clickListener, null);
    }

    public static BaseDialogConfig getDialogConfig(BaseDialogType type,
                                                   Activity context,
                                                   CharSequence message,
                                                   DialogInterface.OnClickListener clickListener,
                                                   DialogInterface.OnCancelListener cancelListener) {
        return getDialogConfig(type, context, 0, 0, null, message, false, false, clickListener, cancelListener);
    }

    public static BaseDialogConfig getDialogConfig(BaseDialogType type,
                                                   Activity context,
                                                   CharSequence title,
                                                   CharSequence message) {
        return getDialogConfig(type, context, 0, 0, title, message, false, false, null, null);
    }

    public static BaseDialogConfig getDialogConfig(BaseDialogType type,
                                                   Activity context,
                                                   CharSequence title,
                                                   CharSequence message,
                                                   DialogInterface.OnClickListener clickListener) {
        return getDialogConfig(type, context, 0, 0, title, message, false, false, clickListener, null);
    }

    public static BaseDialogConfig getDialogConfig(BaseDialogType type,
                                                   Activity context,
                                                   CharSequence title,
                                                   CharSequence message,
                                                   DialogInterface.OnClickListener clickListener,
                                                   DialogInterface.OnCancelListener cancelListener) {
        return getDialogConfig(type, context, 0, 0, title, message, false, false, clickListener, cancelListener);
    }

    public static BaseDialogConfig getDialogConfig(BaseDialogType type,
                                                   Activity context,
                                                   int width,
                                                   int height,
                                                   CharSequence title,
                                                   CharSequence message) {
        return getDialogConfig(type, context, width, height, title, message, false, false, null, null);
    }

    public static BaseDialogConfig getDialogConfig(BaseDialogType type,
                                                   Activity context,
                                                   int width,
                                                   int height,
                                                   CharSequence title,
                                                   CharSequence message,
                                                   boolean cancelable,
                                                   boolean touchOutsideCancle,
                                                   DialogInterface.OnClickListener clickListener,
                                                   DialogInterface.OnCancelListener cancelListener) {
        if (type == null) {
            throw new NullPointerException("BaseDialogType can not be null!");
        }
        BaseDialogConfig config = new BaseDialogConfig(context);
        config.setType(type);
        config.setCancelable(cancelable);
        config.setCanceledOnTouchOutside(touchOutsideCancle);
        config.setTitle(title);
        config.setMessage(message);
        config.setOnCancelListener(cancelListener);
        config.setOnBtnClickListener(clickListener);
        switch (type) {
            case BASE_DIALOG_INFORMATION:
                if (width <= 0) {
                    if (width == 0) {
                        width = WindowManager.LayoutParams.WRAP_CONTENT;
                    } else {
                        width = DEFAULT_INFORMATION_WIDTH;
                    }
                }
                if (height <= 0) {
                    if (height == 0) {
                        height = WindowManager.LayoutParams.WRAP_CONTENT;
                    } else {
                        height = DEFAULT_INFORMATION_HEIGHT;
                    }
                }
                config.setWidth(width);
                config.setHeight(height);
                config.setView(R.layout.dialog_base_information);
                config.setVisibility(R.id.base_dialog_btn_cancel, View.GONE);
                config.setBtnText(R.id.base_dialog_btn_ok, "确定");
                break;

            case BASE_DIALOG_CONFIRM:
                if (width <= 0) {
                    if (width == 0) {
                        width = WindowManager.LayoutParams.WRAP_CONTENT;
                    } else {
                        width = DEFAULT_CONFIRM_WIDTH;
                    }
                }
                if (height <= 0) {
                    if (height == 0) {
                        height = WindowManager.LayoutParams.WRAP_CONTENT;
                    } else {
                        height = DEFAULT_CONFIRM_HEIGHT;
                    }
                }
                config.setWidth(width);
                config.setHeight(height);
                config.setView(R.layout.dialog_base_information);
                config.setBtnText(R.id.base_dialog_btn_ok, "确定");
                config.setBtnText(R.id.base_dialog_btn_cancel, "取消");
                break;

            case BASE_DIALOG_LOADING:
                if(width <= 0) {
                    width = DEFAULT_LOADING_WIDTH;
                }
                if(height <= 0) {
                    height = DEFAULT_LOADING_HEIGHT;
                }
                config.setWidth(width);
                config.setHeight(height);
                config.setView(R.layout.dialog_base_information);
                config.setVisibility(R.id.base_dialog_loading_progressBar, View.VISIBLE);
                config.setVisibility(R.id.base_dialog_btn_cancel, View.GONE);
                config.setVisibility(R.id.base_dialog_btn_ok, View.GONE);
                config.setBtnText(R.id.base_dialog_btn_ok, "取消");
                config.setVisibleDelay(R.id.base_dialog_btn_ok, 10 * 1000);
                break;

            case BASE_DIALOG_PROGRESS:
                if (width <= 0) {
                    width = DEFAULT_PROGRESS_WIDTH;
                }
                if (height <= 0) {
                    if (height == 0) {
                        height = WindowManager.LayoutParams.WRAP_CONTENT;
                    } else {
                        height = DEFAULT_PROGRESS_HEIGHT;
                    }
                }
                config.setWidth(width);
                config.setHeight(height);
                config.setView(R.layout.dialog_base_information);
                config.setVisibility(R.id.base_dialog_btn_cancel, View.INVISIBLE);
                config.setBtnText(R.id.base_dialog_btn_ok, "取消");
                break;
        }
        return config;
    }
}
