package com.hx.template.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.View;

import com.hx.template.global.HXLog;
import com.hx.template.global.SaveSceneUtils;
import com.hx.template.mvp.MvpView;
import com.hx.template.mvp.Presenter;

/**
 * Created by huangx on 2016/5/12.
 */
public class BaseFragment extends Fragment {

    protected boolean isViewCreated;

    protected boolean isVisable;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        String title = getFragmentTitle();
        if (!TextUtils.isEmpty(title)) {
            activity.setTitle(title);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SaveSceneUtils.onRestoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SaveSceneUtils.onRestoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        SaveSceneUtils.onRestoreInstanceState(this, savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
    }

    protected String getFragmentTitle() {
        return null;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        SaveSceneUtils.onSaveInstanceState(this, outState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisable = isVisibleToUser;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        if (!hidden) {
            String title = getFragmentTitle();
            if (!TextUtils.isEmpty(title)) {
                getActivity().setTitle(title);
            }
        }
        super.onHiddenChanged(hidden);
    }

    public boolean onBackKeyPress() {
        return false;
    }

    public void finish() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }
}
