package com.hx.template.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.hx.template.R;
import com.hx.template.mvp.MvpView;
import com.hx.template.mvp.Presenter;

/**
 * Created by huangx on 2016/5/13.
 */
public class BaseStepFragment<P extends Presenter<V>, V extends MvpView> extends BaseFragment<P, V> {
    /**
     * 是否是第一步
     */
    private boolean isFirstStep = false;
    /**
     * 是否是最后一步
     */
    private boolean isLastStep = false;
    /**
     * 是否能从当前页返回上一页
     */
    private boolean canBack = true;
    /**
     * 下一步目标Fragment
     */
    private Class<? extends BaseStepFragment> nextTarget = null;

    public boolean isFirstStep() {
        return isFirstStep;
    }

    public void setFirstStep(boolean firstStep) {
        isFirstStep = firstStep;
    }

    public boolean isLastStep() {
        return isLastStep;
    }

    public void setLastStep(boolean lastStep) {
        isLastStep = lastStep;
    }

    public boolean canBack() {
        return canBack;
    }

    public void setCanBack(boolean canBack) {
        this.canBack = canBack;
    }

    public Class<? extends BaseStepFragment> getNextTarget() {
        return nextTarget;
    }

    public void setNextTarget(Class<? extends BaseStepFragment> nextTarget) {
        this.nextTarget = nextTarget;
    }

    /**
     * 跳到下一步页面
     *
     * @param args 下一步Fragment初始化参数
     */
    protected void nextStepAction(Bundle args) {
        if (getActivity() == null) {
            return;
        }
        if (isLastStep()) {//当前已经是最后一步
            finish();
            return;
        }
        Class<? extends BaseStepFragment> clz = getNextTarget();
        if (clz == null) {//没有下一步
            finish();
            return;
        }
        BaseStepFragment fragment = null;
        try {
            fragment = clz.newInstance();
            fragment.setArguments(args);
        } catch (Exception e) {
            finish();
            return;
        }
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.common_push_in_right_anim,
                R.anim.common_push_out_left_anim,
                R.anim.common_push_in_left_anim,
                R.anim.common_push_out_right_anim);
        if (fm.getFragments() != null && fm.getFragments().size() > 0 && fragment.canBack()) {
            ft.add(R.id.content_area, fragment);
            ft.hide(this);
            ft.addToBackStack(null);
        } else {
            ft.remove(this);
            if (fm.getFragments() != null && fm.getFragments().size() > 0) {
                for (Fragment f : fm.getFragments()) {
                    if (f == null) {
                        continue;
                    }
                    ft.hide(f);
                }
            }
            ft.add(R.id.content_area, fragment);
        }
        ft.commitAllowingStateLoss();
    }
}
