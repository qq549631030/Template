package com.hx.template.base;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hx.template.R;
import com.hx.template.base.BaseDialogConfigFactory.BaseDialogType;

/**
 * Created by huangx on 2016/5/17.
 */
public class BaseDialogConfig implements View.OnClickListener {
    private Activity context;

    private Dialog dialog;

    private View view;
    private DialogInterface.OnCancelListener mCancleListener = null;
    private android.content.DialogInterface.OnClickListener mBtnClicklistener;
    private boolean cancelable = true;
    private boolean touchOutsideCancle = false;
    private CharSequence message;
    private CharSequence title;
    private int width = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int height = ViewGroup.LayoutParams.WRAP_CONTENT;

    private int maxProgress = 100;
    private int currProgress = 0;

    private BaseDialogType type = BaseDialogType.BASE_DIALOG_CONFIRM;

    private int themeId = -1;

    public BaseDialogConfig(Activity context) {
        this.context = context;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void onClick(View v) {
        if (mBtnClicklistener != null) {
            Dialog dialog = BaseDialog.getDialogWithoutCreate((BaseActivity) context);
            mBtnClicklistener.onClick(dialog != null ? dialog : this.dialog, v.getId());
        }
    }

    public BaseDialogConfig setView(int viewId) {
        View view = context.getLayoutInflater().inflate(viewId, null);
        return setView(view);
    }

    public BaseDialogConfig setView(View view) {
        this.view = view;
        return this;
    }

    public View getView() {
        return view;
    }

    public BaseDialogConfig setBtnText(int btnId, CharSequence text) {
        if (view == null) {
            throw new NullPointerException("please call setView beforce setBtnText");
        }
        View v = view.findViewById(btnId);
        if (v != null) {
            if (v instanceof TextView) {
                ((TextView) v).setText(text);
            }
            v.setOnClickListener(this);
        }
        return this;
    }

    public BaseDialogConfig setBtn(int btnId) {
        return setBtnText(btnId, null);
    }

    public BaseDialogConfig setPositiveBtn(String text) {
        return setBtnText(R.id.base_dialog_btn_ok, text);
    }

    public BaseDialogConfig setNegativeBtn(String text) {
        return setBtnText(R.id.base_dialog_btn_cancel, text);
    }

    public BaseDialogConfig setPositiveBtn(int resId) {
        return setBtnText(R.id.base_dialog_btn_ok, context.getResources().getText(resId));
    }

    public BaseDialogConfig setNegativeBtn(int resId) {
        return setBtnText(R.id.base_dialog_btn_cancel, context.getResources().getText(resId));
    }

    public BaseDialogConfig setVisibility(int resId, int visibility) {
        if (view == null) {
            throw new NullPointerException("please call setView beforce setVisibility");
        }
        View v = view.findViewById(resId);
        if (v != null) {
            v.setVisibility(visibility);
        }
        return this;
    }

    @SuppressLint("NewApi")
    public BaseDialogConfig setVisibleDelay(int resId, final long ms) {
        if (view == null) {
            throw new NullPointerException("please call setView beforce setVisibleDelay");
        }
        final View v = view.findViewById(resId);
        if (v != null) {
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    v.setVisibility(View.VISIBLE);
                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(
                            ObjectAnimator.ofFloat(v, "rotation", 0, 90),
                            ObjectAnimator.ofFloat(v, "scaleX", 0.5f, 1.25f, 1),
                            ObjectAnimator.ofFloat(v, "scaleY", 0.5f, 1.25f, 1),
                            ObjectAnimator.ofFloat(v, "alpha", 0, 1)
                    );
                    set.setDuration(context.getResources().getInteger(android.R.integer.config_shortAnimTime)).start();
                }
            };
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                v.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                    @Override
                    public void onViewDetachedFromWindow(View view) {
                        v.removeCallbacks(r);
                    }

                    @Override
                    public void onViewAttachedToWindow(View view) {
                        v.postDelayed(r, ms);
                    }
                });
            } else {
                v.postDelayed(r, ms);
            }
        }
        return this;
    }

    public BaseDialogConfig setOnBtnClickListener(android.content.DialogInterface.OnClickListener listener) {
        mBtnClicklistener = listener;
        return this;
    }


    public BaseDialogConfig setOnCancelListener(DialogInterface.OnCancelListener listener) {
        mCancleListener = listener;
        return this;
    }

    public DialogInterface.OnCancelListener getOnCancleListener() {
        return mCancleListener;
    }


    public BaseDialogConfig setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public boolean getCancelable() {
        return cancelable;
    }

    public BaseDialogConfig setCanceledOnTouchOutside(boolean touchOutsideCancle) {
        this.touchOutsideCancle = touchOutsideCancle;
        return this;
    }

    public boolean getCanceledOnTouchOutside() {
        return touchOutsideCancle;
    }

    public int getHeight() {
        return height;
    }

    public BaseDialogConfig setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public BaseDialogConfig setWidth(int width) {
        this.width = width;
        return this;
    }

    public BaseDialogConfig setTitle(CharSequence title) {
        this.title = title;
        return this;
    }

    public BaseDialogConfig setTitle(int resId) {
        this.title = context.getText(resId);
        return this;
    }

    public CharSequence getTitle() {
        return title;
    }

    public BaseDialogConfig setMessage(CharSequence message) {
        this.message = message;
        return this;
    }

    public BaseDialogConfig setMessage(int resId) {
        this.message = context.getText(resId);
        return this;
    }

    public CharSequence getMessage() {
        return message;
    }

    public BaseDialogType getType() {
        return type;
    }

    public void setType(BaseDialogType type) {
        this.type = type;
    }

    public int getCurrProgress() {
        return currProgress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    public void setCurrProgress(int currProgress) {
        this.currProgress = currProgress;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }
}
