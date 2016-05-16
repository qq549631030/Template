package com.hx.template.base;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

/**
 * Created by huangx on 2016/5/12.
 */
public class BaseFragment extends Fragment {

    protected String getFragmentTitle() {
        return null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        String title = getFragmentTitle();
        if (!TextUtils.isEmpty(title)) {
            activity.setTitle(title);
        }
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
